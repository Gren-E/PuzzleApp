package com.pa.model.data;

import com.pa.model.database.InternalDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractSQL {

    public static boolean delete(int id, String sql) throws SQLException {
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int deleted = statement.executeUpdate();
            return deleted > 0;
        }
    }

    public static int countAll(String sql) throws SQLException {
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt(1);
        }
    }

}
