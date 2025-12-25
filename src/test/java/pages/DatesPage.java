package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Waits;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DatesPage {
    private final WebDriver driver;
    private final Waits waits;

    // TODO: להתאים לוקייטורים לפי DOM
    private final By departureInput = By.cssSelector("input[name='departureDate'], input[placeholder*='יציאה']");
    private final By returnInput    = By.cssSelector("input[name='returnDate'], input[placeholder*='חזרה']");

    // תצוגת "סה\"כ ימים" (דוגמא)
    private final By totalDaysLabel = By.xpath("//*[contains(.,'סה\"כ ימים') or contains(.,'סה״כ ימים')]/following::*[1]");

    private final By nextToPassengersBtn = By.xpath("//button[contains(.,'הלאה לפרטי הנוסעים')]");

    // TODO: לוקייטור של יום בלוח. עדיף data-date=YYYY-MM-DD אם קיים
    private By dayCellByIso(String isoDate) {
        return By.cssSelector("[data-date='" + isoDate + "'], [aria-label*='" + isoDate + "']");
    }

    public DatesPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
    }

    public DatesPage selectDynamicDates() {
        LocalDate departure = LocalDate.now().plusDays(7);
        LocalDate ret = departure.plusDays(30);

        // פורמט נפוץ להקלדה בישראל: dd/MM/yyyy
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        boolean typed = tryTypeDate(departureInput, departure.format(inputFmt))
                && tryTypeDate(returnInput, ret.format(inputFmt));

        if (!typed) {
            // fallback: בחירה מתוך datepicker
            pickFromDatePicker(departureInput, departure);
            pickFromDatePicker(returnInput, ret);
        }

        // Validate total days
        long expectedDays = ChronoUnit.DAYS.between(departure, ret); // כאן יוצא 30
        assertTotalDays(String.valueOf(expectedDays));

        return this;
    }

    private boolean tryTypeDate(By inputBy, String value) {
        try {
            WebElement input = waits.visible(inputBy);
            if ("true".equalsIgnoreCase(input.getAttribute("readonly"))) return false;

            input.click();
            input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            input.sendKeys(value);
            input.sendKeys(Keys.TAB);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void pickFromDatePicker(By inputBy, LocalDate date) {
        waits.click(inputBy);

        String iso = date.toString(); // yyyy-MM-dd
        By dayBy = dayCellByIso(iso);

        // אם הלוח נטען בצורה דינאמית - נחכה מעט
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            w.until(ExpectedConditions.presenceOfElementLocated(dayBy));
            waits.click(dayBy);
        } catch (TimeoutException e) {
            // אם אין data-date/aria-label – תצטרכי להתאים כאן לפי DOM בפועל
            throw new RuntimeException("לא הצלחתי לבחור תאריך בלוח. צריך להתאים locator של יום לתאריך: " + iso, e);
        }
    }

    private void assertTotalDays(String expected) {
        WebElement el = waits.visible(totalDaysLabel);
        String text = el.getText();
        if (text == null || !text.contains(expected)) {
            throw new AssertionError("סה\"כ ימים לא תואם. צפוי להכיל: " + expected + " אבל בפועל: " + text);
        }
    }

    public PassengersPage goNextToPassengers() {
        waits.click(nextToPassengersBtn);
        return new PassengersPage(driver);
    }
}
