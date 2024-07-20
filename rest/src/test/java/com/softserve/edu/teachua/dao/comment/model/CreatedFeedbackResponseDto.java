package com.softserve.edu.teachua.dao.comment.model;

import com.softserve.edu.teachua.dao.user.model.UserReplyDto;

import java.util.ArrayList;
import java.util.List;

public record CreatedFeedbackResponseDto(
        long id,
        int rate,
        String text,
        long clubId,
        UserReplyDto user,
        List<FeedbackReplyResponseDto> replies
) {
    public CreatedFeedbackResponseDto(long id, int rate, String text, long clubId) {
        this(id, rate, text, clubId, null, new ArrayList<>());
    }
    public static CreatedFeedbackResponseDto empty() {
        return new CreatedFeedbackResponseDto(0, 0, "", 0, null, new ArrayList<>());
    }
}
