package com.softserve.edu.teachua.data.user;

public interface IUser {
    String getFirstName();

    String getLastName();

    String getPhoneNumber();

    String getEmail();

    String getPassword();

    boolean isVisitor();

    int getId();
}
