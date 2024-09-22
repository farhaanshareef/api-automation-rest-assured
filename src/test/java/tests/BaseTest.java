package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeTest(alwaysRun = true)
    public void setup() {
        extentReport();
    }

    public void extentReport() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-sss");
        Date date = new Date();
        String formattedDate = formatter.format(date);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("reports/extent-report-" + formattedDate + ".html");

        htmlReporter.config().setDocumentTitle("API Automation Report");
        htmlReporter.config().setReportName("API Automation Report");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static void logInfo(String message) {
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }
    public static void logfail(String message)
    {
        if (test != null) {
        test.log(Status.FAIL, message);
    }
    }
}
