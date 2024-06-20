package tests;

import io.qameta.allure.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import testData.DataProviders;
import utils.BaseTest;

@Epic("Home Page Tests")
@Feature("Home Page Functionality")
public class HomePageTest extends BaseTest {
    private HomePage homePage;

    @BeforeMethod
    public void setUpPage() {
        homePage = PageFactory.initElements(driver, HomePage.class);
    }

    @Test(priority = 1, description = "Test to verify login form is displayed, and able to enter credentials", dataProvider = "loginCredentials", dataProviderClass = DataProviders.class)
    @Description("Verify that the login form with email, password fields, and login button is displayed on the Home Page. Enter login credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Step("Test Login Form Display")
    public void test1_LoginPageFormDisplay(String email, String password) {
        homePage.open();
        Assert.assertTrue(homePage.isLoginFormDisplayed(), "Login Page is not displayed.");
        //Separating the test data from the test class, I am using TestNG’s data provider feature
        //This test runs twice to depict multi run according to number of data values in Data Provider
        homePage.login(email, password);
    }

    @Test(priority = 2, description = "Test to verify list group items")
    @Description("Verify the count and values of list group items.")
    @Severity(SeverityLevel.NORMAL)
    @Step("Test List Group Items")
    public void test2_ListGroupItems() {
        homePage.open();
        softAssert.assertEquals(homePage.getListItemsCount(), 3, "List group items count is not 3.");
        softAssert.assertEquals(homePage.getListItemText(2).trim(), "List Item 2", "Second list item text is not 'List Item 2'.");
        softAssert.assertEquals(homePage.getListItemBadgeText(2), "6", "Second list item badge value is not '6'.");
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Test to verify dropdown functionality")
    @Description("Verify the default selected option and selecting a different option from the dropdown.")
    @Severity(SeverityLevel.NORMAL)
    @Step("Test Dropdown Functionality")
    public void test3_DropdownFunctionality() {
        homePage.open();
        softAssert.assertEquals(homePage.getSelectedDropdownOption(), "Option 1", "Default selected option is not 'Option 1'.");
        homePage.selectDropdownOption("Option 3");
        softAssert.assertEquals(homePage.getSelectedDropdownOption(), "Option 3", "Selected option is not 'Option 3'.");

        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Test to verify button states")
    @Description("Verify that the first button is enabled and the second button is disabled.")
    @Severity(SeverityLevel.NORMAL)
    @Step("Test Button States")
    public void Test4_buttonStates() {
        homePage.open();
        softAssert.assertTrue(homePage.isEnabledButtonEnabled(), "Enabled button is not enabled.");
        softAssert.assertTrue(homePage.isDisabledButtonDisabled(), "Disabled button is not disabled.");

        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Test to verify delayed button functionality")
    @Description("Verify that the delayed button appears, can be clicked, and displays a success message.")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Test Delayed Button Functionality")
    public void test5_DelayedButtonFunctionality() {
        homePage.open();
        homePage.waitForTest5ButtonAndClick();
        softAssert.assertTrue(homePage.isTest5AlertDisplayed(), "Success message is not displayed.");
        softAssert.assertTrue(homePage.isTest5ButtonDisabled(), "Button is not disabled after click.");

        softAssert.assertAll();
    }

    @Test(priority = 6, description = "Test to verify table cell value")
    @Description("Verify the value of a specific cell in the table.")
    @Severity(SeverityLevel.MINOR)
    @Step("Test Table Cell Value")
    public void test6_TableCellValue() {
        homePage.open();
        String cellValue = homePage.getTableCellValue(2, 2);
        Assert.assertEquals(cellValue, "Ventosanzap", "The value of the cell at coordinates 2, 2 is not 'Ventosanzap'.");
    }
}