package com.softserve.edu.teachua.data;

public enum CommentContents {
    DEFAULT_COMMENT("Проба Проба", "2024-06-22 10:59:59", "Проба Коментар"),
    FIRST_COMMENT("Проба Проба", "2024-06-22 10:59:59", "Проба Коментар44");

    private final String author;
    private final String datetime;
    private final String text;

    CommentContents(String author, String datetime, String text) {
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
        return String.format(
                "CommentContents{author='%s', datetime='%s', text='%s'}",
                author, datetime, text);
    }
}
