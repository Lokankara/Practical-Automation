package com.softserve.edu.teachua.tests.restassured;

import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackRequestDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackResponseDto;
import com.softserve.edu.teachua.service.assured.comment.CommentAssuredService;
import com.softserve.edu.teachua.service.assured.comment.CommentRestAssuredService;
import com.softserve.edu.teachua.tools.PositiveFeedbackGenerator;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FeedbackTest {
    private final CommentAssuredService service = new CommentRestAssuredService();

    @Test
    void testCreateFeedbackByRestAssured() {
        CreatedFeedbackRequestDto requestDto = new CreatedFeedbackRequestDto(
                11L, 5, PositiveFeedbackGenerator.generateFeedback(), 198L, 27L);

        ExtractableResponse<Response> response = service.createFeedback(requestDto);
        CreatedFeedbackResponseDto responseDto = response.as(CreatedFeedbackResponseDto.class);

        Assertions.assertEquals(requestDto.rate(), responseDto.rate());
        Assertions.assertEquals(requestDto.text(), responseDto.text());
        Assertions.assertEquals(requestDto.userId(), responseDto.user().id());
        Assertions.assertEquals(requestDto.clubId(), responseDto.clubId());
    }
}
