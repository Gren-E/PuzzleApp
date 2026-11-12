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

/**
 * Abstract class providing basic tools for managing a database, like inserting or deleting records.
 * @param <T> the type of objects recorded in a specific table
 */
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

    /**
     * Inserts a new record into the table or throws exception if the object cannot be added.
     * @param object object representing the new record to be added
     * @throws SQLException whenever the new record cannot be added to the table
     */
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

    /**
     * Returns a {@code List} of all the records in the table as their object representation.
     * @return a List of the table records
     * @throws SQLException if the objects cannot be selected
     */
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

    /**
     * Deletes a record with the provided id number.
     * @param id a unique {@code int} value assigned to the record that is supposed to be deleted
     * @return {@code true} if the record gets deleted properly, false if a record with a specific id cannot be found
     * @throws SQLException if the deletion operation fails
     */
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

    /**
     * Counts all the records in a table.
     * @return returns the number of records in a table
     * @throws SQLException if the records cannot be counted
     */
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt(1);
        }
    }

    /**
     * Performs additional optional operations after inserting a new record.
     * @param connection {@code Connection} to the database
     * @param object an object representing the new record added to the table
     * @throws SQLException if the additional operations cannot be performed
     */
    protected void afterInsertion(Connection connection, T object) throws SQLException {}

    /**
     * Performs additional optional operations after deleting a record from a table.
     * @param connection {@code Connection} to the database
     * @param id a unique number assigned to the record that is being deleted
     * @throws SQLException if the additional operations cannot be performed
     */
    protected void afterDeletion(Connection connection, int id) throws SQLException {}

    /**
     * Adds additional values to the object, whenever one object contains information from multiple tables
     * @param connection {@code Connection} to the database
     * @param object an object to be enriched
     * @throws SQLException if the necessary data from secondary tables cannot be read
     */
    protected void enrichObject(Connection connection, T object) throws SQLException {}

    /**
     * Sets parameters in the statement, based on the object to be recorded in the table.
     * @param statement an SQL statement to be executed in order to add a new record to the table
     * @param object an object to be recorded in the table
     * @throws SQLException if the statement's parameters cannot be set
     */
    protected abstract void setInsertParameters(PreparedStatement statement, T object) throws SQLException;

    /**
     * Reads the data contained in a table's single record and creates a new object based on it.
     * @param results a {@code ResultSet} containing every record in the table
     * @return an object representing a current record in the results set
     * @throws SQLException if the record cannot be read
     */
    protected abstract T readCurrentRecord(ResultSet results) throws SQLException;

    /**
     * Determines how many parameters there are in an SQL statement, depending on the number of columns in the table.
     * @param columns how many columns is there in a record
     * @return a {@code String} value with the right number of wildcards - "?" to be added to an SQL statement
     */
    private String convertToWildcards(List<String> columns) {
        StringBuilder result = new StringBuilder();
        result.append("?, ".repeat(columns.size()));
        return result.substring(0, result.length() - 2);
    }

}
