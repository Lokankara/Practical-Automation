package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {


    public static Connection getConnection() throws SQLException {
        String configFile = "src/main/resources/db.properties";

        HikariConfig cfg = new HikariConfig(configFile);
        HikariDataSource ds = new HikariDataSource(cfg);
        return ds.getConnection();

    }

    public static int executeStatement(String query) throws SQLException {
        Statement st = getConnection().createStatement();
        return st.executeUpdate(query);
    }

}
