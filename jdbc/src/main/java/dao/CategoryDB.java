package dao;

import model.Category;
import model.Child;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryDB {
    private final Connection conn;

    public CategoryDB(Connection conn) {
        this.conn = conn;
    }

    public CategoryDB() {
        try {
            conn = DBUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category add(Category category) {
        String query = "INSERT INTO categories (avatar, title) VALUES (?, ?);";
        try (PreparedStatement prepared = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepared.setString(1, category.avatar());
            prepared.setString(2, category.title());
            prepared.executeUpdate();
            try (ResultSet rs = prepared.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Category(rs.getLong(1), category.avatar(), category.title());
                }
            }
        } catch (PSQLException e) {
            if (e.getMessage().contains("ERROR: duplicate key value violates unique constraint")) {
                System.err.printf("Category with '%s' title already exists.%n", category.title());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
    }
        return null;
    }

    public boolean update(Category category) throws SQLException {
        String query = "UPDATE categories SET avatar = ?, title = ? WHERE id = ?;";
        try (PreparedStatement prepared = conn.prepareStatement(query)) {
            prepared.setString(1, category.avatar());
            prepared.setString(2, category.title());
            prepared.setLong(3, category.id());
            return prepared.executeUpdate() == 1;
        }
    }

    public boolean delete(Long id) throws SQLException {
        String query = "delete from categories where id = ?;";
        try (PreparedStatement prepared = conn.prepareStatement(query)) {
            prepared.setLong(1, id);
            int affectedRows = prepared.executeUpdate();
            return affectedRows == 1;
        }
    }

    public List<Category> titlePart(String titlePart) throws SQLException {
        String query = "SELECT * FROM categories WHERE lower(title) LIKE ?";
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + titlePart.toLowerCase() + "%");
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String avatar = rs.getString("avatar");
                    String title = rs.getString("title");
                    Category category = new Category(id, avatar, title);
                    categories.add(category);
                }
            }
        }
        return categories;
    }

    public List<Child> allChildren(Long categoryID) throws SQLException {
        String query = "SELECT * FROM child WHERE id = ?";
        List<Child> children = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setLong(1, categoryID);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    LocalDate date = LocalDate.parse(rs.getString("birth_date"));
                    var child = new Child(id, firstName, lastName, date);
                    children.add(child);
                }
            }
        }
        return children;
    }
}
