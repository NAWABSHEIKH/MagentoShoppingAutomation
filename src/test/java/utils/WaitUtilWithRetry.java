package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.function.Consumer;

public class WaitUtilWithRetry {

    /**
     * Retry a navigation logic (like hover and click) until a condition is met.
     * For example: navigate to Jackets page until sorter is visible.
     *
     * @param driver The WebDriver instance.
     * @param actionLogic The logic to retry (e.g., hover and click Jackets).
     * @param maxAttempts Number of retries.
     * @param successCondition Locator of element that confirms success (e.g., sorter dropdown).
     */
    public static void retryUntilCondition(WebDriver driver, Consumer<WebDriver> actionLogic, int maxAttempts, By successCondition) {
        int attempts = 0;
        boolean success = false;

        while (attempts < maxAttempts) {
            try {
                actionLogic.accept(driver); // Run the logic (e.g., hover & click jackets)
                WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(successCondition));
                System.out.println("✅ Condition met on attempt #" + (attempts + 1));
                success = true;
                break;
            } catch (Exception e) {
                System.out.println("🔁 Attempt #" + (attempts + 1) + " failed: " + e.getMessage());
                WaitUtil.waitForSeconds(2);
            }
            attempts++;
        }

        if (!success) {
            throw new RuntimeException("❌ Failed to meet condition after " + maxAttempts + " attempts.");
        }
    }

    // Shortcut method for Jackets page navigation retry
    public static void retryJacketsNavigation(WebDriver driver, Consumer<WebDriver> hoverClickJacketsLogic) {
        By sorterLocator = By.id("sorter"); // success condition
        retryUntilCondition(driver, hoverClickJacketsLogic, 3, sorterLocator);
        
    }
}
