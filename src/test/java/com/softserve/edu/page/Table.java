package com.softserve.edu.page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Table extends BasePage {
    @FindBy(id = "remote-filtering")
    private WebElement header;

    private final By table = By.xpath("//h2[@id='remote-filtering']/following-sibling::div[contains(@class, 'embedded-demo')]//iframe");

    public Table(final WebDriver driver) {
        super(driver);
    }

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
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(table));
        return this;
    }

    public Table assertRemoteFilteringPresent() {
        Assertions.assertNotNull(header, "Remote Filtering should be present");
        Assertions.assertTrue(header.isDisplayed(), "Remote Filtering should be visible");
        Assertions.assertEquals("Remote Filtering", header.getText(),
                "Element should be contains Remote Filtering");
        return this;
    }

    public Row getTableHeader() {
        waitFirstRow();
        return new Row(driver);
    }

    private void waitFirstRow() {
        WebElement row = driver.findElement(By.xpath("//tbody/tr[1]/td[2]"));
        String removed = "//td[text()='" + row.getText() + "']";
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(removed)));
    }
}
