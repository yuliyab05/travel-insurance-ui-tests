package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public Waits(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement clickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void click(By by) {
        clickable(by).click();
    }

    public void type(By by, String text) {
        WebElement el = visible(by);
        el.clear();
        el.sendKeys(text);
    }

    public void jsClick(By by) {
        WebElement el = visible(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public boolean isVisible(By by) {
        try {
            return visible(by).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
