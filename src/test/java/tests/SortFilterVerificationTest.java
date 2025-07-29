package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;
import utils.AdHandlerUtil;

import java.util.List;

public class SortFilterVerificationTest extends BaseTest {

    @Test
    public void verifySortByPrice() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToJacketsCategory();
        
       
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.sortBy("Price");

        List<String> productNames = categoryPage.getProductNames();
        List<Double> productPrices = categoryPage.getProductPrices();

        System.out.println("\n🛍️ Sorted Product List:");
        for (int i = 0; i < productNames.size(); i++) {
            System.out.println("➡️ " + productNames.get(i) + " | ₹" + productPrices.get(i));
        }

        for (int i = 1; i < productPrices.size(); i++) {
            Assert.assertTrue(productPrices.get(i) >= productPrices.get(i - 1),
                    "❌ Price not sorted at index " + i + ": " + productPrices.get(i - 1) + " > " + productPrices.get(i));
        }

        System.out.println("\n✅ Sort by Price verification passed.");
    }
}
