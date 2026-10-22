package com.pa.model.data;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class representing a single picture in the catalog, available for puzzle creation.
 * @author Ewelina Gren
 * @version 1.0 TODO
 */
public record Picture(int id, File sourceFile, LocalDateTime uploadTimestamp, Set<String> tags) {

    /**
     * Adds a tag related to the picture, allowing the user to search the catalog by keyword.
     * @param tag a tag describing the picture
     */
    public void addTag(String tag) {
        tags.add(tag);
    }

    /**
     * Checks if a certain tag is assigned to the picture.
     * @param tag a tag to be checked
     * @return {@code true} if the tag is assigned to the picture or {@code false} otherwise
     */
    public boolean containsTag(String tag) {
        return tags.contains(tag);
    }

    @Override
    public String toString() {
        return "Picture{id=" + id + ", sourceFile='" + sourceFile + "'}";
    }

}
