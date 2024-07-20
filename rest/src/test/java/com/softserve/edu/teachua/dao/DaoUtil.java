package com.softserve.edu.teachua.dao;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DaoUtil {

    private static final Dotenv dotenv = Dotenv.load();

    private DaoUtil() {
    }

    public static String getPassword() {
        String password = System.getenv("DATASOURCE_PASSWORD");
        return password != null ? password : dotenv.get("DATASOURCE_PASSWORD");
    }

    public static String getUrl() {
        String url = System.getenv("DATASOURCE_URL");
        return url != null ? url : dotenv.get("DATASOURCE_URL");
    }

    public static String getUser() {
        String user = System.getenv("DATASOURCE_USER");
        return user != null ? user : dotenv.get("DATASOURCE_USER");
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(getUrl(), getUser(), getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
