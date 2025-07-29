package utils;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {

    // Wait for element to be visible
    public static void waitForElementToBeVisible(WebDriver driver, WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    // Wait for element to be clickable
    public static void waitForElementToBeClickable(WebDriver driver, WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(element));
    }
    
 // ✅ Version 2: Accepts By and returns WebElement
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Wait for element to be present using By locator
    public static WebElement waitForPresenceOfElement(WebDriver driver, By locator, int timeoutInSeconds) {
        return new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Wait for alert
    public static void waitForAlert(WebDriver driver, int timeoutInSeconds) {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.alertIsPresent());
    }
    
 // Safe wait for element by locator (used before ads might block input fields)
    public static WebElement waitForElementSafe(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            return new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Element not visible within " + timeoutInSeconds + " seconds: " + locator);
            return null;
        }
        
    }
    
 // Static sleep utility (used when fixed wait is necessary)
    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("⏱️ Sleep interrupted: " + e.getMessage());
        }
    }
    
 // Wait until an element becomes invisible (useful for loaders/overlays)
    public static void waitForInvisibilityOfElement(WebDriver driver, By locator, int timeoutInSeconds) {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
            .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

 // Wait for multiple elements to be visible
//    public static void waitForElementsToBeVisible(WebDriver driver, final java.util.List<WebElement> elements, int timeoutInSeconds) {
//        new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
//                .until(new Function<WebDriver, Object>() {
//					@Override
//					public Object apply(WebDriver driver1) {
//						return elements.stream().allMatch(WebElement::isDisplayed);
//					}
//				});
//    }
    
 // Add this method in WaitUtil.java
    public static void waitForElementsToBeVisible(WebDriver driver, List<WebElement> elements, int timeoutInSeconds) {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
            .until(ExpectedConditions.visibilityOfAllElements(elements));
    }


    // Static sleep method (used as fallback fixed delay)
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("⏱️ Sleep interrupted: " + e.getMessage());
        }
    }

    
//    public static void waitForElementToBeVisible(WebDriver driver, By locator, int timeoutInSeconds) {
//        new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
//            .until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }

    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        return new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
            .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }



 // Wait until DOM is fully loaded + Men tab is clickable
    public static void waitUntilPageReady(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Step 1: Wait until document ready state
        new WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
            d -> js.executeScript("return document.readyState").equals("complete")
        );

        // Step 2: Ensure Men tab is clickable (adjust if you're on a different page)
        try {
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("Men")));
        } catch (TimeoutException e) {
            System.out.println("⚠️ 'Men' tab not clickable even after DOM ready.");
        }
    }

    public static boolean retryingClick(WebDriver driver, WebElement element, int maxAttempts) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                element.click();
                return true;
            } catch (Exception e) {
                System.out.println("Retrying click attempt " + (attempts + 1) + " failed. " + e.getMessage());
            }
            WaitUtil.waitForSeconds(1);
            attempts++;
        }
        return false;
    }

// // By version (returns WebElement)
//    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator, int timeoutInSeconds) {
//        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
//                .until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }

    public static WebElement waitForElementVisibleOrNull(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            return new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return null;
        }
    }

    

    
}
    
