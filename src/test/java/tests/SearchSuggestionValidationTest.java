package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductDetailPage;

import java.util.List;

public class SearchSuggestionValidationTest extends BaseTest {

    @Test
    public void testSearchSuggestions() throws InterruptedException {
        String searchTerm = "juno"; // partial text
        final String expectedProduct = "Juno Jacket";

        HomePage homePage = new HomePage(driver);
        homePage.typeInSearchBox(searchTerm);

        List<String> suggestions = homePage.getSearchSuggestions();
        Assert.assertFalse(suggestions.isEmpty(), "❌ No suggestions found for: " + searchTerm);

        boolean found = suggestions.stream()
            .anyMatch(s -> s.toLowerCase().contains(expectedProduct.toLowerCase()));
        Assert.assertTrue(found, "❌ Expected product not found in suggestions: " + expectedProduct);

        homePage.clickSuggestion(expectedProduct);

        ProductDetailPage pdp = new ProductDetailPage(driver);
        String actualTitle = pdp.getProductTitle().trim();
        Assert.assertTrue(actualTitle.toLowerCase().contains(expectedProduct.toLowerCase()),
                "❌ Wrong product page. Expected: " + expectedProduct + ", Found: " + actualTitle);

        System.out.println("✅ Auto-suggestion test passed");
    }
}
