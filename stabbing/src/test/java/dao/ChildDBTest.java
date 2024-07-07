package dao;

import model.Child;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import utils.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChildDBTest {

    private static ChildDB db;

    @BeforeAll
    public static void beforeAll() throws SQLException, IOException {
        db = new ChildDB(DBUtil.getConnection());
        new DBUtil().executeFile("init.sql");
    }

    @Test
    @Order(1)
    void testGeneratedIdWhenAddChild() throws SQLException {
        int oldCount = DBUtil.totalCount("categories");
        var newChild = new Child("First Name", "LastName", LocalDate.now().minusYears(3));
        newChild = db.add(newChild);
        assertNotNull(newChild.id(), "after add Child should contains id");
    }

    @Test
    void testGeneratedIdWhenAddChildWithNullBirthDate() throws SQLException {
        int oldCount = DBUtil.totalCount("categories");
        var newChild = new Child("First Name", "LastName", null);
        newChild = db.add(newChild);
        assertNotNull(newChild.id(), "after add Child should contains id");
    }

    @Test
    void testOneWhenUpdateExistingUser() throws SQLException {
        var firstChild = new Child(1L, "some", "some", null);
        boolean actual = db.update(firstChild);
        assertTrue(actual, "Should returns true if it was updated just one record");
    }

    @Test
    void testTrueWhenDeleteExistingUser() throws SQLException {
        boolean actual = db.delete(4L);
        assertTrue(actual, "Should returns true if it was delete just one record");
    }

    @Test
    void testNotEmptyListWhenAtLeastAgeTwenty() throws SQLException {
        Child expected = new Child(2L, "First name 2 child", "Last Name 2 child", LocalDate.parse("2000-01-01"));

        List<Child> actual = db.allAtLeastAge(20);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual).size().isEqualTo(1);
        softly.assertThat(actual)
                .filteredOn(child ->
                        child.id().equals(expected.id())
                                && child.firstName().equals(expected.firstName())
                                && child.lastName().equals(expected.lastName())
                ).hasSize(1);
        softly.assertAll();
    }

    @Test
    void testNotEmptyListWhenWithoutBirthDate() throws SQLException {
        Child expected = new Child(1L, "First name child", "Last name child", null);

        List<Child> actual = db.allWithoutBirthDate();
        assertThat(actual).isNotEmpty();
    }

    @Test
    void testNotGetNonExistingChild() throws SQLException {
        var child = db.get(1000L);

        assertNull(child, "Should return null if the child does not exist");
    }

    @Test
    @Order(2)
    void testGetByFirstName() throws SQLException {
        String expected = "First Name";
        List<Child> actual = db.getByName(expected);
        assertThat(actual).isNotEmpty();
        List<Child> foundBothNames = db.getByName(expected);
        assertThat(foundBothNames).hasSize(2);
    }

    @Test
    void testGetByNameNonExistentName() throws SQLException {
        List<Child> notFound = db.getByName("Third Name");
        assertThat(notFound).isEmpty();
    }
}
