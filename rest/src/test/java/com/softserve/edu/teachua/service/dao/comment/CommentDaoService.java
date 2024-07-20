package com.softserve.edu.teachua.service.dao.comment;

import com.softserve.edu.teachua.dao.club.model.ClubDto;

public interface CommentDaoService {

    boolean deleteById(long id);

    ClubDto getById(long id);
}
