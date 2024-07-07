package database;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.postgresql.util.PSQLException;
import utils.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
class DBParamsStructureTest {

    private static boolean isTableExists;
    private static boolean allColumnsExists;

    @BeforeAll
    public static void beforeAll() throws SQLException, IOException {
        new DBUtil().getUtil().executeFile("init.sql");
    }

    @AfterAll
    static void afterAll() throws SQLException, IOException {
        new DBUtil().getUtil().executeFile("destroy.sql");
    }

    static Stream<Arguments> provideValidTablesForFKs() {
        return Stream.of(
                Arguments.of("clubs", List.of("REFERENCES categories")),
                Arguments.of("club_child", List.of("REFERENCES clubs", "REFERENCES children"))
        );
    }

    static Stream<Arguments> invalidProvideFKs() {
        return Stream.of(
                Arguments.of("table", List.of("non_expected", "expected")),
                Arguments.of("non_existing_table", List.of())
        );
    }

    @Order(1)
    @ParameterizedTest(name = "Table: {0}")
    @ValueSource(strings = {"categories", "clubs", "children", "club_child"})
    void testExistTable(String tableName) throws SQLException {
        boolean actual = isTableExist(tableName);

        assertTrue(actual, String.format("Table %s doesn't exists", tableName));
        isTableExists = true;
    }

    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @MethodSource("provideValidTablesForFKs")
    void testCheckFKs(String tableName, List<String> expectedFKs) throws SQLException {
        List<String> actual = getFKs(tableName);
        SoftAssertions assertions = new SoftAssertions();

        expectedFKs.forEach(outer -> assertions.assertThat(actual.stream().anyMatch(e -> e.contains(outer)))
                .withFailMessage(String.format("Table %s should contain FK with %s", tableName, outer))
                .isTrue());
        assertions.assertAll();
    }

    @Order(3)
    @ParameterizedTest(name = "Table: {0}, column: {1}, type: {2}")
    @CsvFileSource(resources = "/table-data.csv", numLinesToSkip = 1)
    void testColumnType(String tableName, String columnName, String expectedType) throws SQLException {
        assumeTrue(allColumnsExists, "Skipping test for column type because the test for presence column failed.");

        String actual = getColumnType(tableName, columnName);
        assertEquals(expectedType, actual, String.format("Type for column %s should be %s", columnName, expectedType));
    }

    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @CsvSource({"categories, title", "clubs, title"})
    void testCheckUnique(String tableName, String columnName) throws SQLException {
        List<String> actual = getUnique(tableName);

        assertTrue(actual.contains(columnName),
                String.format("Column %s in table %s should be unique", columnName, tableName));
    }

    @Order(2)
    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @CsvSource({"categories, avatar", "clubs, title", "children, first_name", "club_child, club_id"})
    void testExistColumn(String tableName, String columnName) throws SQLException {
        assumeTrue(isTableExists, "Skipping test for column because the test for presence table failed.");
        boolean actual = isColumnInTableExist(tableName, columnName);

        assertTrue(actual, String.format("Column %s in table %s doesn't exists", columnName, tableName));
        allColumnsExists = true;
    }

    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @CsvSource({"categories, id", "clubs, id", "children, id"})
    void testCheckPK(String tableName, String expectedPK) throws SQLException {
        String actual = getPK(tableName);

        assertEquals(expectedPK, actual,
                String.format("Primary key for table %s should be named %s", tableName, expectedPK));
    }

    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @CsvSource({"categories, title"})
    void testCheckNotNull(String tableName, String columnName) throws SQLException {
        List<String> actual = getNotNull(tableName);

        assertTrue(actual.contains(columnName),
                String.format("Column %s in table %s should be not null", columnName, tableName));
    }

    @ParameterizedTest(name = "Table: {0}")
    @ValueSource(strings = {"categories", "clubs", "children", "club_child"})
    void testIsTableExistDoesNotThrow(String tableName) {

        assertDoesNotThrow(() -> assertTrue(isTableExist(tableName)),
                () -> String.format("Exception thrown for table: '%s'. The table should exist.", tableName));
    }

    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @CsvSource({"categories, id", "clubs, id", "children, id"})
    void testGetPKDoesNotThrow(String tableName, String expectedPK) {

        assertDoesNotThrow(() -> assertEquals(expectedPK, getPK(tableName)),
                () -> String.format("Exception thrown while retrieving primary key for table: '%s'.", tableName));
    }

    @ParameterizedTest(name = "Table: {0}")
    @CsvSource({"' '", "null"})
    void testGetNullTest(String tableName) {

        assertDoesNotThrow(() -> getNotNull(tableName),
                () -> String.format("Exception thrown for null or empty table name: '%s'.", tableName));
    }

    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @CsvSource({"table, column", " , "})
    void testGetUniqueTestWithInvalidDataDoesNotThrow(String tableName, String columnName) {

        assertDoesNotThrow(() -> getUnique(tableName).contains(columnName),
                () -> String.format("Exception thrown while checking uniqueness of column: '%s' in table: '%s'.", columnName, tableName));
    }

    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @CsvSource({"table, column", "non_exist_table, "})
    void testIsColumnInTableExistWithInvalidDataDoesNotThrow(String tableName, String columnName) {

        assertDoesNotThrow(() -> isColumnInTableExist(tableName, columnName),
                () -> String.format("Exception thrown while checking existence of column: '%s' in table: '%s'.", columnName, tableName));
    }

    @ParameterizedTest(name = "Table: {0}")
    @ValueSource(strings = {"non_exist", " ", "null"})
    void testIsTableExistInvalidTestDoesNotThrow(String tableName) {

        assertDoesNotThrow(
                () -> assertFalse(isTableExist(tableName), String.format("Expected '%s' to not exist.", tableName)),
                () -> String.format("Exception thrown while checking existence of table: '%s'.", tableName));
    }

    @ParameterizedTest(name = "Table: {0}, column: {1}, type: {2}")
    @CsvFileSource(resources = "/table-data.csv", numLinesToSkip = 1)
    void testGetColumnTypeValidTestDoesNotThrow(String tableName, String columnName, String expectedType) {

        assertDoesNotThrow(
                () -> assertEquals(expectedType, getColumnType(tableName, columnName),
                        String.format("Unexpected column type for table: '%s', column: '%s'.", tableName, columnName)),
                () -> String.format("Exception thrown while retrieving column type for table: '%s', column: '%s'.", tableName, columnName)
        );
    }

    @ParameterizedTest(name = "Table: {0}")
    @MethodSource("provideValidTablesForFKs")
    void testCheckFKsValidDoesNotThrow(String tableName) {

        assertDoesNotThrow(
                () -> assertNotNull(getFKs(tableName),
                        String.format("Foreign keys are null for table: '%s'.", tableName)),
                () -> String.format("Exception thrown while checking foreign keys for table: '%s'.", tableName)
        );
    }

    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @CsvSource({"categories, id", "clubs, id", "children, id",})
    void testCheckPKValidTest(String tableName, String expectedPK) {

        assertDoesNotThrow(() -> assertEquals(expectedPK, getPK(tableName),
                        String.format("Unexpected primary key for table: '%s'. Expected: '%s'", tableName, expectedPK)),
                () -> String.format("Exception thrown while checking primary key for table: '%s'.", tableName)
        );
    }

    @ParameterizedTest(name = "Table: {0}")
    @ValueSource(strings = {"categories", "clubs", "children", "club_child"})
    void testIsTableExistValidDataDoesNotThrow(String tableName) {
        assertDoesNotThrow(() -> assertTrue(isTableExist(tableName),
                        String.format("Table does not exist: '%s'.", tableName)),
                () -> String.format("Exception thrown while checking existence of table: '%s'.", tableName));
    }

    @ParameterizedTest(name = "Table: {0}")
    @MethodSource("invalidProvideFKs")
    void testGetFKsTestWithInvalidDoesThrowsPSQLException(String tableName) {

        assertThrows(PSQLException.class, () -> getFKs(tableName),
                () -> String.format("Expected PSQLException while retrieving foreign keys for table: '%s'.", tableName));
    }

    @Order(3)
    @ParameterizedTest(name = "Table: {0}, column: {1}")
    @CsvSource({"table, column", "non_table, ", " , "})
    void testGetColumnTypeWithInvalidDataThrowsPSQLException(String tableName, String columnName) {

        assertThrows(PSQLException.class, () -> getColumnType(tableName, columnName),
                () -> String.format("Expected PSQLException while retrieving column type for table: '%s', column: '%s'.", tableName, columnName));
    }

    @ParameterizedTest(name = "Table: {0}")
    @MethodSource("invalidProvideFKs")
    void testCheckFKsInvalidThrowsPSQLException(String tableName) {

        assertThrows(PSQLException.class, () -> getFKs(tableName),
                () -> String.format("Expected PSQLException while checking foreign keys for table: '%s'.", tableName));
    }

    @ParameterizedTest(name = "Table: {0}")
    @CsvSource({"non_existing_table, ''"})
    void testCheckPKInvalidThrowsPSQLException(String tableName) {

        assertThrows(PSQLException.class, () -> getPK(tableName),
                () -> String.format("Expected PSQLException while checking primary key for table: '%s'.", tableName));
    }
}
