package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://oregon-postgres.render.com/selenium", "admin", "xsy0WYIZQm8W2qgTQH0FK5lJaln4ZJwk"
        );
    }

    public static int totalCount(String tableName) throws SQLException {
        String query = "SELECT count(*) FROM " + tableName;
        try (Statement statement = getConnection().createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public void executeFile(String path) throws IOException, SQLException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);


        assert inputStream != null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

             Statement statement = getConnection().createStatement()) {

            StringBuilder builder = new StringBuilder();

            String line;
            int lineNumber = 0;
            int count = 0;

            while ((line = bufferedReader.readLine()) != null) {
                lineNumber += 1;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("--"))
                    continue;

                builder.append(line);
                if (line.endsWith(";"))
                    try {
                        statement.execute(builder.toString());
                        System.err.println(
                                ++count
                                        + " Command successfully executed : "
                                        + builder.substring(
                                        0,
                                        Math.min(builder.length(), 15))
                                        + "...");
                        builder.setLength(0);
                    } catch (SQLException e) {
                        System.err.println(
                                "At line " + lineNumber + " : "
                                        + e.getMessage() + "\n");
                        return;
                    }
            }

        }
    }
}
