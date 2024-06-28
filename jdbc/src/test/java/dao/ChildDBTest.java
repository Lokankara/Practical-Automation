package dao;

import model.Child;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

class ChildDBTest {
    private final ChildDB childDB = new ChildDB();
    private final Child child = new Child(null, "Test", "Child", LocalDate.now());

    @BeforeAll
    public static void beforeAll() throws SQLException, IOException {
        new DBUtil().executeFile("init.sql");
    }

    @Test
    @DisplayName("Test a new child, when added to the database, then it should have the same first name, last name, and birth date, and a non-null ID")
    void testAdd() throws SQLException {

        Child addedChild = childDB.add(child);

        Assertions.assertNotNull(addedChild.id());
        Assertions.assertEquals(child.firstName(), addedChild.firstName());
        Assertions.assertEquals(child.lastName(), addedChild.lastName());
        Assertions.assertEquals(child.birthDate(), addedChild.birthDate());
    }

    @Test
    @DisplayName("Test a new child, when added to the database, then the total count of children should increase by 1")
    void testIncrementChildCountWhenAddNewChild() throws SQLException {
        final int oldCount = utils.DBUtil.totalCount("child");

        childDB.add(child);

        int newCount = utils.DBUtil.totalCount("child");
        Assertions.assertEquals(newCount, oldCount, "The difference between two numbers");
    }

    @Test
    @DisplayName("Test a child, when updated in the database, then it should return true")
    void testUpdate() throws SQLException {
        Child firstChild = new Child(1L, "Create", "Child", LocalDate.now());
        Child added = childDB.add(firstChild);
        firstChild = new Child(added.id(), "First", "Test", LocalDate.now());

        boolean isUpdated = childDB.update(firstChild);

        Assertions.assertTrue(isUpdated);
    }

    @Test
    @DisplayName("Test a child, when updated in the database, then it should return true")
    void testUpdateChild() throws SQLException {
        final Child firstChild = new Child(1L, "Test", "Child", LocalDate.now());

        boolean actual = childDB.update(firstChild);

        Assertions.assertTrue(actual, "Should returns true if it was updated just one record");
    }

    @Test
    @DisplayName("Test two children with different ages, when allAtLeastAge method is called, then it should return the correct number of children who are at least a certain age")
    void testAllAtLeastAge() throws SQLException {
        final Child child1 = new Child(null, "Test", "Child1", LocalDate.now().minusYears(5));
        final Child child2 = new Child(null, "Test", "Child2", LocalDate.now().minusYears(10));
        childDB.add(child1);
        childDB.add(child2);

        List<Child> children = childDB.allAtLeastAge(6);

        Assertions.assertEquals(1, children.size());
        Assertions.assertEquals(child2.firstName(), children.get(0).firstName());
    }

    @Test
    @DisplayName("Test a child without a birth date, when added to the database, then the database should contain the expected number of records without a birth date")
    void testAllWithoutBirthDate() throws SQLException {
        final Child child2 = new Child(null, "Second", "Child2", null);
        childDB.add(child);
        childDB.add(child2);

        List<Child> children = childDB.allWithoutBirthDate(0);

        Assertions.assertEquals(1, children.size());
        Assertions.assertEquals(child2.firstName(), children.get(0).firstName());
    }

    @Test
    @DisplayName("Test a child without a birth date, when added to the database, then the database should contain the expected number of records without a birth date")
    void testAllWithoutBirthDateChild() throws SQLException {
        final int expected = 1;
        childDB.add(child);

        int actual = childDB.allWithoutBirthDate(0).size();

        Assertions.assertEquals(expected, actual, String.format("Database should contain %d records without birth date", expected));
    }

    @Test
    @DisplayName("Test a child added to the database, when deleted, then the operation should return true")
    void testDelete() throws SQLException {
        Child added = childDB.add(child);

        boolean isDeleted = childDB.delete(added.id());

        Assertions.assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Test a child added to the database, when deleted, then the total count of children should decrement by 1")
    void testDecrementChildCountWhenAddDeleteChild() throws SQLException {
        final Child child = new Child(1L, "Test", "Child", LocalDate.now());
        final int expected = 0;
        Child added = childDB.add(child);
        int count = utils.DBUtil.totalCount("child");
        Assertions.assertEquals(expected, count, String.format("Database should contain %d record", expected));

        boolean actual = childDB.delete(added.id());

        count = utils.DBUtil.totalCount("child");
        Assertions.assertTrue(actual, "Should returns true if it was delete just one record");
        Assertions.assertEquals(expected, count, String.format("Database should contain %d record", expected));
    }
}
