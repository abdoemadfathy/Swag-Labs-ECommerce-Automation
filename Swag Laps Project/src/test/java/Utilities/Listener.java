package Utilities;

import BaseClass.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Listener implements ITestListener {

    // المتغيرات الاستاتيكية بالطريقة التي طلبتها
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;

    /**
     * ميثود مستقلة لإنشاء وإعداد التقرير بخصائص بروفيشنال
     */
    public static ExtentReports createReport() {
        if (extentReports == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/Reports/ExecutionReport_" + timestamp + ".html";

            // إعداد الـ Reporter (النسخة الحديثة المستقرة من HtmlReporter)
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // تخصيص مظهر التقرير الافتراضي
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setReportName("Automation Test Execution Report");
            sparkReporter.config().setDocumentTitle("QA Test Results");

            // ربط الـ Reporter بكائن الـ ExtentReports الأساسي
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);

            // إضافة معلومات النظام والبيئة
            extentReports.setSystemInfo("Environment", "QA");
            extentReports.setSystemInfo("QA Engineer", "Abdulrahman Emad");
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extentReports;
    }

    @Override
    public void onStart(ITestContext context) {
        // استدعاء الميثود لتجهيز التقرير فور بدء الـ Suite
        createReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // إنشاء خطوة اختبار جديدة لكل تست كيس تبدأ
        extentTest = extentReports.createTest(result.getName());
        extentTest.log(Status.INFO, "Test Case '" + result.getName() + "' execution started.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.log(Status.PASS, "Test Case '" + result.getName() + "' PASSED.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.log(Status.FAIL, "Test Case '" + result.getName() + "' FAILED.");
        extentTest.log(Status.FAIL, "Failure Reason: " + result.getThrowable().getMessage());

        // التقاط الصورة وربطها بالتقرير
        String screenshotPath = captureScreenshot(result.getName());
        if (screenshotPath != null) {
            extentTest.addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.log(Status.SKIP, "Test Case '" + result.getName() + "' SKIPPED.");
    }

    @Override
    public void onFinish(ITestContext context) {
        // كتابة البيانات بشكل نهائي وإغلاق التقرير
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    // ميثود مساعدة لالتقاط السكرين شوت وحفظها
    private String captureScreenshot(String testName) {
        if (BaseClass.driver == null) {
            return null;
        }
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            String directoryPath = System.getProperty("user.dir") + "/Screenshots/";

            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File srcFile = ((TakesScreenshot) BaseClass.driver).getScreenshotAs(OutputType.FILE);
            File targetFile = new File(directoryPath + fileName);
            FileHandler.copy(srcFile, targetFile);

            return targetFile.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }
}