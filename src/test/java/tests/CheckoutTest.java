package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.AdHandlerUtil;

public class CheckoutTest extends BaseTest {

    @Test
    public void testCompleteCheckoutFlow() throws Exception {
        String email = "testuser@example.com";  // ✅ use valid test login email
        String password = "Test@1234";
        String productName = "Juno Jacket";

        // Step 1: Login
        HomePage homePage = new HomePage(driver);
        homePage.clickSignIn();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        // Step 2: Search and select product
        AdHandlerUtil.removeAds(driver);
        homePage.searchProduct(productName);
        homePage.clickOnProduct(productName);

        // Step 3: Add to Cart
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);
        productDetailPage.selectSize("XS");
        productDetailPage.selectColor("Blue");  // ✅ Make sure color exists
        productDetailPage.clickAddToCart();

        Assert.assertTrue(productDetailPage.isAddToCartSuccessMessageDisplayed(), "❌ Product not added to cart");

        // Step 4: Go to Checkout page manually
        driver.get("https://magento.softwaretestingboard.com/checkout/"); // update URL if needed

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillShippingDetails("123 Main St", "New York", "10001", "1234567890");
        checkoutPage.chooseShippingMethodAndContinue();

        // Wait for payment step to load
        Thread.sleep(3000);

        checkoutPage.clickPlaceOrder();
        
        Thread.sleep(3000); // give page time to render after place order

        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(), "❌ Order was not placed successfully");
    }
}
