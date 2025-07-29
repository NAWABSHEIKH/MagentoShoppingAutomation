package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

import java.util.List;

public class ProductDetailPage {
    WebDriver driver;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "button#product-addtocart-button")
    WebElement addToCartButton;

    @FindBy(css = "div.message-success.success.message")
    WebElement successMessage;

    @FindBy(css = "div.swatch-attribute.size div.swatch-option")
    List<WebElement> sizeOptions;

    @FindBy(css = "div.swatch-attribute.color div.swatch-option")
    List<WebElement> colorOptions;
    
    @FindBy(css = "a.action.viewcart")
    WebElement viewAndEditCartButton;
    
    @FindBy(css = "span.base")
    WebElement productTitle;
    
    @FindBy(css = "div.message-success a[href*='checkout/cart']")
    WebElement shoppingCartLinkInSuccessMessage;



    // ✅ Click via JavaScript to avoid iframe interception issues
    private void safeClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // ✅ Select specific size by visible label (e.g., "XS", "M")
    public void selectSize(String desiredSize) {
        try {
            for (WebElement option : sizeOptions) {
                String label = option.getAttribute("aria-label");
                if (label != null && label.equalsIgnoreCase(desiredSize)) {
                    safeClick(option);
                    System.out.println("✅ Size selected: " + label);
                    return;
                }
            }
            System.out.println("⚠️ Desired size not found: " + desiredSize);
        } catch (Exception e) {
            System.out.println("❌ Failed to select size: " + e.getMessage());
        }
    }

    // ✅ Select specific color by visible label (e.g., "Red", "Blue")
    public void selectColor(String desiredColor) {
        try {
            for (WebElement option : colorOptions) {
                String label = option.getAttribute("aria-label");
                if (label != null && label.equalsIgnoreCase(desiredColor)) {
                    safeClick(option);
                    System.out.println("✅ Color selected: " + label);
                    return;
                }
            }
            System.out.println("⚠️ Desired color not found: " + desiredColor);
        } catch (Exception e) {
            System.out.println("❌ Failed to select color: " + e.getMessage());
        }
    }

    public void clickAddToCart() throws Exception {
        try {
            WaitUtil.waitForElementToBeClickable(driver, addToCartButton, 10);
            safeClick(addToCartButton);
            System.out.println("🛒 Clicked Add to Cart");
        } catch (Exception e) {
            System.out.println("❌ Could not click Add to Cart: " + e.getMessage());
            throw e;
        }
    }

    public boolean isAddToCartSuccessMessageDisplayed() {
        try {
            WaitUtil.waitForElementToBeVisible(driver, successMessage, 10);
            return successMessage.getText().toLowerCase().contains("you added");
        } catch (Exception e) {
            return false;
        }
    }
// // ✅ Click cart icon  (the original one )
//    public void clickCartIcon() {
//        try {
//            WebElement cartIcon = driver.findElement(By.cssSelector("a.action.showcart"));
//            cartIcon.click();
//            System.out.println("🛒 Clicked cart icon.");
//        } catch (Exception e) {
//            System.out.println("❌ Failed to click cart icon: " + e.getMessage());
//        }
//    }
    
    public void clickCartIcon() {
        try {
            By cartCountLocator = By.cssSelector("span.counter-number");

            // Wait until the cart count updates (not empty or 0)
            WebElement counter = WaitUtil.waitForElementToBeVisible(driver, cartCountLocator, 10);
            String countText = counter.getText().trim();
            int waitAttempts = 0;

            while ((countText.isEmpty() || countText.equals("0")) && waitAttempts < 5) {
                WaitUtil.waitForSeconds(1);
                countText = counter.getText().trim();
                waitAttempts++;
            }

            if (countText.isEmpty() || countText.equals("0")) {
                System.out.println("⚠️ Cart count still 0 after retries.");
            }

            WebElement cartIcon = driver.findElement(By.cssSelector("a.action.showcart"));
            WaitUtil.waitForElementToBeClickable(driver, cartIcon, 10);

            // Use JavaScript click for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartIcon);
            System.out.println("🛒 Safely clicked on cart icon.");

        } catch (Exception e) {
            System.out.println("❌ Failed to click cart icon: " + e.getMessage());
            throw new RuntimeException("Cart icon click failed");
        }
    }


//    // ✅ Click Proceed to Checkout  (Original one)
//    public void clickProceedToCheckout() {
//        try {
//            WebElement proceedButton = driver.findElement(By.id("top-cart-btn-checkout"));
//            WaitUtil.waitForElementToBeClickable(driver, proceedButton, 10);
//            proceedButton.click();
//            System.out.println("✅ Clicked Proceed to Checkout.");
//        } catch (Exception e) {
//            System.out.println("❌ Failed to click Proceed to Checkout: " + e.getMessage());
//        }
//    }
    
    public void clickProceedToCheckout() {
        try {
            // Wait until the minicart dropdown appears
            By proceedBtnLocator = By.cssSelector("button#top-cart-btn-checkout");
            WebElement proceedBtn = WaitUtil.waitForPresenceOfElement(driver, proceedBtnLocator, 10);
            WaitUtil.waitForElementToBeClickable(driver, proceedBtn, 10);

            // Click using JavaScript (more reliable for dropdowns)
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedBtn);
            System.out.println("✅ Clicked Proceed to Checkout.");
        } catch (Exception e) {
            System.out.println("❌ Failed to click Proceed to Checkout: " + e.getMessage());
            throw new RuntimeException("Proceed to Checkout button not found");
        }
    }


//    public void clickViewAndEditCart() {
//        WaitUtil.waitForElementToBeClickable(driver, viewAndEditCartButton, 10);
//        viewAndEditCartButton.click();
//        System.out.println("🛒 Clicked View and Edit Cart");
//    }

 // Add this method inside your ProductDetailPage class
    public void clickViewAndEditCart() {
        try {
            By viewCartLocator = By.cssSelector("a.action.viewcart");
            WebElement viewCartBtn = WaitUtil.waitForElementToBeClickable(driver, viewCartLocator, 10);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCartBtn);
            System.out.println("✅ Clicked 'View and Edit Cart'");
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to click 'View and Edit Cart': " + e.getMessage());
        }
    }
   

    public String getProductTitle() {
        WaitUtil.waitForElementToBeVisible(driver, productTitle, 10);
        return productTitle.getText();
    }
    
    
    public void clickShoppingCartLinkInSuccessMessage() {
        try {
            WaitUtil.waitForElementToBeClickable(driver, shoppingCartLinkInSuccessMessage, 10);
            shoppingCartLinkInSuccessMessage.click();
            System.out.println("🛒 Clicked 'shopping cart' link in success message.");
        } catch (Exception e) {
            System.out.println("❌ Failed to click 'shopping cart' link: " + e.getMessage());
            throw new RuntimeException("Could not click shopping cart link.");
        }
    }


 // ✅ Click 'shopping cart' link from success message
    public void clickShoppingCartFromSuccessMessage() {
        try {
            By shoppingCartLink = By.xpath("//a[text()='shopping cart']");
            WebElement link = WaitUtil.waitForElementToBeClickable(driver, shoppingCartLink, 10);
            link.click();
            System.out.println("✅ Clicked 'shopping cart' link from success message");
        } catch (Exception e) {
            System.out.println("❌ Failed to click shopping cart link: " + e.getMessage());
        }
    }
    public void clickShoppingCartLinkFromSuccessMessage() {
        By shoppingCartLink = By.xpath("//a[contains(text(),'shopping cart')]");
        WebElement cartLink = WaitUtil.waitForElementToBeClickable(driver, shoppingCartLink, 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", cartLink);
        System.out.println("✅ Clicked 'shopping cart' link from success message");
    }

    
}
