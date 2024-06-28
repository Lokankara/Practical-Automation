package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private DBUtil() {
    }

    public static Connection getConnection() {
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            return DriverManager
                    .getConnection(
                            String.format("jdbc:postgresql://%s:%s/%s", prop.getProperty("hostname"), prop.getProperty("port"),
                                    prop.getProperty("db.name")),
                            prop.getProperty("db.user"), prop.getProperty("db.password"));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
