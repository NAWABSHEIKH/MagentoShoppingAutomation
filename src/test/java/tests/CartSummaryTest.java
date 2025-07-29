package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailPage;

import java.util.List;
import java.util.function.Predicate;

public class CartSummaryTest extends BaseTest {

    @Test
    public void testCartSummaryDetails() throws Exception {
        final String expectedProductName = "Juno Jacket";
        String expectedSize = "XS"; // ✅ Match the actual working size
        String expectedColor = "Blue";

        // Step 1: Search & click on product
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(expectedProductName);
        homePage.safeClickOnProduct(expectedProductName);

        // Step 2: Select size & color, add to cart
        ProductDetailPage pdp = new ProductDetailPage(driver);
        pdp.selectSize(expectedSize);
        pdp.selectColor(expectedColor);
        pdp.clickAddToCart();
        Assert.assertTrue(pdp.isAddToCartSuccessMessageDisplayed(), "❌ Add to cart failed!");

        // Step 3: Click on 'shopping cart' link in the success message
        pdp.clickShoppingCartFromSuccessMessage();  // ✅ Make sure this method is implemented

        // Step 4: Validate cart summary
        CartPage cartPage = new CartPage(driver);
        cartPage.waitForCartToLoad();  // ✅ wait for cart content

        List<String> productNames = cartPage.getCartItemNames();
        System.out.println("🛒 Products found in cart: " + productNames); // optional debug
        Assert.assertTrue(productNames.stream().anyMatch(new Predicate<String>() {
            @Override
            public boolean test(String name) {
                return name.contains(expectedProductName);
            }
        }), "❌ Product not found in cart!");

        List<Integer> quantities = cartPage.getCartItemQuantities();
        Assert.assertFalse(quantities.isEmpty(), "❌ Quantity list is empty!");
        Assert.assertTrue(quantities.get(0) > 0, "❌ Quantity is 0!");

        List<Double> prices = cartPage.getCartItemPrices();
        Assert.assertFalse(prices.isEmpty(), "❌ Price list is empty!");
        Assert.assertTrue(prices.get(0) > 0.0, "❌ Price is 0!");

        double total = cartPage.getTotalAmount();
        Assert.assertTrue(total > 0.0, "❌ Total amount is 0!");
        
        // Print cart summary with boundaries
        System.out.println("++++++++++++++ CART SUMMARY ++++++++++++++");

        for (int i = 0; i < productNames.size(); i++) {
            System.out.println("Product Name  : " + productNames.get(i));
            System.out.println("Quantity      : " + (i < quantities.size() ? quantities.get(i) : "N/A"));
            System.out.println("Price         : $" + (i < prices.size() ? prices.get(i) : "N/A"));
            System.out.println("------------------------------------------");
        }

        System.out.println("Total Amount  : $" + total);
        System.out.println("==========================================");
        System.out.println("✅ Cart Summary Validated Successfully");


        System.out.println("✅ Cart Summary Validated Successfully");
    }
}
