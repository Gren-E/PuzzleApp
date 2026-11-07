package com.pa.model.data;

import com.pa.model.database.InternalDatabase;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PictureSQL {

    private static final String TABLE = "pictures";
    private static final String TAGS_TABLE = "pictures_tags";

    public static void insert(Picture picture) throws SQLException {
        String pictureSql = "INSERT INTO " + TABLE + " (picture_id, source_file, upload_timestamp) VALUES (?, ?, ?)";
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement pictureStatement = connection.prepareStatement(pictureSql);
            pictureStatement.setInt(1, picture.id());
            pictureStatement.setString(2, picture.sourceFile().getPath());
            pictureStatement.setTimestamp(3, Timestamp.valueOf(picture.uploadTimestamp()));
            pictureStatement.executeUpdate();
        }

        String tagsSql = "INSERT INTO " + TAGS_TABLE + " (picture_id, tag) VALUES (?, ?)";
        for (String tag : picture.tags()) {
            try (Connection connection = InternalDatabase.getInstance().getConnection()) {
                PreparedStatement tagStatement = connection.prepareStatement(tagsSql);
                tagStatement.setInt(1, picture.id());
                tagStatement.setString(2, tag);
                tagStatement.executeUpdate();
            }
        }
    }

    public static Picture[] selectAll() throws SQLException {
        List<Picture> allPictures = new ArrayList<>();

        String pictureSql = "SELECT * FROM " + TABLE;
        try(Connection connection = InternalDatabase.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(pictureSql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                allPictures.add(readCurrentPictureRecord(results));
            }
        }

        for (Picture picture : allPictures) {
            String tagsSql = "SELECT * FROM " + TAGS_TABLE + " WHERE picture_id = ? ";
            try (Connection connection = InternalDatabase.getInstance().getConnection()) {
                PreparedStatement statement = connection.prepareStatement(tagsSql);
                statement.setInt(1, picture.id());
                ResultSet results = statement.executeQuery();
                while (results.next()) {
                    picture.tags().add(results.getString(2));
                }
            }
        }

        return allPictures.toArray(new Picture[0]);
    }

    public static boolean delete(int id) throws SQLException {
        String pictureSql = "DELETE FROM " + TABLE + " WHERE template_id = ?";
        String tagsSql = "DELETE FROM " + TAGS_TABLE + " WHERE template_id = ?";

        return AbstractSQL.delete(id, pictureSql) && AbstractSQL.delete(id, tagsSql);
    }

    public static int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + TABLE;
        return AbstractSQL.countAll(sql);
    }

    private static Picture readCurrentPictureRecord(ResultSet results) throws SQLException {
        return new Picture(
                results.getInt(1),
                new File(results.getString(2)),
                results.getTimestamp(3).toLocalDateTime(),
                new HashSet<>()
        );
    }

}
