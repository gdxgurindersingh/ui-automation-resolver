package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

//@Listeners(TestListener.class)
public class BaseTest {
    protected CustomWebDriver driver;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static final ConfigProperties config = ConfigFactory.create(ConfigProperties.class);

    @BeforeMethod
    public void setup() {
        String browser = config.browser();
        boolean headless = config.headless();

        if (browser == null) {
            throw new IllegalArgumentException("Configuration properties are not loaded correctly.");
        }

        WebDriver rawDriver;

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            if (headless) {
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
            }
            rawDriver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("--headless");
            }
            rawDriver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Browser " + browser + " is not supported.");
        }

        driver = new CustomWebDriver(rawDriver);
        driver.manage().window().maximize();
        logger.info("Browser setup completed.");
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        if (!result.isSuccess()) {
            saveScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
        logger.info("Browser closed.");
    }

    @Attachment(value = "{testName} - screenshot", type = "image/png")
    public byte[] saveScreenshot(String testName) {
        byte[] screenshotBytes = null;
        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Debugging : Saving the screenshot to the filesystem
            String screenshotPath = "target/screenshots/" + testName + ".png";
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));

            logger.info("Screenshot saved at: " + screenshotPath);
        } catch (WebDriverException | IOException e) {
            logger.error("Error capturing screenshot: " + e.getMessage());
        }
        return screenshotBytes;
    }

    @AfterSuite
    public void afterSuite() {
        EmailUtil.sendEmailReport();
    }

    protected String getBaseUrl() {
        String projectDir = System.getProperty("user.dir");
        String indexPath = Paths.get(projectDir, "resources", "index.html").toUri().toString();
        return indexPath;
    }
}