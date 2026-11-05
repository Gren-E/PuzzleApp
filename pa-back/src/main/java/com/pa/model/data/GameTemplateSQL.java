package com.pa.model.data;

import com.pa.model.creator.PieceStyle;
import com.pa.model.database.InternalDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameTemplateSQL {

    private static final String TABLE = "game_templates";

    public static void insert(GameTemplate template) throws SQLException {
        String sql = "INSERT INTO " + TABLE + " (template_id, picture_id, piece_style, rows, columns, rotation) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, template.id());
            statement.setInt(2, template.pictureId());
            statement.setString(3, template.style().name());
            statement.setInt(4, template.rows());
            statement.setInt(5, template.columns());
            statement.setBoolean(6, template.canPiecesRotate());
            statement.executeUpdate();
        }
    }

    public static GameTemplate[] selectAll() throws SQLException {
        List<GameTemplate> allTemplates = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE;
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                allTemplates.add(readCurrentRecord(results));
            }
        }

        return allTemplates.toArray(new GameTemplate[0]);
    }

    public static boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE template_id = ?";
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int deleted = statement.executeUpdate();
            return deleted > 0;
        }
    }

    public static int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + TABLE;
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt(1);
        }
    }

    private static GameTemplate readCurrentRecord(ResultSet results) throws SQLException {
        return new GameTemplate(
                results.getInt(1),
                results.getInt(2),
                PieceStyle.valueOf(results.getString(3)),
                results.getInt(4),
                results.getInt(5),
                results.getBoolean(6)
        );
    }

}
