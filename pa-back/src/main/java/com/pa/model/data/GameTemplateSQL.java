package com.pa.model.data;

import com.pa.model.creator.PieceStyle;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GameTemplateSQL extends AbstractSQL<GameTemplate> {

    protected GameTemplateSQL() {
        super("game_templates","template_id", "template_id", "picture_id", "piece_style", "rows", "columns", "rotation");
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, GameTemplate template) throws SQLException {
        statement.setInt(1, template.id());
        statement.setInt(2, template.pictureId());
        statement.setString(3, template.style().name());
        statement.setInt(4, template.rows());
        statement.setInt(5, template.columns());
        statement.setBoolean(6, template.canPiecesRotate());
    }

    @Override
    protected GameTemplate readCurrentRecord(ResultSet results) throws SQLException {
        return new GameTemplate(
                results.getInt(1),
                results.getInt(2),
                PieceStyle.valueOf(results.getString(3)),
                results.getInt(4),
                results.getInt(5),
                results.getBoolean(6)
        );
    }

    public static void main(String[] args) throws SQLException {
        GameTemplateSQL sql = new GameTemplateSQL();
        GameTemplate t1 = new GameTemplate(1, 34, PieceStyle.RECTANGLE, 12, 43, true);
        GameTemplate t2 = new GameTemplate(2, 34, PieceStyle.CLASSIC, 16, 34, false);


        System.out.println(sql.countAll());
        sql.insert(t1);
        sql.insert(t2);
        System.out.println(sql.countAll());
        sql.selectAll().forEach(t -> System.out.println(t.toString()));
        System.out.println(sql.delete(1));
        System.out.println(sql.delete(2));
        System.out.println(sql.delete(2));
        System.out.println(sql.countAll());
    }

}
