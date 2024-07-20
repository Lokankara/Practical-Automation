package com.softserve.edu.teachua.service.assured.comment;

import com.softserve.edu.teachua.dao.club.model.ClubDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackRequestDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackResponseDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

public interface CommentAssuredService {
    ExtractableResponse<Response> createFeedback(CreatedFeedbackRequestDto comment);

    void saveToFile(CreatedFeedbackResponseDto responseDto);

    boolean deleteById(long id);

    List<ClubDto> getClubsByName(String city);
}
