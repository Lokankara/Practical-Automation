package com.softserve.edu.teachua.data.user;

import com.softserve.edu.teachua.data.comment.CommentContents;

import java.util.List;

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
    IComments setPassword(String password);
}

interface IComments {
    IBuildUser setComments(List<CommentContents> comments);
}

interface IBuildUser {
    IBuildUser setVisitor(boolean visitor);

    IUser build();
}

public class User implements IFirstName, ILastName, IPhoneNumber, IEmail, IPassword, IComments, IBuildUser, IUser {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private List<CommentContents> comments;
    private boolean isVisitor;

    private User() {
        isVisitor = true;
    }

    public static IFirstName get() {
        return new User();
    }

    @Override
    public ILastName setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public IPhoneNumber setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public IEmail setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public IPassword setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public IComments setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public IBuildUser setComments(List<CommentContents> comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public IBuildUser setVisitor(boolean visitor) {
        isVisitor = visitor;
        return this;
    }

    @Override
    public IUser build() {
        return this;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<CommentContents> getComments() {
        return comments;
    }

    @Override
    public boolean isVisitor() {
        return isVisitor;
    }

    @Override
    public String toString() {
        return String.format("User{firstName='%s', lastName='%s', phoneNumber='%s', email='%s', password='%s', comments=%s, isVisitor=%s}",
                firstName, lastName, phoneNumber, email, password, comments, isVisitor);
    }
}
