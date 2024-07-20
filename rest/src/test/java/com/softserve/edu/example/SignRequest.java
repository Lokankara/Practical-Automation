package com.softserve.edu.example;

record SignRequest(
        int id,
        String email,
        String firstName,
        String lastName,
        String phone,
        String password,
        String roleName,
        String verificationCode,
        String urlLogo,
        String status) {

    public SignRequest(String email, String firstName, String lastName, String phone, String password) {
        this(0, email, firstName, lastName, phone, password, "ROLE_USER", "", "", "");
    }
}
