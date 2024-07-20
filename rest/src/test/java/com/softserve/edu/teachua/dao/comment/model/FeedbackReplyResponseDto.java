package com.softserve.edu.teachua.dao.comment.model;

import com.softserve.edu.teachua.dao.user.model.UserReplyDto;

public record FeedbackReplyResponseDto(
        int id,
        String text,
        String date,
        UserReplyDto user) {
}
