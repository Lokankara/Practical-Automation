package com.softserve.edu.teachua.dao.comment;

import com.softserve.edu.teachua.dao.DaoUtil;
import com.softserve.edu.teachua.dao.club.ClubDao;
import com.softserve.edu.teachua.dao.club.ClubJdbcDao;
import com.softserve.edu.teachua.dao.club.model.ClubDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackRequestDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackResponseDto;
import com.softserve.edu.teachua.exception.DaoSQLException;
import com.softserve.edu.teachua.tools.ResourceUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.softserve.edu.teachua.service.assured.comment.CommentRestAssuredService.NEW_COMMENT_JSON;

public class CommentJdbcDao implements CommentDao {

    private static final String CREATE_FEEDBACK = "INSERT INTO feedbacks (rate, text, user_id, club_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE_COMMENT_BY_ID = "DELETE FROM feedbacks WHERE id = ?";

    private final Connection conn;

    private final ClubDao clubDao;

    public CommentJdbcDao() {
        this.conn = DaoUtil.getConnection();
        this.clubDao = new ClubJdbcDao(this.conn);
    }

    @Override
    public ClubDto findClubById(long id) {
        return clubDao.getClubById(id);
    }

    @Override
    public CreatedFeedbackResponseDto create(CreatedFeedbackRequestDto dto) {
        ClubDto club = findClubById(dto.clubId());
        System.out.printf("Found by ID %s: %s", dto.clubId(), club);

        try (PreparedStatement prepared = conn.prepareStatement(CREATE_FEEDBACK, Statement.RETURN_GENERATED_KEYS)) {
            prepared.setInt(1, dto.rate());
            prepared.setString(2, dto.text());
            prepared.setLong(3, dto.userId());
            prepared.setLong(4, dto.clubId());

            if (prepared.executeUpdate() > 0) {
                try (ResultSet generatedKeys = prepared.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        CreatedFeedbackResponseDto createdFeedbackResponseDto =
                                new CreatedFeedbackResponseDto(
                                        generatedKeys.getLong(1),
                                        dto.rate(),
                                        dto.text(),
                                        dto.clubId()
                                );
                        saveToFile(createdFeedbackResponseDto);
                        return createdFeedbackResponseDto;
                    } else {
                        throw new DaoSQLException("Creating feedback failed, no ID obtained.");
                    }
                }
            } else {
                throw new DaoSQLException("Creating feedback failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoSQLException("Failed to create feedback", e);
        }
    }

    @Override
    public boolean deleteCommentById(long id) {
        try (PreparedStatement statement = conn.prepareStatement(DELETE_COMMENT_BY_ID)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            saveToFile(CreatedFeedbackResponseDto.empty());
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete comment", e);
        }
    }

    private void saveToFile(CreatedFeedbackResponseDto dto) {
        ResourceUtils.get().saveAsJson(NEW_COMMENT_JSON, dto);
    }
}
