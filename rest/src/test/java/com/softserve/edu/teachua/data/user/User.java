package com.softserve.edu.teachua.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;

interface IId {
    IFirstName setId(Integer id);
}

interface IFirstName {
    ILastName setFirstName(String firstName);
}

interface ILastName {
    IPhoneNumber setLastName(String lastName);
}

interface IPhoneNumber {
    IEmail setPhoneNumber(String phoneNumber);
}

interface IEmail {
    IPassword setEmail(String email);
}

interface IPassword {
    IBuildUser setPassword(String password);
}

interface IBuildUser {
    IBuildUser setVisitor(boolean visitor);

    IUser build();
}

public class User implements IId, IFirstName, ILastName, IPhoneNumber, IEmail, IPassword, IBuildUser, IUser {

    private Integer id;
    private String firstName;
    private String lastName;
    @JsonProperty("phone")
    private String phoneNumber;
    private String email;
    private String password;
    private boolean isVisitor;

    private User() {
        isVisitor = true;
    }

    public static IId get() {
        return new User();
    }

    // setters

    @Override
    public IFirstName setId(Integer id) {
        this.id = id;
        return this;
    }

    public ILastName setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public IPhoneNumber setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public IEmail setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public IPassword setEmail(String email) {
        this.email = email;
        return this;
    }

    public IBuildUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public IBuildUser setVisitor(boolean visitor) {
        isVisitor = visitor;
        return this;
    }

    public IUser build() {
        return this;
    }

    // getters

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVisitor() {
        return isVisitor;
    }
}
