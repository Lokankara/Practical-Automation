package com.softserve.edu.teachua.service.dao.user;

import com.softserve.edu.teachua.dao.user.UserDao;
import com.softserve.edu.teachua.dao.user.UserJdbcDao;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import com.softserve.edu.teachua.tools.ResourceUtils;

import static com.softserve.edu.teachua.tools.ResourceUtils.NEW_USER_JSON;

public class UserJdbcDaoService implements UserDaoService {

    UserDao userDao = new UserJdbcDao();

    @Override
    public LoggedUserDto findById(int id) {
        return userDao.getById(id);
    }

    @Override
    public boolean disableById(int id) {
        return userDao.updateStatusUser(id, false);
    }

    @Override
    public boolean activateById(int id) {
        return userDao.updateStatusUser(id, true);
    }

    @Override
    public boolean deleteById(int id) {
        return userDao.deleteById(id);
    }

    @Override
    public void saveToFile(LoggedUserDto user) {
        ResourceUtils.get().saveAsJson(NEW_USER_JSON, user);
    }
}
