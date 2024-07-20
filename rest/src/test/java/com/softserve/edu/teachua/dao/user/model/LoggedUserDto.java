package com.softserve.edu.teachua.dao.user.model;

import com.github.javafaker.Faker;

public record LoggedUserDto(Integer id, String firstName, String lastName, String phone,
                            String email, String password, String roleName,
                            String urlLogo, String status) {

    public static LoggedUserDto createUser() {
        Faker faker = new Faker();
        String randomEmail = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String randomPassword = "Password123!";
        String roleUser = "ROLE_USER";
        String phone = "0997890123";

        return new LoggedUserDto(0, firstName, lastName, phone, randomEmail, randomPassword, roleUser, "", "");
    }

    public static LoggedUserDto createUser(LoggedUserDto newUser, int id) {
        return new LoggedUserDto(id, newUser.firstName(), newUser.lastName(), newUser.phone(),
                newUser.email(), newUser.password(), newUser.roleName(), newUser.urlLogo(), newUser.status());
    }

    public static LoggedUserDto empty() {
        return new LoggedUserDto(0, "", "", "", "", "", "", "", "");
    }
}
