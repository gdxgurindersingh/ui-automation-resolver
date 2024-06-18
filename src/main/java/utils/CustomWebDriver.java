package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Set;

public class CustomWebDriver implements WebDriver, JavascriptExecutor, TakesScreenshot {
    private final WebDriver driver;
    private final Actions actions;

    public CustomWebDriver(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    /**
     * Scrolls the given element into view if it is not already in the viewport.
     *
     * @param element The WebElement to scroll into view.
     */
    private void scrollToElement(WebElement element) {
        try {
            Boolean isInView = (Boolean) ((JavascriptExecutor) driver).executeScript("var elem = arguments[0],                 " + "  box = elem.getBoundingClientRect(),    " + "  cx = box.left + box.width / 2,         " + "  cy = box.top + box.height / 2,         " + "  e = document.elementFromPoint(cx, cy); " + "for (; e; e = e.parentElement) {         " + "  if (e === elem)                        " + "    return true;                         " + "}                                        " + "return false;                            ", element);

            if (!isInView) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
            }
        } catch (JavascriptException e) {
            // Handle JavascriptException if JavaScript execution fails
            System.err.println("Error scrolling to element: " + e.getMessage());
        }
    }

    @Override
    public void get(String url) {
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        WebElement element = driver.findElement(by);
        scrollToElement(element); // Scroll to the element if not in view
        return element;
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeAsyncScript(script, args);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return ((TakesScreenshot) driver).getScreenshotAs(target);
    }

}