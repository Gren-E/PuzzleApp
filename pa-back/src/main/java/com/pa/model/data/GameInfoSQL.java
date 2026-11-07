package com.pa.model.data;

import com.pa.model.creator.PieceStyle;
import com.pa.model.database.InternalDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GameInfoSQL {

    private static final String TABLE = "games";

    public static void insert(GameInfo game) throws SQLException {
        String sql = "INSERT INTO " + TABLE + " (game_id, status, template_id, picture_id, piece_style, rows, columns, rotation, start_timestamp, duration_in_seconds) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, game.getId());
            statement.setString(2, game.getStatus().name());
            statement.setInt(3, game.getTemplateId());
            statement.setInt(4, game.getPictureId());
            statement.setString(5, game.getPieceStyle().name());
            statement.setInt(6, game.getRows());
            statement.setInt(7, game.getColumns());
            statement.setBoolean(8, game.isRotation());
            statement.setTimestamp(9, Timestamp.valueOf(game.getStartTimestamp()));
            statement.setLong(10, game.getDurationInSeconds());
            statement.executeUpdate();
        }
    }

    public static GameInfo[] selectAll() throws SQLException {
        List<GameInfo> allGames = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE;
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                allGames.add(readCurrentRecord(results));
            }
        }

        return allGames.toArray(new GameInfo[0]);
    }

    public static boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE game_id = ?";
        return AbstractSQL.delete(id, sql);
    }

    public static int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + TABLE;
        return AbstractSQL.countAll(sql);
    }

    private static GameInfo readCurrentRecord(ResultSet results) throws SQLException {
        GameInfo game = new GameInfo(
                results.getInt(1),
                results.getInt(4),
                results.getInt(6),
                results.getInt(7),
                results.getBoolean(8),
                results.getInt(3),
                results.getTimestamp(9).toLocalDateTime(),
                GameStatus.valueOf(results.getString(2)),
                PieceStyle.valueOf(results.getString(5))
        );
        game.setDurationInSeconds(results.getInt(10));
        return game;
    }

}
