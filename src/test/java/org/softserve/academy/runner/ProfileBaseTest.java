package org.softserve.academy.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class ProfileBaseTest extends BaseTest {
    protected void clickEditProfile() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='edit-button']"))).click();
        WebElement editTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='menu-title']")));
        assertTextEquals("Особистий кабінет", editTitle, "edit Title user profile text");
        WebElement editHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='content-title']")));
        assertTextEquals("Мій профіль", editHeader, "Edit user profile text");
    }

    protected void openProfile() {
        WebElement dropdownMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-dropdown-trigger user-profile']")));
        assertEnable(dropdownMenu, "After clicking the user icon Dropdown menu");
        dropdownMenu.click();
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Особистий кабінет')]")));
        assertEnable(profile, "After clicking the user Profile menu");
        clickElementWithJS(profile);
    }
}
