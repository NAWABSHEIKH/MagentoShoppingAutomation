package pages;

import utils.AdHandlerUtil;
import utils.WaitUtil;
import utils.WaitUtilWithRetry;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='panel header']//a[contains(text(),'Sign In')]")
    WebElement signInLink;

    @FindBy(xpath = "//a[@class='action create primary']")
    WebElement createAccountButton;

    @FindBy(id = "search")
    WebElement searchBox;

    @FindBy(css = "button.action.search")
    WebElement searchButton;

    @FindBy(id = "search")
    WebElement searchInput;
    
 // ✅ Updated suggestionItems locator
    @FindBy(xpath = "//div[@id='search_autocomplete']//li")
    List<WebElement> suggestionItems;

    // ✅ Updated locator for suggestion items
//    @FindBy(xpath = "//ul[contains(@class,'search-autocomplete')]//li")
//    List<WebElement> suggestionItems;

    public void clickCreateAccount() {
        WaitUtil.waitForElementToBeClickable(driver, signInLink, 10);
        signInLink.click();

        boolean clicked = false;
        int attempts = 0;

        while (!clicked && attempts < 3) {
            try {
                AdHandlerUtil.removeAds(driver);
                WaitUtil.waitForElementToBeClickable(driver, createAccountButton, 10);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createAccountButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createAccountButton);
                clicked = true;

                WebElement firstName = WaitUtil.waitForElementSafe(driver, By.id("firstname"), 5);
                if (firstName == null) {
                    System.out.println("⚠️ First name not found after click. Retrying...");
                    clicked = false;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Exception during createAccount click: " + e.getMessage());
            }
            attempts++;
        }

        if (!clicked) {
            throw new RuntimeException("❌ Failed to click 'Create Account' after retries");
        }
    }

    public void clickSignIn() {
        WaitUtil.waitForElementToBeClickable(driver, signInLink, 10);
        signInLink.click();
    }

    public void searchProduct(String productName) {
        WaitUtil.waitForElementToBeVisible(driver, searchBox, 10);
        searchBox.clear();
        searchBox.sendKeys(productName);
        searchButton.click();
    }

    public void clickOnProduct(String productName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement productLink = driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]"));

        js.executeScript("arguments[0].scrollIntoView(true);", productLink);
        js.executeScript("arguments[0].click();", productLink);

        try {
            Thread.sleep(2000);
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("juno-jacket")) {
                System.out.println("⚠️ Page still not on product detail. Trying click again.");
                js.executeScript("arguments[0].scrollIntoView(true);", productLink);
                js.executeScript("arguments[0].click();", productLink);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Retry failed: " + e.getMessage());
        }
    }

    public void safeClickOnProduct(String productName) {
        clickOnProduct(productName);
        AdHandlerUtil.removeAds(driver);
        if (!driver.getCurrentUrl().toLowerCase().contains(productName.toLowerCase().replace(" ", "-"))) {
            System.out.println("⚠️ Retrying product click after ad");
            clickOnProduct(productName);
        }
    }
    
 // ✅ Trick to trigger suggestions (don't remove existing ones)
    public void typeInSearchBox(String partialText) {
        searchInput.clear();
        searchInput.sendKeys(partialText);
        searchInput.sendKeys(Keys.SPACE);     // Force trigger
        searchInput.sendKeys(Keys.BACK_SPACE);
        System.out.println("⌨️ Typed in search box: " + partialText);
    }

    public List<String> getSearchSuggestions() {
        WaitUtil.waitForSeconds(1); // Let suggestion box load

        try {
            WebElement suggestionBox = driver.findElement(By.id("search_autocomplete"));
            String html = (String) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].outerHTML;", suggestionBox);
            System.out.println("🧪 Suggestion box HTML:\n" + html);
        } catch (Exception e) {
            System.out.println("❌ Suggestion box not found.");
        }

        WaitUtil.waitForPresenceOfElement(driver,
                By.xpath("//div[@id='search_autocomplete']//li"), 10);
        WaitUtil.waitForElementsToBeVisible(driver, suggestionItems, 10);

//        List<String> suggestions = suggestionItems.stream()
//                .map(WebElement::getText)
//                .filter(text -> !text.isEmpty())
//                .toList();
        List<String> suggestions = suggestionItems.stream()
        	    .map(WebElement::getText)
        	    .filter(text -> !text.isEmpty())
        	    .collect(Collectors.toList());

        suggestions.forEach(s -> System.out.println("🔍 Suggestion: " + s));
        return suggestions;
    }

    public void clickSuggestion(String partialMatchText) {
        for (WebElement item : suggestionItems) {
            if (item.getText().toLowerCase().contains(partialMatchText.toLowerCase())) {
                item.click();
                System.out.println("🖱️ Clicked on suggestion: " + partialMatchText);
                return;
            }
        }
        throw new RuntimeException("❌ Suggestion with text not found: " + partialMatchText);
    }
    public void navigateToJacketsCategory() {
        Actions actions = new Actions(driver);
        By sorterLocator = By.id("sorter");

        try {
            // Step 1: Hover and Click Jackets
            hoverAndClickJackets(actions);
            System.out.println("✅ Clicked Men > Tops > Jackets");

            // Step 2: Remove ads (if any)
            WaitUtil.waitForSeconds(2);
            AdHandlerUtil.removeAds(driver);
            WaitUtil.waitForSeconds(2);

            // Step 3: Check if redirected to homepage (i.e., not on Jackets page)
            if (!driver.getCurrentUrl().toLowerCase().contains("jackets")) {
                System.out.println("⚠️ Redirected after ad! Hovering again...");

                // Step 4: Hover again and click Jackets
                hoverAndClickJackets(actions);
                WaitUtil.waitForSeconds(2);
            }

            // Step 5: Final Wait for Sorter
            WaitUtil.waitForElementToBeVisible(driver, sorterLocator, 10);
            System.out.println("✅ Sorter dropdown is visible. Jackets page is ready.");

        } catch (Exception e) {
            System.out.println("❌ Failed to navigate to Jackets category: " + e.getMessage());
            throw e;
        }
    }

    private void hoverAndClickJackets(Actions actions) {
        WebElement menMenu = WaitUtil.waitForElementToBeVisible(driver, By.linkText("Men"), 10);
        actions.moveToElement(menMenu).perform();
        WaitUtil.waitForSeconds(1);

        WebElement topsMenu = WaitUtil.waitForElementToBeVisible(driver, By.linkText("Tops"), 10);
        actions.moveToElement(topsMenu).perform();
        WaitUtil.waitForSeconds(1);

        WebElement jacketsLink = WaitUtil.waitForElementToBeVisible(driver, By.linkText("Jackets"), 10);
        jacketsLink.click();
    }

    public void navigateToJacketsWithRetry() {
        Actions actions = new Actions(driver);

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                System.out.println("🔄 Attempt #" + attempt + " to navigate to Jackets");

                // Wait for page load and hover Men > Tops
                WaitUtil.waitUntilPageReady(driver);
                WebElement menMenu = WaitUtil.waitForElementToBeVisible(driver, By.linkText("Men"), 10);
                actions.moveToElement(menMenu).perform();
                WaitUtil.waitForSeconds(1);

                WebElement topsMenu = WaitUtil.waitForElementToBeVisible(driver, By.linkText("Tops"), 10);
                actions.moveToElement(topsMenu).perform();
                WaitUtil.waitForSeconds(1);

                WebElement jacketsLink = WaitUtil.waitForElementToBeVisible(driver, By.linkText("Jackets"), 10);
                jacketsLink.click();

                System.out.println("✅ Clicked Jackets. Waiting for page and removing ads...");
                WaitUtil.waitForSeconds(3);
                AdHandlerUtil.removeAds(driver); // Ad appears after click

                // Wait for Jackets page
                boolean sorterPresent = WaitUtil.waitForElementVisibleOrNull(driver, By.id("sorter"), 5) != null;

                if (sorterPresent) {
                    System.out.println("✅ Jackets page loaded successfully");
                    return;
                } else {
                    System.out.println("⚠️ Still not on Jackets page. Retrying...");
                }

            } catch (Exception e) {
                System.out.println("❌ Attempt #" + attempt + " failed: " + e.getMessage());
            }

            WaitUtil.waitForSeconds(2);
        }

        throw new RuntimeException("❌ Failed to navigate to Jackets after 3 retries.");
    }





//    // ✅ Improved typing method to trigger suggestions
//    public void typeInSearchBox(String partialText) {
//        searchInput.clear();
//        searchInput.sendKeys(partialText);
//        searchInput.sendKeys(Keys.SPACE);     // Trick to force suggestion load
//        searchInput.sendKeys(Keys.BACK_SPACE);
//        System.out.println("⌨️ Typed in search box: " + partialText);
//    }
//
//    public List<String> getSearchSuggestions() {
//        // Wait for autocomplete to be shown
//        WaitUtil.waitForSeconds(1);
//
//        try {
//            WebElement suggestionBox = driver.findElement(By.id("search_autocomplete"));
//            String html = (String) ((JavascriptExecutor) driver)
//                    .executeScript("return arguments[0].outerHTML;", suggestionBox);
//            System.out.println("🧪 Suggestion box HTML:\n" + html);
//        } catch (Exception e) {
//            System.out.println("❌ Suggestion box element NOT found.");
//        }
//
//        // Wait for list items
//        WaitUtil.waitForPresenceOfElement(driver,
//                By.xpath("//div[@id='search_autocomplete']//li"), 10);
//        WaitUtil.waitForElementsToBeVisible(driver, suggestionItems, 10);
//
//        List<String> suggestions = suggestionItems.stream()
//                .map(WebElement::getText)
//                .filter(text -> !text.isEmpty())
//                .toList();
//
//        suggestions.forEach(s -> System.out.println("🔍 Suggestion: " + s));
//        return suggestions;
//    }
//
//
//    public void clickSuggestion(String partialMatchText) {
//        for (WebElement item : suggestionItems) {
//            if (item.getText().contains(partialMatchText)) {
//                item.click();
//                System.out.println("🖱️ Clicked on suggestion: " + partialMatchText);
//                return;
//            }
//        }
//        throw new RuntimeException("❌ Suggestion with text not found: " + partialMatchText);
//    }
}
