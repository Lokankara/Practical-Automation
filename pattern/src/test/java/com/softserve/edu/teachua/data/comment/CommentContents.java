package com.softserve.edu.teachua.data.comment;

public enum CommentContents {
    DEFAULT_COMMENT("Проба Проба", "2024-06-22 10:59:59", "Проба Коментар", "Новий Кадр"),
    FIRST_COMMENT("Проба Проба", "2024-06-22 10:59:59", "Проба Коментар", "Новий Кадр"),
    SECOND_COMMENT("Проба Проба", "2024-06-22 10:59:59", "Проба Коментар44", "IT освіта: курси \"ГРАНД\"");

    private final String author;
    private final String datetime;
    private final String text;
    private final String club;

    private CommentContents(String author, String datetime, String text, String club) {
        this.author = author;
        this.datetime = datetime;
        this.text = text;
        this.club = club;
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

    public String getClub() {return club;}

    @Override
    public String toString() {
        return text;
    }
}
