package dao;

import model.Category;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryDBTest {

    private final CategoryDB db = new CategoryDB();

    @BeforeAll
    public static void beforeAll() throws SQLException, IOException {
        new DBUtil().executeFile("init.sql");
    }

    @Test
    @DisplayName("Test increment category count when add new category")
    void testIncrementCategoryCountWhenAddNewCategory() throws SQLException {
        int oldCount = DBUtil.totalCount("categories");
        var newCategory = new Category("avatar", "title");

        db.add(newCategory);

        int newCount = DBUtil.totalCount("categories");
        assertEquals(newCount, oldCount+1, "the difference between two numbers");
    }

    @Test
    @DisplayName("Test unchanged category count when add category with existing title")
    void testUnchangedCategoryCountWhenAddCategoryWithExistingTitle() throws SQLException {
        int oldCount = DBUtil.totalCount("categories");
        var newCategory = new Category("avatar", "Old Category");

        Category added = db.add(newCategory);

        int newCount = DBUtil.totalCount("categories");
        assertEquals(newCount, oldCount, "The count categories should not be change");
        Assertions.assertNull(added);
    }

    @Test
    @DisplayName("Given a category, when updated in the database, then it should return true")
    void testUpdate() throws SQLException {
        var firstCategory = new Category(1L, "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII", "New Old Category");

        boolean actual = db.update(firstCategory);

        assertTrue(actual, "Should returns true if it was updated just one record");
    }

    @Test
    @DisplayName("Given a category ID, when deleted from the database, then it should return true")
    void testDelete() throws SQLException {

        boolean actual = db.delete(2L);

        assertTrue(actual, "Should returns true if it was delete just one record");
    }

    @Test
    @DisplayName("Given a part of a title, when searched in the database, then it should return the correct number of records containing that part")
    void testTitlePart() throws SQLException {
        final int expected = 2;
        final String searchedPart = "uniquE";

        int actual = db.titlePart(searchedPart).size();

        assertEquals(expected, actual, String.format("Database should contain %d records with %s word ", expected, searchedPart));
    }

    @Test
    @DisplayName("Given a category ID, when allChildren method is called, then it should return all children")
    void testAllChildren() throws SQLException {
        Category category = new Category(1L, "avatar", "title");
        Child child = new Child(1L, "Test", "Child", LocalDate.now());

        new ChildDB().add(child);
        List<Child> children = db.allChildren(category.id());

        assertNotNull(children);
        assertEquals(1, children.size());
        assertEquals(child, children.get(0));
    }
}
