package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private DBUtil(){}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://oregon-postgres.render.com/selenium", "admin", "xsy0WYIZQm8W2qgTQH0FK5lJaln4ZJwk"
        );
    }
}
