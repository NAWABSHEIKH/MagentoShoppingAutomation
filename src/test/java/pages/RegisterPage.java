package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

public class RegisterPage {
    WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "firstname")
    WebElement firstNameInput;

    @FindBy(id = "lastname")
    WebElement lastNameInput;

    @FindBy(id = "email_address")
    WebElement emailInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(id = "password-confirmation")
    WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[@title='Create an Account']")
    WebElement createAccountButton;

    // ✅ Handles popup using JS click (improved logic)
    public void closePopupIfPresent() {
        try {
            Thread.sleep(2000); // wait for popup to render
            WebElement popupClose = driver.findElement(By.cssSelector("#ltkpopup-close-button"));
            if (popupClose.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", popupClose);
            }
        } catch (Exception e) {
            // If popup doesn't appear, skip silently
        }
    }

    public void registerNewUser(String firstName, String lastName, String email, String password) {
        WaitUtil.waitForElementToBeVisible(driver, firstNameInput, 10);
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        confirmPasswordInput.sendKeys(password);
        createAccountButton.click();
    }

}
