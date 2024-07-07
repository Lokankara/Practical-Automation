package com.softserve.edu.page.react;

import com.softserve.edu.page.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class Table extends BasePage {
    public Table(final WebDriver driver) {super(driver);}

    @FindBy(id = "remote-filtering")
    private WebElement header;

    @FindBy(xpath = "//tbody/tr[1]/td[2]")
    private WebElement firstRow;

    @FindBy(xpath = "//h2[@id='remote-filtering']/following-sibling::div[contains(@class, 'embedded-demo')]//iframe")
    private WebElement table;

    @FindBy(xpath = "(//div[contains(@class, 'MuiInputBase-root')])[last()-1]//input[contains(@class, 'MuiInputBase-input') and contains(@class, 'MuiInput-input') and contains(@class, 'Editor-input')]")
    private WebElement cityFilterInput;

    public Table filterCityByLetter(final String letter) {
        cityFilterInput.sendKeys(letter);

        return this;
    }

    public Table scrollToHeader() {
        scrollTo(header);

        return this;
    }

    public Table switchToIframe() {
        getDriver().switchTo().frame(table);

        return this;
    }

    public Table assertRemoteFilteringPresent() {
        Assertions.assertNotNull(header, "Remote Filtering should be present");
        Assertions.assertTrue(header.isDisplayed(), "Remote Filtering should be visible");
        Assertions.assertEquals("Remote Filtering", header.getText(),
                "Element should be contains Remote Filtering");

        return this;
    }

    public Row waitUntilFirstRowRemoved() {
        assertPresentText(firstRow);
        waitFirstRow("//td[text()='" + firstRow.getText() + "']");

        return new Row(getDriver());
    }

    private void waitFirstRow(String removed) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(removed)));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
    }
}
