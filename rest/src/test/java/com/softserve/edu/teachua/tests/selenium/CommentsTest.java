package com.softserve.edu.teachua.tests.selenium;

import com.softserve.edu.teachua.dao.club.model.ClubDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackResponseDto;
import com.softserve.edu.teachua.pages.club.CommentsContainer;
import com.softserve.edu.teachua.service.facade.CommentFacade;
import com.softserve.edu.teachua.tools.ResourceUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.softserve.edu.teachua.service.assured.comment.CommentRestAssuredService.NEW_COMMENT_JSON;

class CommentsTest extends TestRunner {

    private final CommentFacade service = new CommentFacade();

    @Test
    void checkCommentExist() {
        var dto = ResourceUtils.get().convertJsonToEntity(NEW_COMMENT_JSON, CreatedFeedbackResponseDto.class);
        ClubDto club = service.getClubById(dto.clubId());
        CommentsContainer comments = service.findComments(club);

        Assertions.assertTrue(comments.isExistClubComponentByPartialAuthor(
                String.format("%s %s", dto.user().firstName(), dto.user().lastName())));
        Assertions.assertEquals(dto.text(), comments.getFirstCommentComponent().getCommentLabelText());
    }
}
