package com.pa.model.data;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;

public class PictureSQL extends AbstractSQL<Picture> {

    private static final String TAGS_TABLE = "picture_tags";

    protected PictureSQL() {
        super("pictures", "picture_id", "picture_id", "source_file", "upload_timestamp");
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, Picture picture) throws SQLException {
        statement.setInt(1, picture.id());
        statement.setString(2, picture.sourceFile().getPath());
        statement.setTimestamp(3, Timestamp.valueOf(picture.uploadTimestamp()));
    }

    @Override
    protected void afterInsertion(Connection connection, Picture picture) throws SQLException {
        String tagsSql = "INSERT INTO " + TAGS_TABLE + " (picture_id, tag) VALUES (?, ?)";
        for (String tag : picture.tags()) {
            PreparedStatement tagStatement = connection.prepareStatement(tagsSql);
            tagStatement.setInt(1, picture.id());
            tagStatement.setString(2, tag);
            tagStatement.executeUpdate();
        }
    }

    @Override
    protected void enrichObject(Connection connection, Picture picture) throws SQLException {
        String tagsSql = "SELECT * FROM " + TAGS_TABLE + " WHERE picture_id = ? ";
        PreparedStatement statement = connection.prepareStatement(tagsSql);
        statement.setInt(1, picture.id());
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            picture.tags().add(results.getString(2));
        }
    }

    @Override
    protected void afterDeletion(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM " + TAGS_TABLE + " WHERE picture_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    protected Picture readCurrentRecord(ResultSet results) throws SQLException {
        return new Picture(
                results.getInt(1),
                new File(results.getString(2)),
                results.getTimestamp(3).toLocalDateTime(),
                new HashSet<>()
        );
    }

}
