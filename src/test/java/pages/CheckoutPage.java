package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.WaitUtil;

import java.util.List;

public class CheckoutPage {
    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // --- Email field (for guest checkout) ---
    @FindBy(id = "customer-email")
    WebElement emailInput;

    // --- New Address Button ---
    @FindBy(css = "button.action-show-popup")
    WebElement newAddressButton;

    // --- Address form fields ---
    @FindBy(name = "firstname")
    WebElement firstNameInput;

    @FindBy(name = "lastname")
    WebElement lastNameInput;

    @FindBy(name = "company")
    WebElement companyInput;

    @FindBy(name = "street[0]")
    WebElement streetInput;

    @FindBy(name = "city")
    WebElement cityInput;

    @FindBy(name = "region_id")
    WebElement stateDropdown;

    @FindBy(name = "postcode")
    WebElement zipInput;

    @FindBy(name = "country_id")
    WebElement countryDropdown;

    @FindBy(name = "telephone")
    WebElement phoneInput;

    // --- Ship Here button ---
    @FindBy(css = "button.action-save-address")
    WebElement shipHereButton;

    // --- Shipping Method section ---
    @FindBy(css = "button.continue")
    WebElement nextButton;

    // --- Place Order Button ---
    @FindBy(css = "button.action.primary.checkout")
    WebElement placeOrderButton;

    // --- Order Success Message ---
    @FindBy(xpath = "//h1[contains(@class, 'page-title')]//span[contains(text(), 'Thank you for your purchase')]")
    WebElement orderSuccessMessage;

    // ✅ Main method to fill form (for both first and second appearance)
    public void fillShippingDetails(String address, String city, String zip, String phone) {
        try {
            WaitUtil.waitForInvisibilityOfElement(driver, By.id("checkout-loader"), 15);

            // ✅ Fill email if visible
            try {
                if (emailInput.isDisplayed()) {
                    emailInput.clear();
                    emailInput.sendKeys("guest" + System.currentTimeMillis() + "@mailinator.com");
                    System.out.println("📧 Guest email entered");
                }
            } catch (Exception e) {
                System.out.println("ℹ️ Email field not found or already filled");
            }

            // ✅ Click "New Address" if available
            if (driver.findElements(By.cssSelector("button.action-show-popup")).size() > 0) {
                WaitUtil.waitForElementToBeClickable(driver, newAddressButton, 10);
                newAddressButton.click();
                System.out.println("✅ Clicked New Address");
            }

            // ✅ Fill form fields
            WaitUtil.waitForElementToBeVisible(driver, streetInput, 10);
            firstNameInput.clear();
            firstNameInput.sendKeys("Mary");

            lastNameInput.clear();
            lastNameInput.sendKeys("User");

            companyInput.clear();
            companyInput.sendKeys("N/A");

            streetInput.clear();
            streetInput.sendKeys(address);

            cityInput.clear();
            cityInput.sendKeys(city);

            new Select(stateDropdown).selectByVisibleText("New York");

            zipInput.clear();
            zipInput.sendKeys(zip);

            phoneInput.clear();
            phoneInput.sendKeys(phone);

            // ✅ Click "Ship Here" if present
            if (driver.findElements(By.cssSelector("button.action-save-address")).size() > 0) {
                WaitUtil.waitForElementToBeClickable(driver, shipHereButton, 10);
                shipHereButton.click();
                System.out.println("🚚 Clicked Ship Here");
            }

        } catch (Exception e) {
            System.out.println("❌ Error filling shipping details: " + e.getMessage());
        }
    }

//    // ✅ For second guest form  (the real one)
//    public void fillGuestShippingFormIfPresent(String address, String city, String zip, String phone) {
//        try {
//            WaitUtil.waitForInvisibilityOfElement(driver, By.id("checkout-loader"), 10);
//            Thread.sleep(1500);
//            if (driver.findElements(By.id("customer-email")).size() > 0 || driver.findElements(By.name("firstname")).size() > 0) {
//                System.out.println("🔄 Guest form detected again, refilling...");
//                fillShippingDetails(address, city, zip, phone);
//            } else {
//                System.out.println("✅ No second guest form found.");
//            }
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not fill guest form again: " + e.getMessage());
//        }
//    }
    
    
//  (the working one commented)
//    public void chooseShippingMethodAndContinue() {
//        try {
//            WaitUtil.waitForInvisibilityOfElement(driver, By.id("checkout-loader"), 15);
//            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 400);");
//            Thread.sleep(2000);
//
//            List<WebElement> shippingOptions = driver.findElements(By.cssSelector("input[type='radio'][name^='ko_unique']"));
//            if (shippingOptions.isEmpty()) throw new Exception("❌ No shipping options found");
//
//            WebElement firstOption = shippingOptions.get(0);
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstOption);
//            System.out.println("📦 Selected shipping option");
//
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextButton);
//            WaitUtil.waitForElementToBeClickable(driver, nextButton, 10);
//            nextButton.click();
//            System.out.println("➡️ Clicked Next");
//
//        } catch (Exception e) {
//            System.out.println("❌ Error selecting shipping method: " + e.getMessage());
//        }
//    }

    public void clickPlaceOrder() {
        try {
            WaitUtil.waitForInvisibilityOfElement(driver, By.id("checkout-loader"), 15);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeOrderButton);
            WaitUtil.waitForElementToBeClickable(driver, placeOrderButton, 10);
            placeOrderButton.click();
            System.out.println("💳 Clicked Place Order");
        } catch (Exception e) {
            System.out.println("❌ Error clicking Place Order: " + e.getMessage());
        }
    }

//    the real one which is working
//    public boolean isOrderPlacedSuccessfully() {
//        try {
//            WaitUtil.waitForInvisibilityOfElement(driver, By.id("checkout-loader"), 10);
//            WaitUtil.waitForElementToBeVisible(driver, orderSuccessMessage, 15);
//            String text = orderSuccessMessage.getText().trim().toLowerCase();
//            return text.contains("thank you for your purchase");
//        } catch (Exception e) {
//            System.out.println("❌ Success message not found: " + e.getMessage());
//            return false;
//        }
//    }

    public String getOrderNumber() {
        try {
            return driver.findElement(By.cssSelector("a.order-number")).getText().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public void selectNewAddressIfNeeded(String address, String city, String zip, String phone) {
        try {
            if (driver.findElements(By.cssSelector("button.action-show-popup")).size() > 0) {
                System.out.println("📬 Filling shipping address");
                fillShippingDetails(address, city, zip, phone);
            } else {
                System.out.println("📦 Using saved address");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Address detection failed: " + e.getMessage());
        }
    }
    
    public void fillGuestShippingFormIfPresent(String address, String city, String zip, String phone) {
        try {
            WaitUtil.waitForInvisibilityOfElement(driver, By.id("checkout-loader"), 10);
            Thread.sleep(1500);

            if (driver.findElements(By.id("customer-email")).size() > 0 || driver.findElements(By.name("firstname")).size() > 0) {
                System.out.println("🔄 Guest form detected again, refilling...");

                // NEW: Retry logic for guest form
                for (int attempt = 0; attempt < 3; attempt++) {
                    try {
                        fillShippingDetails(address, city, zip, phone);
                        return;
                    } catch (Exception e) {
                        System.out.println("⚠️ Retry guest form fill: " + (attempt + 1));
                        Thread.sleep(1500);
                    }
                }
            } else {
                System.out.println("✅ No second guest form found.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not fill guest form again: " + e.getMessage());
        }
    }
//    public void fillGuestShippingFormIfPresent(String address, String city, String zip, String phone) {
//        try {
//            WaitUtil.waitForInvisibilityOfElement(driver, By.id("checkout-loader"), 10);
//            Thread.sleep(1500);
//
//            if (driver.findElements(By.id("customer-email")).size() > 0 || driver.findElements(By.name("firstname")).size() > 0) {
//                System.out.println("🔄 Guest form detected again, refilling...");
//
//                // NEW: Retry logic for guest form
//                for (int attempt = 0; attempt < 3; attempt++) {
//                    try {
//                        fillShippingDetails(address, city, zip, phone);
//                        return;
//                    } catch (Exception e) {
//                        System.out.println("⚠️ Retry guest form fill: " + (attempt + 1));
//                        Thread.sleep(1500);
//                    }
//                }
//            } else {
//                System.out.println("✅ No second guest form found.");
//            }
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not fill guest form again: " + e.getMessage());
//        }
//    }
    
    public void clickProceedToCheckout() {
        try {
            WebElement proceedButton = WaitUtil.waitForPresenceOfElement(driver, By.id("top-cart-btn-checkout"), 10);
            WaitUtil.waitForElementToBeClickable(driver, proceedButton, 10);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedButton);
            System.out.println("✅ Clicked Proceed to Checkout.");
        } catch (Exception e) {
            System.out.println("❌ Failed to click Proceed to Checkout: " + e.getMessage());
        }
    }
    
    
    public void chooseShippingMethodAndContinue() {
        try {
            WaitUtil.waitForInvisibilityOfElement(driver, By.id("checkout-loader"), 15);
            Thread.sleep(1500);
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");

            List<WebElement> shippingOptions = driver.findElements(By.cssSelector("input[type='radio'][name^='ko_unique']"));
            if (shippingOptions.isEmpty()) {
                throw new NoSuchElementException("❌ No shipping options found");
            }

            WebElement firstOption = shippingOptions.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstOption);
            System.out.println("📦 Selected shipping option");

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextButton);
            WaitUtil.waitForElementToBeClickable(driver, nextButton, 10);
            nextButton.click();
            System.out.println("➡️ Clicked Next");

        } catch (Exception e) {
            System.out.println("❌ Error selecting shipping method: " + e.getMessage());
        }
    }

    
    public boolean isOrderPlacedSuccessfully() {
        try {
            WaitUtil.waitForInvisibilityOfElement(driver, By.id("checkout-loader"), 15);
            WebElement successMessageElement = WaitUtil.waitForElementToBeVisible(driver,
                    By.xpath("//h1[contains(@class, 'page-title')]//span[contains(text(), 'Thank you for your purchase')]"), 15);
            String text = successMessageElement.getText().trim().toLowerCase();
            return text.contains("thank you for your purchase");
        } catch (Exception e) {
            System.out.println("❌ Success message not found: " + e.getMessage());
            return false;
        }
    }


    public void safeClickPlaceOrderButton() {
        WebElement btn = driver.findElement(By.cssSelector("button.action.primary.checkout"));

        if (btn.isDisplayed() && btn.isEnabled()) {
            WaitUtil.retryingClick(driver, btn, 3);
        } else {
            System.out.println("⚠️ Place Order button not interactable. Attempting JS click...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
    }


    public void selectShippingMethodAndContinue() {
        chooseShippingMethodAndContinue();
    }
    
    public void clickProceedToCheckoutButtonFromCartPage() {
        By proceedToCheckoutBtn = By.cssSelector("button[data-role='proceed-to-checkout']");
        WebElement checkoutBtn = WaitUtil.waitForElementToBeClickable(driver, proceedToCheckoutBtn, 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", checkoutBtn);
        System.out.println("✅ Clicked 'Proceed to Checkout' on Shopping Cart page");
    }

}
