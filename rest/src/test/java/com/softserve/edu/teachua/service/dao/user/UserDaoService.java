package com.softserve.edu.teachua.service.dao.user;

import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;

public interface UserDaoService {

    LoggedUserDto findById(int id);

    boolean disableById(int id);

    boolean activateById(int id);

    boolean deleteById(int id);

    void saveToFile(LoggedUserDto user);
}
