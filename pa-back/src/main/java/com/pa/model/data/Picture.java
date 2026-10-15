package com.pa.model.data;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

public record Picture(int id, File sourceFile, LocalDateTime uploadTimestamp, Set<String> tags) {

    public void addTag(String tag) {
        tags.add(tag);
    }

    public boolean containsTag(String tag) {
        return tags.contains(tag);
    }

    @Override
    public String toString() {
        return "Picture{id=" + id + ", sourceFile='" + sourceFile + "'}";
    }

}
