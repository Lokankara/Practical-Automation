package com.softserve.edu.teachua.dao.comment.model;


public record CreatedFeedbackRequestDto(
        long id,
        int rate,
        String text,
        long userId,
        long clubId
) {
}
