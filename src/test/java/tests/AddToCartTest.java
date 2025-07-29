package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.AdHandlerUtil;

public class AddToCartTest extends BaseTest {

	@Test
	public void addProductToCart() throws Exception {
	    String productName = "Juno Jacket";

	    HomePage homePage = new HomePage(driver);
	    AdHandlerUtil.removeAds(driver); // Step 1: Clean homepage ads

	    homePage.searchProduct(productName);
	    homePage.clickOnProduct(productName); // Step 2: Initial product click

	    AdHandlerUtil.removeAds(driver); // Step 3: Ad intercepts and blocks nav

	    // 🔁 If still on search page, re-click the product
	    if (!driver.getCurrentUrl().toLowerCase().contains("juno-jacket")) {
	        System.out.println("⚠️ Ad blocked navigation, retrying click...");
	        homePage.clickOnProduct(productName);
	    }

	    AdHandlerUtil.removeAds(driver); // Step 4: Final cleanup if needed

	    ProductDetailPage productPage = new ProductDetailPage(driver);
	    productPage.selectSize("XS");
	    productPage.selectColor("Blue");

	    AdHandlerUtil.removeAds(driver); // Step 5: Clean before final action

	    productPage.clickAddToCart();

	    Assert.assertTrue(productPage.isAddToCartSuccessMessageDisplayed(), "❌ Product not added to cart");
	}

}
