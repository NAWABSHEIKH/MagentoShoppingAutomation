package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

public class AccountPage {
    WebDriver driver;

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div.page-title > h1 span")
    WebElement accountTitle;

    @FindBy(css = "div.message-success.success.message")
    WebElement registrationSuccessMessage;

    @FindBy(css = "li.customer-welcome span.customer-name")
    WebElement customerNameTopRight;

    public boolean isRegistrationSuccessMessageDisplayed() {
        try {
            WaitUtil.waitForElementToBeVisible(driver, registrationSuccessMessage, 10);
            return registrationSuccessMessage.getText().toLowerCase().contains("thank you for registering");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWelcomeTitleVisible() {
        try {
            WaitUtil.waitForElementToBeVisible(driver, accountTitle, 10);
            return accountTitle.getText().toLowerCase().contains("my account");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserLoggedInTopWelcomeMessagePresent() {
        try {
            WaitUtil.waitForElementToBeVisible(driver, customerNameTopRight, 10);
            return customerNameTopRight.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}





//package pages;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import utils.WaitUtil;
//
//public class AccountPage {
//    WebDriver driver;
//
//    public AccountPage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//    }
//
//    @FindBy(css = "div.page-title > h1 span")
//    WebElement accountTitle;
//
//    @FindBy(css = "div.message-success.success.message")
//    WebElement registrationSuccessMessage;
//
//    public boolean isRegistrationSuccessMessageDisplayed() {
//        try {
//            WaitUtil.waitForElementToBeVisible(driver, registrationSuccessMessage, 10);
//            return registrationSuccessMessage.getText().toLowerCase().contains("thank you for registering");
//        } catch (Exception e) {
//            return false;
//        }
//    }
//    
//    public boolean isWelcomeTitleVisible() {
//        try {
//            WaitUtil.waitForElementToBeVisible(driver, accountTitle, 10);
//            return accountTitle.getText().toLowerCase().contains("my account");
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//}
