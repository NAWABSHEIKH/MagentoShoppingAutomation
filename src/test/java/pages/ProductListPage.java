package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

public class ProductListPage {
    WebDriver driver;

    public ProductListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Adjust the XPath to target a product card or name
    @FindBy(xpath = "(//li[contains(@class, 'product')])[1]//a")
    WebElement firstProduct;

    public void clickOnFirstProduct() {
        WaitUtil.waitForElementToBeClickable(driver, firstProduct, 10);
        firstProduct.click();
    }
}
