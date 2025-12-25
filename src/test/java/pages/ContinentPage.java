package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Waits;

public class ContinentPage {
    private final WebDriver driver;
    private final Waits waits;

    // TODO: לבחור יבשת. הכי יציב: locator לפי label/aria-label/טקסט
    // דוגמא: "אירופה"
    private final By europeOption = By.xpath("//*[self::button or self::div][contains(.,'אירופה')]");

    private final By nextToDatesBtn = By.xpath("//button[contains(.,'הלאה לבחירת תאריכי הנסיעה')]");

    public ContinentPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
    }

    public ContinentPage chooseAnyContinent() {
        waits.click(europeOption); // אפשר להחליף ליבשת אחרת
        return this;
    }

    public DatesPage goNextToDates() {
        waits.click(nextToDatesBtn);
        return new DatesPage(driver);
    }
}
