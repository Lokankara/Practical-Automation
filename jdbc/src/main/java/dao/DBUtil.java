package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private DBUtil(){}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://oregon-postgres.render.com/selenium_j1pm", "admin", "WKb5NQQAGwOs50zb3kroalITQqoFsnhY"
        );
    }
}
