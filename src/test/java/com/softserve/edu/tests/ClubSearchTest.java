package com.softserve.edu.tests;

import com.softserve.edu.page.speak.ClubPage;
import com.softserve.edu.runner.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class ClubSearchTest extends BaseTest {
    private static final int NUMBER_PAGE = 5;
    private static final String CITY_NAME = "Харків";
    private static final String CLUB_NAME = "Новий Кадр";
    public static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void beforeEach() {
        driver.get(BASE_URL);
    }

    @Test
    @DisplayName("When a city is selected, the club search should return the correct club name")
    void testClubSearch() {

        new ClubPage(driver)
                .selectCity(CITY_NAME)
                .assertPresentClubName(CLUB_NAME)
                .openSideBar()
                .assertHeader()
                .gotoPage(NUMBER_PAGE)
                .assertPresentClubName(CLUB_NAME);
    }

    @Test
    @DisplayName("Optional task after opening the sidebar and choosing a city, navigating pages should verify the club name")
    void testClubSearchWithAdvancedSearch() {

        new ClubPage(driver)
                .openSideBar()
                .chooseCity(CITY_NAME)
                .assertHeader()
                .gotoPage(NUMBER_PAGE)
                .assertPresentClubName(CLUB_NAME);
    }
}
