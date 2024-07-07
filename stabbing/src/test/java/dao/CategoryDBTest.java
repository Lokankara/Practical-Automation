package dao;

import exception.EntityNotFoundException;
import model.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.DBUtil;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryDBTest {

    private static CategoryDB db;

    @BeforeAll
    public static void beforeAll() throws SQLException, IOException {
        db = new CategoryDB(DBUtil.getConnection());
        new DBUtil().executeFile("init.sql");
    }

    @Test
    void incrementCategoryCountWhenAddNewCategory() throws SQLException {
        int oldCount = DBUtil.totalCount("categories");
        var newCategory = new Category("avatar one", "title");
        db.add(newCategory);
        int newCount = DBUtil.totalCount("categories");
        assertEquals(newCount, oldCount + 1, "the difference between two numbers");
    }

    @Test
    void unchangedCategoryCountWhenAddCategoryWithExistingTitle() throws SQLException {
        int oldCount = DBUtil.totalCount("categories");
        var newCategory = new Category("avatar two", "Unique Category");
        db.add(newCategory);
        int newCount = DBUtil.totalCount("categories");
        assertEquals(oldCount, newCount, "The count categories should not be change");
    }

    @Test
    void update() throws SQLException {
        var firstCategory = new Category(1L, "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII", "New Old Category");
        boolean actual = db.update(firstCategory);
        assertTrue(actual, "Should returns true if it was updated just one record");
    }

    @Test
    void delete() throws SQLException {
        boolean actual = db.delete(2L);
        assertTrue(actual, "Should returns true if it was delete just one record");
    }

    @Test
    void titlePart() throws SQLException {
        String searchedPart = "uniquE";
        int expected = 2;
        int actual = db.titlePart(searchedPart).size();
        assertEquals(expected, actual,
                String.format("Database should contain %d records with %s word ", expected, searchedPart));
    }

    @Test
    void testGetById() throws SQLException {
        Long id = 1L;
        Category result = db.getById(id);
        assertNotNull(result, "The result should not be null");
        assertEquals(id, result.id(), String.format("Database should contain %d ID", id));
    }

    @Test
    void testGetByIdThrowsEntityNotFoundException() {
        Long id = 999L;
        assertThrows(EntityNotFoundException.class,
                () -> db.getById(id), "Expected EntityNotFound Exception");
    }
}
