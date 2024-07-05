package com.softserve.edu.teachua.data;

public enum CommentContents {
    DEFAULT_COMMENT("Проба Проба", "2024-06-22 10:59:59", "Проба Коментар"),
    FIRST_COMMENT("Проба Проба", "2024-06-22 10:59:59", "Проба Коментар");

    private String author;
    private String datetime;
    private String text;

    private CommentContents(String author, String datetime, String text) {
        this.author = author;
        this.datetime = datetime;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

}
