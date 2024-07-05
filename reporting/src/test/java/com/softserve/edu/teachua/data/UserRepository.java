package com.softserve.edu.teachua.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class UserRepository {
    private final String TIME_TEMPLATE = "HH_mm_ss_S";
    //
    private static volatile UserRepository instance = null;

    private UserRepository() {
    }

    public static UserRepository get() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public IUser defaultUser() {
        return customer();
    }

    public IUser admin() {
        return User.get()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setPhoneNumber("PhoneNumber")
                .setEmail("email")
                .setPassword("password")
                .build();
    }

    public IUser customer() {
        return User.get()
                .setFirstName("Проба")
                .setLastName("Проба")
                .setPhoneNumber("0671234567")
                .setEmail("yagifij495@eqvox.com")
                .setPassword("Qwerty_1")
                .setVisitor(true)
                .build();
    }

    public IUser user() {
        return User.get()
                .setFirstName("user")
                .setLastName("userl")
                .setPhoneNumber("00000000")
                .setEmail("user@gmail.com")
                .setPassword("user")
                .setVisitor(true)
                .build();
    }

    public IUser newUser() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        String newEmail = currentTime + "@gmail.com";
        return User.get()
                .setFirstName("user")
                .setLastName("userl")
                .setPhoneNumber("00000000")
                .setEmail("user" + currentTime + "@gmail.com")
                .setPassword(System.getenv().get("NEW_PASSWORD"))
                .setVisitor(true)
                .build();
    }

    public IUser invalidUser() {
        return User.get()
                .setFirstName("user1")
                .setLastName("user1")
                .setPhoneNumber("1111111111")
                .setEmail("hahaha@gmail.com")
                .setPassword("qwerty")
                .setVisitor(true)
                .build();
    }

    // public List<IUser> fromCsv() {}
    // public List<IUser> fromExcel() {}
    // public List<IUser> fromDB() {}
}
