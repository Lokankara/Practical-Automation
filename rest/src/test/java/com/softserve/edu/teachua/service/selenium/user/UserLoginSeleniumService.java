package com.softserve.edu.teachua.service.selenium.user;

import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.service.selenium.SeleniumService;

public class UserLoginSeleniumService extends SeleniumService implements UserSeleniumService {

    @Override
    public String unsuccessfulLogin(IUser user) {
        return loadApplication()
                .openLoginModal()
                .unsuccessfulLoginPage(user.getEmail(), user.getPassword())
                .getPopupMessageLabelText();
    }

    @Override
    public HomePage successfulLogin(IUser user) {
        return loadApplication()
                .openLoginModal()
                .successfulLogin(user.getEmail(), user.getPassword());
    }
}
