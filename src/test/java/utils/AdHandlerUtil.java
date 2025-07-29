package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdHandlerUtil {

    public static void removeAds(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean adRemoved = false;

        String[] adSelectors = {
            "#ltkpopup-overlay",
            "#ltkpopup-container",
            "#ltkpopup-close-button",
            "#dismiss-button",
            "[aria-label='Close ad']",
            "div[role='dialog']",
            "div[aria-label='Advertisement']",
            "div[class*='overlay']",
            "iframe[src*='ad']",
            "iframe[id*='google_ads']",
            "iframe[title='Advertisement']",
            "iframe"
        };

        for (String selector : adSelectors) {
            try {
                String script = "document.querySelectorAll('" + selector + "').forEach(e => e.remove());";
                js.executeScript(script);
                System.out.println("✅ Removed ad elements for selector: " + selector);
                adRemoved = true;
            } catch (Exception e) {
                System.out.println("⚠️ Could not remove ad selector: " + selector);
            }
        }

        // Force remove all visible iframes (for floating ads)
        try {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (WebElement iframe : iframes) {
                if (iframe.isDisplayed()) {
                    js.executeScript("arguments[0].remove();", iframe);
                    System.out.println("✅ Force removed visible iframe");
                    adRemoved = true;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not force remove iframe");
        }

        // 🔄 Final fallback: remove any leftover overlay/modal via JS
        try {
            js.executeScript("""
                document.querySelectorAll('div[role="dialog"], .popup, .modal, .backdrop, .fade, .overlay')
                .forEach(e => e.remove());
            """);
            System.out.println("✅ Removed fallback overlays");
        } catch (Exception e) {
            System.out.println("⚠️ Could not remove fallback overlays");
        }

        if (!adRemoved) {
            System.out.println("ℹ️ No ad elements matched for removal.");
        }
    }
}


//package utils;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//import java.util.List;
//
//public class AdHandlerUtil {
//
//    public static void removeAds(WebDriver driver) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        boolean adRemoved = false;
//
//        String[] adSelectors = {
//            "#ltkpopup-overlay",
//            "#ltkpopup-container",
//            "#ltkpopup-close-button",
//            "#dismiss-button",
//            "[aria-label='Close ad']",
//            "div[role='dialog']",
//            "div[aria-label='Advertisement']",
//            "div[class*='overlay']",
//            "iframe[src*='ad']",
//            "iframe[id*='google_ads']",
//            "iframe[title='Advertisement']",
//            "iframe"
//        };
//
//        for (String selector : adSelectors) {
//            try {
//                String script = "document.querySelectorAll('" + selector + "').forEach(e => e.remove());";
//                js.executeScript(script);
//                System.out.println("✅ Removed ad elements for selector: " + selector);
//                adRemoved = true;
//            } catch (Exception e) {
//                System.out.println("⚠️ Could not remove ad selector: " + selector);
//            }
//        }
//
//        // Additional: Remove iframes over center of screen
//        try {
//            List<WebElement> allIframes = driver.findElements(By.tagName("iframe"));
//            for (WebElement iframe : allIframes) {
//                if (iframe.isDisplayed()) {
//                    js.executeScript("arguments[0].remove();", iframe);
//                    System.out.println("✅ Force removed visible iframe");
//                    adRemoved = true;
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not force remove iframe");
//        }
//
//        if (!adRemoved) {
//            System.out.println("ℹ️ No ad elements matched for removal.");
//        }
//    }
//}
//
//
//
////package utils;
////
////import org.openqa.selenium.*;
////import org.openqa.selenium.support.ui.WebDriverWait;
////
////import java.time.Duration;
////
////public class AdHandlerUtil {
////
////    // ✅ This method removes all known ad/popup overlays
////    public static void removeAds(WebDriver driver) {
////        JavascriptExecutor js = (JavascriptExecutor) driver;
////        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
////        boolean adRemoved = false;
////
////        String[] adSelectors = {
////            "#ltkpopup-overlay",
////            "#ltkpopup-container",
////            "#ltkpopup-close-button",
////            "#dismiss-button",
////            "[aria-label='Close ad']",
////            "div[role='dialog']",
////            "div[aria-label='Advertisement']",
////            "div[class*='overlay']",
////            "iframe[src*='ad']"
////        };
////
////        for (String selector : adSelectors) {
////            try {
////                String script = "document.querySelectorAll('" + selector + "').forEach(e => e.remove());";
////                js.executeScript(script);
////                System.out.println("✅ Removed ad elements for selector: " + selector);
////                adRemoved = true;
////            } catch (Exception e) {
////                System.out.println("⚠️ Could not remove ad selector: " + selector);
////            }
////        }
////
////        // ❌ Close SVG ❌ icon under dismiss button
////        try {
////            WebElement svgDismiss = driver.findElement(By.xpath("//div[@id='dismiss-button']//svg"));
////            if (svgDismiss.isDisplayed()) {
////                js.executeScript("arguments[0].remove();", svgDismiss);
////                System.out.println("✅ SVG ❌ under #dismiss-button removed");
////                adRemoved = true;
////            }
////        } catch (Exception e) {
////            System.out.println("ℹ️ SVG ❌ under dismiss-button not found");
////        }
////
////        if (!adRemoved) {
////            System.out.println("ℹ️ No ad elements matched for removal.");
////        }
////    }
////}
