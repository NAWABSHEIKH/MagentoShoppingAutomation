package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

public class OrderConfirmationPage {
    WebDriver driver;

    public OrderConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ✅ Locators
    private By thankYouMessage = By.xpath("//span[contains(text(),'Thank you for your purchase!')]");
    private By orderNumber = By.cssSelector("a.order-number");

    // ✅ Methods
    public boolean isThankYouMessageDisplayed() {
        try {
            WaitUtil.waitForPresenceOfElement(driver, thankYouMessage, 10);
            return driver.findElement(thankYouMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getOrderNumber() {
        try {
            WaitUtil.waitForPresenceOfElement(driver, orderNumber, 10);
            return driver.findElement(orderNumber).getText().trim();
        } catch (Exception e) {
            return null;
        }
    }
}
