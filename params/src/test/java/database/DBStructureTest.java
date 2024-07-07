package database;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import utils.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static utils.DBUtil.getColumnType;
import static utils.DBUtil.getFKs;
import static utils.DBUtil.getNotNull;
import static utils.DBUtil.getPK;
import static utils.DBUtil.getUnique;
import static utils.DBUtil.isColumnInTableExist;
import static utils.DBUtil.isTableExist;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBStructureTest {
    private static boolean allColumnsExists = false;

    @BeforeAll
    public static void beforeAll() throws SQLException, IOException {
        new DBUtil().getUtil().executeFile("destroy.sql");
        new DBUtil().getUtil().executeFile("init.sql");
    }

    @Test
    void existTable() throws SQLException {
        boolean actual = isTableExist("categories");
        assertTrue(actual, "Table categories doesn't exists");
    }

    @AfterAll
    static void afterAll() throws SQLException, IOException {
        new DBUtil().getUtil().executeFile("destroy.sql");
    }

    @Test
    @Order(1)
    void existColumn() throws SQLException {
        boolean actual = isColumnInTableExist("categories", "avatar");
        assertTrue(actual, "Column avatar in table categories doesn't exists");
        allColumnsExists = true;
    }


    @Test
    @Order(2)
    void columnType() throws SQLException {
        assumeTrue(allColumnsExists, "Skipping test for column type because the test for presence column failed.");

        String expected = "character varying";
        String actual = getColumnType("categories", "avatar");
        assertEquals(expected, actual, String.format("Type for column %s should be %s", "avatar", expected));
    }

    @Test
    void checkPK() throws SQLException {
        String expected = "id";
        String actual = getPK("categories");
        assertEquals(expected, actual, String.format("PK for table %s should be named %s", "categories", expected));
    }

    @Test
    void checkFKs() throws SQLException {
        List<String> actual = getFKs("club_child");
        List<String> expected = List.of("REFERENCES child", "REFERENCES clubs");
        String tableName = "club_child";

        SoftAssertions assertions = new SoftAssertions();
        expected.forEach(outer -> assertions.assertThat(actual.stream().anyMatch(e -> e.contains(outer)))
                .withFailMessage(String.format("Table %s should contain FK with %s", "club_child", outer))
                .isTrue());
        assertions.assertAll();

        assertAll("Check if FK present",
                () -> assertTrue(actual.stream().anyMatch(e -> e.contains(expected.get(0))),
                        String.format("Table %s should contain FK with %s", tableName, expected.get(0))),
                () -> assertTrue(actual.stream().anyMatch(e -> e.contains(expected.get(1))),
                        String.format("Table %s should contain FK with %s", tableName, expected.get(1))));
    }

    @Test
    void checkNotNull() throws SQLException {
        List<String> actual = getNotNull("categories");
        assertTrue(actual.contains("title"), "Column title in table categories should be not null");
    }


    @Test
    void checkUnique() throws SQLException {
        List<String> actual = getUnique("clubs");
        assertTrue(actual.contains("title"), "Column title in table clubs should be unique");
    }

}