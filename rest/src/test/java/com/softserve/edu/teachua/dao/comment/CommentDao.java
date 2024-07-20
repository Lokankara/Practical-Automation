package com.softserve.edu.teachua.dao.comment;

import com.softserve.edu.teachua.dao.club.model.ClubDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackRequestDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackResponseDto;

public interface CommentDao {
    CreatedFeedbackResponseDto create(CreatedFeedbackRequestDto dto);

    boolean deleteCommentById(long id);

    ClubDto findClubById(long id);
}
