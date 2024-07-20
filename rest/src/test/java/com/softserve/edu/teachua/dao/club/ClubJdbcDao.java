package com.softserve.edu.teachua.dao.club;

import com.softserve.edu.teachua.dao.club.model.CategoryDto;
import com.softserve.edu.teachua.dao.club.model.ClubDto;
import com.softserve.edu.teachua.exception.DaoSQLException;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubJdbcDao implements ClubDao {

    private static final String FIND_CLUB_BY_ID = "SELECT c.id AS club_id, c.age_from, c.age_to, c.center_external_id, c.club_external_id, c.contacts, c.description AS club_description, c.feedback_count, c.is_approved, c.is_online, c.name AS club_name, c.rating, c.url_background, c.url_logo, c.url_web, c.center_id, c.user_id, cat.id AS category_id, cat.background_color, cat.description AS category_description, cat.name AS category_name, cat.sortby, cat.tag_background_color, cat.tag_text_color, cat.url_logo AS category_url_logo FROM clubs c LEFT JOIN club_category cc ON c.id = cc.club_id LEFT JOIN categories cat ON cc.category_id = cat.id WHERE c.id = ?";

    private final Connection conn;

    public ClubJdbcDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public ClubDto getClubById(long clubId) {

        try (PreparedStatement statement = conn.prepareStatement(FIND_CLUB_BY_ID)) {
            statement.setLong(1, clubId);
            ResultSet resultSet = statement.executeQuery();
            ClubDto club = null;
            List<CategoryDto> categories = new ArrayList<>();
            while (resultSet.next()) {
                if (club == null) {
                    club = getClub(resultSet, categories);
                }
                if (resultSet.getInt("category_id") != 0) {
                    categories.add(getCategory(resultSet));
                }
            }
            if (club == null) {
                throw new DaoSQLException("Club not found with ID: " + clubId);
            }
            return club;
        } catch (SQLException e) {
            throw new DaoSQLException("Failed to fetch club" + e.getMessage());
        }
    }

    @NotNull
    private ClubDto getClub(ResultSet resultSet, List<CategoryDto> categories) throws SQLException {
        return new ClubDto(
                resultSet.getInt("club_id"),
                resultSet.getInt("age_from"),
                resultSet.getInt("age_to"),
                resultSet.getString("club_name"),
                resultSet.getString("club_description"),
                resultSet.getString("url_web"),
                resultSet.getString("url_logo"),
                resultSet.getString("url_background"),
                categories,
                null,
                resultSet.getInt("feedback_count"),
                null,
                resultSet.getDouble("rating")
        );
    }

    @NotNull
    private CategoryDto getCategory(ResultSet resultSet) throws SQLException {
        return new CategoryDto(
                resultSet.getInt("category_id"),
                resultSet.getInt("sortby"),
                resultSet.getString("category_name"),
                resultSet.getString("category_description"),
                resultSet.getString("category_url_logo"),
                resultSet.getString("background_color"),
                resultSet.getString("tag_background_color"),
                resultSet.getString("tag_text_color")
        );
    }
}
