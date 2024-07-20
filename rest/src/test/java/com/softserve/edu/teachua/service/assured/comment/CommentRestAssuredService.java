package com.softserve.edu.teachua.service.assured.comment;

import com.softserve.edu.teachua.dao.club.model.ClubDto;
import com.softserve.edu.teachua.dao.club.model.Clubs;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackRequestDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackResponseDto;
import com.softserve.edu.teachua.service.assured.RestAssuredBase;
import com.softserve.edu.teachua.tools.ResourceUtils;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CommentRestAssuredService extends RestAssuredBase implements CommentAssuredService {

    private static final String CREATE = "feedback";
    private static final String CLUBS_SEARCH = "clubs/search";
    private static final String DELETE = "feedbacks/{id}";
    public static final String NEW_COMMENT_JSON = "new_comment.json";
    private static final String FEEDBACK_SCHEMA_JSON = "feedback_schema.json";

    @Override
    public ExtractableResponse<Response> createFeedback(CreatedFeedbackRequestDto comment) {
        ExtractableResponse<Response> responseExtractableResponse = getAuthorization()
                .body(getFeedback(comment))
                .when()
                .post(CREATE)
                .then()
                .statusCode(200)
                .body(notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(FEEDBACK_SCHEMA_JSON))
                .extract();
        saveToFile(responseExtractableResponse.as(CreatedFeedbackResponseDto.class));
        return responseExtractableResponse;
    }

    @Override
    public void saveToFile(CreatedFeedbackResponseDto response) {
        ResourceUtils.get().saveAsJson(NEW_COMMENT_JSON, response);
    }

    @Override
    public boolean deleteById(long id) {
        return getAuthorization()
                .when()
                .delete(DELETE, id)
                .getStatusCode() == 200;
    }

    public List<ClubDto> getClubsByName(String cityName) {
        return given()
                .queryParam("page", "0")
                .queryParam("cityName", cityName)
                .contentType(ContentType.JSON)
                .when()
                .get(CLUBS_SEARCH)
                .then()
                .statusCode(200)
                .extract()
                .response().as(Clubs.class).content();
    }

    @NotNull
    private static Map<String, Object> getFeedback(CreatedFeedbackRequestDto comment) {
        Map<String, Object> feedback = new HashMap<>();
        feedback.put("id", comment.id());
        feedback.put("rate", comment.rate());
        feedback.put("text", comment.text());
        feedback.put("userId", comment.userId());
        feedback.put("clubId", comment.clubId());
        return feedback;
    }
}
