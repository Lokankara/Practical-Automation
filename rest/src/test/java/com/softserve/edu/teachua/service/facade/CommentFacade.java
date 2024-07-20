package com.softserve.edu.teachua.service.facade;

import com.softserve.edu.teachua.dao.club.model.ClubDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackRequestDto;
import com.softserve.edu.teachua.pages.club.CommentsContainer;
import com.softserve.edu.teachua.service.assured.comment.CommentAssuredService;
import com.softserve.edu.teachua.service.assured.comment.CommentRestAssuredService;
import com.softserve.edu.teachua.service.dao.comment.CommentDaoService;
import com.softserve.edu.teachua.service.dao.comment.CommentJdbcDaoService;
import com.softserve.edu.teachua.service.selenium.comment.CommentContainerSeleniumService;
import com.softserve.edu.teachua.service.selenium.comment.CommentSeleniumService;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

public class CommentFacade {
    private final CommentDaoService daoService;
    private final CommentAssuredService assuredService;
    private final CommentSeleniumService seleniumService;

    public CommentFacade() {
        daoService = new CommentJdbcDaoService();
        seleniumService = new CommentContainerSeleniumService();
        assuredService = new CommentRestAssuredService();
    }

    public CommentsContainer findComments(ClubDto club) {
        return seleniumService.findComments(club.name());
    }

    public ExtractableResponse<Response> createFeedback(CreatedFeedbackRequestDto requestDto) {
        return assuredService.createFeedback(requestDto);
    }

    public String getFirstComment(String partClubName) {
        return seleniumService
                .findComments(partClubName)
                .getFirstCommentComponent()
                .getCommentLabelText();
    }

    public boolean deleteCommentByDao(long id) {
        return daoService.deleteById(id);
    }

    public boolean deleteCommentByAssured(long id) {
        return assuredService.deleteById(id);
    }

    public List<ClubDto> getClubsByCity(String city) {
        return assuredService.getClubsByName(city);
    }

    public ClubDto getClubById(long id) {
        return daoService.getById(id);
    }
}
