package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = "reports/Magento_Test_Report.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // 🎨 Dark theme and branding
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setReportName("🛒 Magento Automation Report");
            sparkReporter.config().setDocumentTitle("Magento E-commerce Test Report");
            sparkReporter.config().setTimeStampFormat("dd MMMM yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // 🧑‍💻 Add System Info (for filtering/grouping)
            extent.setSystemInfo("Project", "Magento E-commerce");
            extent.setSystemInfo("Tester", "Md Nawab");
            extent.setSystemInfo("Environment", "Staging");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }
}



//package reports;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
//public class ExtentManager {
//    private static ExtentReports extent;
//
//    public static ExtentReports getInstance() {
//        if (extent == null) {
//            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
//            extent = new ExtentReports();
//            extent.attachReporter(spark);
//        }
//        return extent;
//    }
//}
////