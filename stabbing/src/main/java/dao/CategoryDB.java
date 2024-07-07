package dao;

import exception.EntityNotFoundException;
import model.Category;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDB {
    private Connection conn;

    public CategoryDB(Connection conn) {
        this.conn = conn;
    }

    public Category add(Category category) throws SQLException {
        String query = "INSERT INTO categories (avatar, title) VALUES (?, ?);";
        try (PreparedStatement pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, category.avatar());
            pst.setString(2, category.title());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                return new Category(id, category.avatar(), category.title());
            } else {
                throw new SQLException("Failed to retrieve generated keys for category insertion.");
            }
        } catch (PSQLException e) {
            return null;
        }
    }

    public boolean update(Category category) throws SQLException {
        String query = "update categories set avatar = ?, title = ? where id = ?;";
        try (PreparedStatement pst = conn.prepareStatement(query);) {
            pst.setString(1, category.avatar());
            pst.setString(2, category.title());
            pst.setLong(3, category.id());
            int affectedRows = pst.executeUpdate();
            return affectedRows == 1;
        }
    }

    public boolean delete(Long id) throws SQLException {
        String query = "delete from categories where id = ?;";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setLong(1, id);
            int affectedRows = pst.executeUpdate();
            return affectedRows == 1;
        }
    }

    public List<Category> titlePart(String titlePart) throws SQLException {
        String query = "SELECT * FROM categories where lower(title) like ?";
        try (PreparedStatement statement = conn.prepareStatement(query);) {
            statement.setString(1, "%" + titlePart.toLowerCase() + "%");
            ResultSet rs = statement.executeQuery();
            return fromResultSet(rs);
        }
    }

    private static List<Category> fromResultSet(ResultSet rs) throws SQLException {
        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            Long id = rs.getLong("id");
            String avatar = rs.getString("avatar");
            String title = rs.getString("title");
            Category category = new Category(id, avatar, title);

            categories.add(category);
        }
        return categories;
    }

    public Category getById(Long id) throws SQLException {
        String query = "SELECT * FROM categories where id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query);) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String avatar = rs.getString("avatar");
                String title = rs.getString("title");
                return new Category(id, avatar, title);
            }
        }
        throw new EntityNotFoundException(Category.class, id);
    }
}
