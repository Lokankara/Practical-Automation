package com.softserve.edu.teachua.service.dao.comment;

import com.softserve.edu.teachua.dao.club.model.ClubDto;
import com.softserve.edu.teachua.dao.comment.CommentDao;
import com.softserve.edu.teachua.dao.comment.CommentJdbcDao;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackRequestDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackResponseDto;
import com.softserve.edu.teachua.tools.ResourceUtils;

import static com.softserve.edu.teachua.service.assured.comment.CommentRestAssuredService.NEW_COMMENT_JSON;

public class CommentJdbcDaoService implements CommentDaoService {
    private final CommentDao userDao;

    public CommentJdbcDaoService() {
        this.userDao = new CommentJdbcDao();
    }

    public CreatedFeedbackResponseDto create(CreatedFeedbackRequestDto dto) {
        CreatedFeedbackResponseDto response = userDao.create(dto);
        ResourceUtils.get().saveAsJson(NEW_COMMENT_JSON, response);
        return response;
    }

    @Override
    public boolean deleteById(long id) {
        boolean deleted = userDao.deleteCommentById(id);
        ResourceUtils.get().saveAsJson(NEW_COMMENT_JSON, CreatedFeedbackResponseDto.empty());
        return deleted;
    }

    @Override
    public ClubDto getById(long id) {
        return userDao.findClubById(id);
    }
}
