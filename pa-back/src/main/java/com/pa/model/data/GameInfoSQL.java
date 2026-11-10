package com.pa.model.data;

import com.pa.model.creator.PieceStyle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class GameInfoSQL extends AbstractSQL<GameInfo> {

    protected GameInfoSQL() {
        super("games", "game_id", "game_id", "status", "template_id", "picture_id", "piece_style", "rows", "columns", "rotation", "start_timestamp", "duration_in_seconds");
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, GameInfo game) throws SQLException {
            statement.setInt(1, game.getId());
            statement.setString(2, game.getStatus().name());
            if (game.getTemplateId() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, game.getTemplateId());
            }
            statement.setInt(4, game.getPictureId());
            statement.setString(5, game.getPieceStyle().name());
            statement.setInt(6, game.getRows());
            statement.setInt(7, game.getColumns());
            statement.setBoolean(8, game.isRotation());
            statement.setTimestamp(9, Timestamp.valueOf(game.getStartTimestamp()));
            statement.setLong(10, game.getDurationInSeconds());
    }

    @Override
    protected GameInfo readCurrentRecord(ResultSet results) throws SQLException {
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
