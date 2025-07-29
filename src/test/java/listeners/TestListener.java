package listeners;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.*;
import reports.ExtentManager;
import utils.ScreenshotUtil;

public class TestListener extends BaseTest implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        extent = ExtentManager.getInstance();  // ✅ Using shared manager
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
    	
    	System.out.println("🛑 Test failed: " + result.getName());
    	System.out.println("📸 Attempting to capture screenshot...");

        test.get().fail("❌ Test Failed: " + result.getThrowable());

        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
        if (screenshotPath != null) {
            try {
                test.get().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                System.out.println("❌ Failed to attach screenshot to report: " + e.getMessage());
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}


//package listeners;
//
//import base.BaseTest;
//import com.aventstack.extentreports.*;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import org.testng.*;
//import utils.ScreenshotUtil;
//
//public class TestListener extends BaseTest implements ITestListener {
//
//    private static ExtentReports extent;
//    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
//
//    @Override
//    public void onStart(ITestContext context) {
//        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/ExtentReport.html");
//        extent = new ExtentReports();
//        extent.attachReporter(reporter);
//    }
//
//    @Override
//    public void onTestStart(ITestResult result) {
//        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
//        test.set(extentTest);
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        test.get().pass("✅ Test Passed");
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        test.get().fail("❌ Test Failed: " + result.getThrowable());
//
//        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
//        if (screenshotPath != null) {
//            try {
//                test.get().addScreenCaptureFromPath(screenshotPath);
//            } catch (Exception e) {
//                System.out.println("❌ Failed to attach screenshot to report: " + e.getMessage());
//            }
//        }
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
//        extent.flush();
//    }
//}
