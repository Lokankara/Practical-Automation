package com.softserve.edu.teachua.service.selenium.user;

import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.pages.menu.HomePage;

public interface UserSeleniumService {
    String unsuccessfulLogin(IUser user);

    HomePage successfulLogin(IUser user);
}
