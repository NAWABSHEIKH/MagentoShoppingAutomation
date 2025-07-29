package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

import java.util.ArrayList;
import java.util.List;

public class CartPage {

    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // --- Product names in cart ---
    @FindBy(css = "strong.product-item-name a")
    List<WebElement> productNames;

    // --- Product quantities ---
    @FindBy(css = "input.input-text.qty")
    List<WebElement> productQuantities;

    // --- Product prices ---
    @FindBy(css = "span.cart-price > span.price")
    List<WebElement> productPrices;

    // --- Subtotal or grand total ---
    @FindBy(css = "td.amount .price")
    WebElement grandTotal;

    // ✅ NEW: Wait until product names are visible (cart loaded)
    public void waitForCartToLoad() {
        By cartProductName = By.cssSelector("strong.product-item-name a");
        WaitUtil.waitForPresenceOfElement(driver, cartProductName, 15);
    }

    public List<String> getCartItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement item : productNames) {
            names.add(item.getText().trim());
        }
        return names;
    }

    public List<Integer> getCartItemQuantities() {
        List<Integer> quantities = new ArrayList<>();
        for (WebElement qty : productQuantities) {
            try {
                quantities.add(Integer.parseInt(qty.getAttribute("value")));
            } catch (Exception e) {
                quantities.add(0);
            }
        }
        return quantities;
    }

    public List<Double> getCartItemPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement price : productPrices) {
            try {
                String clean = price.getText().replace("$", "").replace(",", "").trim();
                prices.add(Double.parseDouble(clean));
            } catch (Exception e) {
                prices.add(0.0);
            }
        }
        return prices;
    }

    public double getTotalAmount() {
        try {
            WaitUtil.waitForElementToBeVisible(driver, grandTotal, 10);
            String totalText = grandTotal.getText().replace("$", "").replace(",", "").trim();
            return Double.parseDouble(totalText);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
