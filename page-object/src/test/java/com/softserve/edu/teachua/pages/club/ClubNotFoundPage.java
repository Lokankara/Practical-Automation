package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class ClubNotFoundPage extends TopSearchPart {
    public static String NOT_FOUND_MASSAGE = "За критеріями пошуку гуртків не знайдено";

    private WebElement notFoundLabel;

    public ClubNotFoundPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    private void initElements() {
        notFoundLabel = driver.findElement(By.cssSelector("div.clubs-not-found"));
    }

    // Page Object
    public WebElement getNotFoundLabel() {
        return notFoundLabel;
    }

    public String getNotFoundLabelText() {
        return getNotFoundLabel().getText();
    }

    // Functional
    // Business Logic
}
