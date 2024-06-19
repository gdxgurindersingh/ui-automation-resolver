package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected CustomWebDriver driver;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    private Properties properties;

    @BeforeMethod
    public void setup() {
        properties = loadProperties();
        String browser = System.getProperty("browser", properties.getProperty("browser"));
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", properties.getProperty("headless")));

        if (browser == null) {
            throw new IllegalArgumentException("Configuration properties are not loaded correctly.");
        }

        WebDriver rawDriver = null;

        try {
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
            driver.manage().timeouts().implicitlyWait(Long.parseLong(properties.getProperty("implicitlyWait")), TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(Long.parseLong(properties.getProperty("pageLoadTimeout")), TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(Long.parseLong(properties.getProperty("setScriptTimeout")), TimeUnit.SECONDS);
            driver.manage().window().maximize();
            logger.info("Browser setup completed.");
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: " + e.getMessage());
            if (rawDriver != null) {
                rawDriver.quit();
            }
            throw e;
        }
    }

/*    @BeforeMethod
    public void setup() {
        // Check for system properties first
        String browser = System.getProperty("browser", config.browser());
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", String.valueOf(config.headless())));

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
        driver.manage().timeouts().implicitlyWait(config.implicitlyWait(), TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(config.pageLoadTimeout(), TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(config.setScriptTimeout(), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("Browser setup completed.");
    }*/

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

    private Properties loadProperties() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("resources/config.properties")) {
            props.load(input);
        } catch (IOException ex) {
            logger.error("Unable to load config.properties file", ex);
        }
        return props;
    }
}