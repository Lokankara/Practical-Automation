package com.softserve.edu.teachua.data.comment;

import com.softserve.edu.teachua.data.club.IClub;

interface IAuthor {
    IDatetime setAuthor(String author);
}

interface IDatetime {
    IText setDatetime(String datetime);
}

interface IText {
    IClubs setText(String text);
}

interface IClubs {
    IBuildComment setClub(IClub club);
}

interface IBuildComment {
    IComment build();
}

public class Comment implements IAuthor, IDatetime, IText, IClubs, IBuildComment, IComment {

    private String author;
    private String datetime;
    private String text;
    private IClub club;

    public static IAuthor get() {
        return new Comment();
    }

    @Override
    public IDatetime setAuthor(String author) {
        this.author = author;
        return this;
    }

    @Override
    public IText setDatetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    @Override
    public IClubs setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public IBuildComment setClub(IClub club) {
        this.club = club;
        return this;
    }

    @Override
    public IComment build() {
        return this;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getDatetime() {
        return datetime;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IClub getClub() {
        return club;
    }

    @Override
    public String toString() {
        return String.format("Comment{author='%s', datetime='%s', text='%s', club=%s}",
                author, datetime, text, club);
    }
}
