package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LandingPage;

public class PurchaseFlowTest extends BaseTest {

    @Test
    public void purchaseFlow_shouldReachPassengersPage_withDynamicDates() {
        new LandingPage(driver)
                .open()
                .clickFirstTimePurchase()
                .chooseAnyContinent()
                .goNextToDates()
                .selectDynamicDates()
                .goNextToPassengers()
                .assertPageOpened();
    }
}
