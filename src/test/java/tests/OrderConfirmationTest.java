package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import pages.HomePage;
import pages.ProductDetailPage;
import utils.WaitUtil;

public class OrderConfirmationTest extends BaseTest {

    @Test(dataProvider = "productData")
    public void testOrderPlacementSuccess(String productName, String size, String color) throws Exception {
        System.out.println("+++++++++++++++ Starting Test Case +++++++++++++++");
        System.out.println("Product: " + productName + " | Size: " + size + " | Color: " + color);
        System.out.println("=================================================");

        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(productName);
        homePage.safeClickOnProduct(productName);

        ProductDetailPage pdp = new ProductDetailPage(driver);
        pdp.selectSize(size);
        pdp.selectColor(color);
        pdp.clickAddToCart();

        Assert.assertTrue(pdp.isAddToCartSuccessMessageDisplayed(), "❌ Add to cart failed!");

        // ✅ Click shopping cart link from success message
        pdp.clickShoppingCartLinkFromSuccessMessage();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickProceedToCheckoutButtonFromCartPage();

        checkoutPage.fillGuestShippingFormIfPresent("123 Broadway", "New York", "10001", "9876543210");
        checkoutPage.selectShippingMethodAndContinue();
        checkoutPage.clickPlaceOrder();

        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(), "❌ Order not placed!");
        System.out.println("✅ Order Placed Successfully for: " + productName);
        System.out.println("+++++++++++++++ Test Case Passed +++++++++++++++\n");
    }

    @DataProvider(name = "productData")
    public Object[][] productData() {
        return new Object[][]{
                {"Juno Jacket", "M", "Blue"},
                {"Juno Jacket", "XS", "Blue"},
                {"Juno Jacket", "L", "Green"}  // ✅ Add more as needed
        };
    }
}



//package tests;
//
//import base.BaseTest;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebElement;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import pages.CheckoutPage;
//import pages.HomePage;
//import pages.ProductDetailPage;
//import utils.WaitUtil;
//
//public class OrderConfirmationTest extends BaseTest {
//
//    @Test
//    public void testOrderPlacementSuccess() {
//        HomePage homePage = new HomePage(driver);
//
//        // ✅ Search and select product
//        homePage.searchProduct("Juno Jacket");
//        homePage.safeClickOnProduct("Juno Jacket");
//
//        // ✅ Select size & color
//        ProductDetailPage productDetailPage = new ProductDetailPage(driver);
//        productDetailPage.selectSize("M");
//        productDetailPage.selectColor("Blue");
//
//        try {
//            productDetailPage.clickAddToCart();
//            Assert.assertTrue(productDetailPage.isAddToCartSuccessMessageDisplayed(), "❌ Add to Cart failed");
//        } catch (Exception e) {
//            Assert.fail("❌ Exception during Add to Cart: " + e.getMessage());
//        }
//
//        // ✅ Click 'shopping cart' link in success message
//        productDetailPage.clickShoppingCartLinkInSuccessMessage();
//
//        // ✅ Click "Proceed to Checkout" on Shopping Cart Page using JavaScript
//        try {
//            By proceedToCheckoutBtn = By.cssSelector("button[data-role='proceed-to-checkout']");
//            WebElement checkoutBtn = WaitUtil.waitForElementToBeClickable(driver, proceedToCheckoutBtn, 10);
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", checkoutBtn);
//            System.out.println("✅ Clicked 'Proceed to Checkout' on Shopping Cart page");
//        } catch (Exception e) {
//            Assert.fail("❌ Failed to click 'Proceed to Checkout' on Cart Page: " + e.getMessage());
//        }
//
//        // ✅ Continue with shipping and order placement
//        CheckoutPage checkoutPage = new CheckoutPage(driver);
//        checkoutPage.selectNewAddressIfNeeded("123 Broadway", "New York", "10001", "9876543210");
//        checkoutPage.fillGuestShippingFormIfPresent("123 Broadway", "New York", "10001", "9876543210");
//        checkoutPage.selectShippingMethodAndContinue();
//        checkoutPage.clickPlaceOrder();
//
//        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(), "❌ Order was not placed successfully");
//    }
//}
