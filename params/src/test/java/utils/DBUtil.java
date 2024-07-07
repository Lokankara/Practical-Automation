package utils;

import database.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DBUtil {
    private static Connection connection;
    private static Util util;

    public DBUtil() {
        if (util == null) {
            try {
                util = new Util(getConnection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Util getUtil() {
        return util;
    }

    public DBUtil(Connection connection) {
        DBUtil.connection = connection;
        util = new Util(connection);
    }

    public static Connection getConnection() throws SQLException {
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            return DriverManager
                    .getConnection(
                            String.format("jdbc:postgresql://%s:%s/%s", prop.getProperty("hostname"), prop.getProperty("port"),
                                    prop.getProperty("db.name")),
                            prop.getProperty("db.user"), prop.getProperty("db.password"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isTableExist(String tableName) throws SQLException {
        return util.executeQueryOneBoolean(String.format("SELECT EXISTS ( SELECT 1 FROM pg_tables\n" +
                "            WHERE tablename = '%s');", tableName));
    }

    public static String getColumnType(String tableName, String columnName) throws SQLException {
        return util.executeQueryOneString(String.format("SELECT data_type\n" +
                "FROM information_schema.columns WHERE table_name = '%s' and column_name = '%s';", tableName, columnName));
    }

    public static String getPK(String tableName) throws SQLException {
        return util.executeQueryOneString(String.format("SELECT pg_attribute.attname\n" +
                "    FROM pg_class, pg_attribute, pg_index\n" +
                "    WHERE pg_class.oid = pg_attribute.attrelid AND\n" +
                "    pg_class.oid = pg_index.indrelid AND\n" +
                "    pg_index.indkey[0] = pg_attribute.attnum AND\n" +
                "    pg_index.indisprimary = 't' and pg_class.relname = '%s';", tableName));
    }

    public static List<String> getFKs(java.lang.String tableName) throws SQLException {
        return util.executeQueryListString(java.lang.String.format("SELECT pg_catalog.pg_get_constraintdef(r.oid, true) as condef\n" +
                "FROM pg_catalog.pg_constraint r\n" +
                "WHERE r.conrelid = '%s'::regclass AND r.contype = 'f' ORDER BY 1", tableName));
    }

    public static java.util.List<String> getNotNull(String tableName) throws SQLException {
        return util.executeQueryListString(String.format("SELECT column_name FROM information_schema.columns\n" +
                "WHERE is_nullable = 'NO' and table_name = '%s';", tableName));
    }

    public static List<String> getUnique(String tableName) throws SQLException {
        return util.executeQueryListString(String.format("SELECT column_name\n" +
                "    FROM information_schema.constraint_column_usage\n" +
                "    WHERE table_name = '%s'\n" +
                "    AND constraint_name IN (\n" +
                "            SELECT constraint_name\n" +
                "    FROM information_schema.table_constraints\n" +
                "            WHERE constraint_type = 'UNIQUE'\n" +
                "    );", tableName));
    }

    public static boolean isColumnInTableExist(String tableName, String columnName) throws SQLException {
        return util.executeQueryOneBoolean("SELECT EXISTS (SELECT 1\n" +
                "               FROM information_schema.columns\n" +
                "               WHERE table_schema='public' AND table_name='" + tableName + "' AND column_name='" + columnName + "');");
    }
}
