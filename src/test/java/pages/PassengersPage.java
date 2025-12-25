package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Waits;

public class PassengersPage {
    private final WebDriver driver;
    private final Waits waits;

    // TODO: התאמה לפי מה שמזהה את עמוד "פרטי הנוסעים"
    private final By passengersHeader = By.xpath("//*[contains(.,'פרטי הנוסעים') or contains(.,'נוסעים')]");

    public PassengersPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
    }

    public void assertPageOpened() {
        if (!waits.isVisible(passengersHeader)) {
            throw new AssertionError("עמוד פרטי הנוסעים לא נפתח/לא זוהה.");
        }
    }
}
