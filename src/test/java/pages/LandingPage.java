package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Waits;

public class LandingPage {
    private final WebDriver driver;
    private final Waits waits;

    private final String url = "https://digital.harel-group.co.il/travel-policy";

    // TODO: להתאים לוקייטור לפי DOM (חפש button עם הטקסט)
    private final By firstTimePurchaseBtn = By.xpath("//button[contains(.,'לרכישה בפעם הראשונה')]");

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
    }

    public LandingPage open() {
        driver.get(url);
        // אם יש popups/קוקיז – לטפל כאן
        waits.visible(firstTimePurchaseBtn);
        return this;
    }

    public ContinentPage clickFirstTimePurchase() {
        waits.click(firstTimePurchaseBtn);
        return new ContinentPage(driver);
    }
}
