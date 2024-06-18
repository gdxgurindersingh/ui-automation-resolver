package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseTest;
import utils.LoggerUtil;
import utils.WaitUtil;

import java.util.List;

public class HomePage extends BaseTest {
    private WebDriver driver;
    private WaitUtil waitUtil;

    // Test 1 elements
    @FindBy(id = "inputEmail")
    private WebElement emailField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(css = ".btn.btn-lg.btn-primary.btn-block")
    private WebElement loginButton;

    // Test 2 elements
    @FindBy(css = "#test-2-div .list-group .list-group-item")
    private List<WebElement> listItems;

    // Test 3 elements
    @FindBy(id = "dropdownMenuButton")
    private WebElement dropdownButton;

    @FindBy(css = "#test-3-div .dropdown-item")
    private List<WebElement> dropdownOptions;

    // Test 4 elements
    @FindBy(css = "#test-4-div .btn-primary")
    private WebElement enabledButton;

    @FindBy(css = "#test-4-div .btn-secondary")
    private WebElement disabledButton;

    // Test 5 elements
    @FindBy(id = "test5-button")
    private WebElement test5Button;

    @FindBy(id = "test5-alert")
    private WebElement test5Alert;

    // Test 6 elements
    @FindBy(css = "#test-6-div table tbody tr")
    private List<WebElement> tableRows;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtil = new WaitUtil(driver); // 10 seconds timeout
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(getBaseUrl());
        LoggerUtil.info("Navigated to Home Page");
    }

    // Test 1 methods
    public boolean isLoginFormDisplayed() {
        waitUtil.waitForElementToBeVisible(emailField);
        return emailField.isDisplayed() && passwordField.isDisplayed() && loginButton.isDisplayed();
    }

    public void login(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
//        loginButton.click();
        LoggerUtil.info("Attempted to login with email: " + email);
    }

    // Test 2 methods

    // Method to get the count of list items
    public int getListItemsCount() {
        return listItems.size();
    }

    public String getListItemText(int index) {
        int zeroIndex = index - 1; // Convert to 0-based index
        if (zeroIndex >= 0 && zeroIndex < listItems.size()) {
            WebElement listItem = listItems.get(zeroIndex);
            return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].firstChild.textContent.trim();", listItem);
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds for list items: " + index);
        }
    }

    // Method to get the badge value of a list item
    public String getListItemBadgeText(int index) {
        int zeroIndex = index - 1; // Convert to 0-based index
        if (zeroIndex >= 0 && zeroIndex < listItems.size()) {
            return listItems.get(zeroIndex).findElement(By.tagName("span")).getText().trim();
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds for list items: " + index);
        }
    }

    // Test 3 methods
    public String getSelectedDropdownOption() {
        waitUtil.waitForElementToBeVisible(dropdownButton);
        return dropdownButton.getText();
    }

    public void selectDropdownOption(String optionText) {
        dropdownButton.click();  // Ensure dropdown is open
        waitUtil.waitForElementsToBeVisible(dropdownOptions);
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionText)) {
                option.click();
                break;
            }
        }
    }

    // Test 4 methods
    public boolean isEnabledButtonEnabled() {
        waitUtil.waitForElementToBeVisible(enabledButton);
        return enabledButton.isEnabled();
    }

    public boolean isDisabledButtonDisabled() {
        waitUtil.waitForElementToBeVisible(disabledButton);
        return !disabledButton.isEnabled();
    }

    // Test 5 methods
    public void waitForTest5ButtonAndClick() {
        waitUtil.waitForElementToBeClickable(test5Button);
        test5Button.click();
    }

    public boolean isTest5AlertDisplayed() {
        waitUtil.waitForElementToBeVisible(test5Alert);
        return test5Alert.isDisplayed();
    }

    public boolean isTest5ButtonDisabled() {
        return !test5Button.isEnabled();
    }

    // Test 6 methods
    public String getTableCellValue(int row, int col) {
        waitUtil.waitForElementToBeVisible(tableRows.get(row));
        return tableRows.get(row).findElements(By.tagName("td")).get(col).getText();
    }
}
