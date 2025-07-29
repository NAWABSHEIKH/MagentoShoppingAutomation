package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

//    public static String captureScreenshot(WebDriver driver, String testName) {
//        try {
//            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            
//            // ✅ Create path relative to project root
//            String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
//            String path = screenshotDir + testName + "_" + timestamp + ".png";
//            
//            File dest = new File(path);
//            dest.getParentFile().mkdirs(); // Ensure folder exists
//            Files.copy(src.toPath(), dest.toPath());
//
//            System.out.println("📸 Screenshot saved at: " + dest.getAbsolutePath());
//            return dest.getAbsolutePath();
//        } catch (Exception e) {
//            System.out.println("❌ Failed to capture screenshot: " + e.getMessage());
//            return null;
//        }
//    }
    
    
    
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            if (driver == null) {
                System.out.println("❌ DRIVER IS NULL: Cannot take screenshot");
                return null;
            }
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
            String path = screenshotDir + testName + "_" + timestamp + ".png";
            File dest = new File(path);
            dest.getParentFile().mkdirs(); // Ensure folder exists
            Files.copy(src.toPath(), dest.toPath());
            System.out.println("📸 Screenshot saved at: " + dest.getAbsolutePath());
            return dest.getAbsolutePath();
        } catch (Exception e) {
            System.out.println("❌ Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

}
