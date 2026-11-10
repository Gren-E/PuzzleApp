package com.pa.model.data;

import com.pa.model.database.InternalDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSQL<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractSQL.class);

    private final String tableName;
    private final String uniqueColumnName;
    private final List<String> columnNames;


    protected AbstractSQL(String tableName, String uniqueColumnName, String... columnNames) {
        this.tableName = tableName;
        this.uniqueColumnName = uniqueColumnName;
        this.columnNames = List.of(columnNames);
    }

    public void insert(T object) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (" + String.join(", ", columnNames) + ") VALUES (" + convertToWildcards(columnNames) + ")";
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                setInsertParameters(statement, object);
                statement.executeUpdate();
                afterInsertion(connection, object);
                connection.commit();
            } catch (Exception e) {
                LOG.error("Could not insert object: {}", object, e);
                connection.rollback();
            }
        }
    }

    public List<T> selectAll() throws SQLException {
        List<T> allObjects = new ArrayList<>();

        String sql = "SELECT * FROM " + tableName;
        try (Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                T object = readCurrentRecord(results);
                enrichObject(connection, object);
                allObjects.add(object);
            }
        }

        return allObjects;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + uniqueColumnName + " = ?";
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                int deleted = statement.executeUpdate();
                if (deleted == 0) {
                    return false;
                }
                afterDeletion(connection, id);
                connection.commit();
                return true;
            } catch (Exception e) {
                LOG.error("Could not delete object with id: {}", id, e);
                connection.rollback();
                return false;
            }
        }
    }

    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt(1);
        }
    }

    protected void afterInsertion(Connection connection, T object) throws SQLException {}

    protected void afterDeletion(Connection connection, int id) throws SQLException {}

    protected void enrichObject(Connection connection, T object) throws SQLException {}

    protected abstract void setInsertParameters(PreparedStatement statement, T object) throws SQLException;

    protected abstract T readCurrentRecord(ResultSet results) throws SQLException;

    private String convertToWildcards(List<String> columns) {
        StringBuilder result = new StringBuilder();
        result.append("?, ".repeat(columns.size()));
        return result.substring(0, result.length() - 2);
    }

}
