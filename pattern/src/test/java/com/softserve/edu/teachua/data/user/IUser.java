package com.softserve.edu.teachua.data.user;

import com.softserve.edu.teachua.data.comment.CommentContents;

import java.util.List;

public interface IUser {
    String getFirstName();

    String getLastName();

    String getPhoneNumber();

    String getEmail();

    String getPassword();

    List<CommentContents> getComments();

    boolean isVisitor();
}
