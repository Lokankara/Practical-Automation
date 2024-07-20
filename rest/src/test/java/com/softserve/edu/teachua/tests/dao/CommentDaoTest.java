package com.softserve.edu.teachua.tests.dao;

import com.softserve.edu.teachua.dao.DaoUtil;
import com.softserve.edu.teachua.dao.comment.CommentDao;
import com.softserve.edu.teachua.dao.comment.CommentJdbcDao;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackRequestDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackResponseDto;
import com.softserve.edu.teachua.tools.PositiveFeedbackGenerator;
import com.softserve.edu.teachua.tools.ResourceUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.softserve.edu.teachua.service.assured.comment.CommentRestAssuredService.NEW_COMMENT_JSON;

class CommentDaoTest extends DaoTest {

    private final CommentDao commentDao = new CommentJdbcDao();

    @Test
    void testGetColumnNames() throws SQLException {
        DatabaseMetaData metaData = DaoUtil.getConnection().getMetaData();
        ResultSet columns = metaData.getColumns(null, null, "feedbacks", null);

        Assertions.assertTrue(hasColumn(columns, "id"), "Column 'id' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "date"), "Column 'date' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "rate"), "Column 'rate' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "text"), "Column 'text' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "club_id"), "Column 'club_id' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "parent_comment_id"), "Column 'parent_comment_id' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "user_id"), "Column 'user_id' not found in feedbacks table");
    }

    @Test
    @Order(1)
    void testCreateFeedbackByCommentDao() {
        String feedback = PositiveFeedbackGenerator.generateFeedback();
        CreatedFeedbackRequestDto requestDto = new CreatedFeedbackRequestDto(11L, 5, feedback, 123L, 27L);
        CreatedFeedbackResponseDto responseDto = commentDao.create(requestDto);

        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(5, responseDto.rate());
        Assertions.assertEquals(feedback, responseDto.text());
        Assertions.assertEquals(27L, responseDto.clubId());
    }

    @Test
    @Order(2)
    void testDeleteFeedback() {
        CreatedFeedbackResponseDto comments = ResourceUtils.get().convertJsonToEntity(NEW_COMMENT_JSON, CreatedFeedbackResponseDto.class);
        boolean deleted = commentDao.deleteCommentById(comments.id());
        Assertions.assertTrue(deleted);
    }
}
