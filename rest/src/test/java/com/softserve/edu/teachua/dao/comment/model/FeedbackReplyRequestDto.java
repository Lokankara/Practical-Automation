package com.softserve.edu.teachua.dao.comment.model;

public record FeedbackReplyRequestDto(int id, int parentCommentId, String text, int userId) {
}
