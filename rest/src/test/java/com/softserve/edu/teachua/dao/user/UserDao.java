package com.softserve.edu.teachua.dao.user;

import com.softserve.edu.teachua.dao.Dao;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;

import java.util.List;

public interface UserDao extends Dao<LoggedUserDto> {

    boolean updateStatusUser(int id, boolean status);

    long create(LoggedUserDto user);

    List<LoggedUserDto> getAll();

    LoggedUserDto getById(int id);

    LoggedUserDto getUserByEmail(String email);

    void update(LoggedUserDto user);

    boolean deleteById(int id);
}
