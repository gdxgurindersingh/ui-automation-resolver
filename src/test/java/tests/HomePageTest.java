package tests;

import io.qameta.allure.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.BaseTest;

@Epic("Home Page Tests")
@Feature("Home Page Functionality")
public class HomePageTest extends BaseTest {
    private HomePage homePage;

    @BeforeMethod
    public void setUpPage() {
        homePage = PageFactory.initElements(driver, HomePage.class);
    }

    @Test(description = "Test to verify login form is displayed")
    @Description("Verify that the login form with email, password fields, and login button is displayed on the Home Page.")
    @Severity(SeverityLevel.BLOCKER)
    @Step("Test Login Form Display")
    public void loginPageFormDisplay() {
        homePage.open();
        Assert.assertTrue(homePage.isLoginFormDisplayed(), "Login Page is not displayed.");
    }

    @Test(description = "Test to verify login functionality")
    @Description("Verify login functionality by entering email and password.")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Test Login Functionality")
    public void loginFunctionality() {
        homePage.open();
        homePage.login("test@example.com", "password");
        // Add assertions for successful login if applicable
    }

    @Test(description = "Test to verify list group items")
    @Description("Verify the count and values of list group items.")
    @Severity(SeverityLevel.NORMAL)
    @Step("Test List Group Items")
    public void listGroupItems() {
        homePage.open();
        Assert.assertEquals(homePage.getListItemCount(), 3, "List group items count is not 3.");
        Assert.assertEquals(homePage.getSecondListItemText(), "List Item 2", "Second list item text is not 'List Item 2'.");
        Assert.assertEquals(homePage.getSecondListItemBadgeText(), "6", "Second list item badge value is not '6'.");
    }

    @Test(description = "Test to verify dropdown functionality")
    @Description("Verify the default selected option and selecting a different option from the dropdown.")
    @Severity(SeverityLevel.NORMAL)
    @Step("Test Dropdown Functionality")
    public void dropdownFunctionality() {
        homePage.open();
        Assert.assertEquals(homePage.getSelectedDropdownOption(), "Option 1", "Default selected option is not 'Option 1'.");
        homePage.selectDropdownOption("Option 3");
        Assert.assertEquals(homePage.getSelectedDropdownOption(), "Option 3", "Selected option is not 'Option 3'.");
    }

    @Test(description = "Test to verify button states")
    @Description("Verify that the first button is enabled and the second button is disabled.")
    @Severity(SeverityLevel.NORMAL)
    @Step("Test Button States")
    public void buttonStates() {
        homePage.open();
        Assert.assertTrue(homePage.isEnabledButtonEnabled(), "Enabled button is not enabled.");
        Assert.assertTrue(homePage.isDisabledButtonDisabled(), "Disabled button is not disabled.");
    }

    @Test(description = "Test to verify delayed button functionality")
    @Description("Verify that the delayed button appears, can be clicked, and displays a success message.")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Test Delayed Button Functionality")
    public void delayedButtonFunctionality() {
        homePage.open();
        homePage.waitForTest5ButtonAndClick();
        Assert.assertTrue(homePage.isTest5AlertDisplayed(), "Success message is not displayed.");
        Assert.assertTrue(homePage.isTest5ButtonDisabled(), "Button is not disabled after click.");
    }

    @Test(description = "Test to verify table cell value")
    @Description("Verify the value of a specific cell in the table.")
    @Severity(SeverityLevel.MINOR)
    @Step("Test Table Cell Value")
    public void tableCellValue() {
        homePage.open();
        String cellValue = homePage.getTableCellValue(2, 2);
        Assert.assertEquals(cellValue, "Ventosanzap", "The value of the cell at coordinates 2, 2 is not 'Ventosanzap'.");
    }
}