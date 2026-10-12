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
import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Listener implements ITestListener, IConfigurationListener {

    private static ExtentReports extentReports;
    // ThreadLocal عشان كل thread يشتغل على الـ ExtentTest بتاعه (آمن لو اتفعل التشغيل المتوازي)
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /**
     * ميثود مستقلة لإنشاء وإعداد التقرير بخصائص بروفيشنال
     */
    public static synchronized ExtentReports createReport() {
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
            extentReports.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
            extentReports.setSystemInfo("Headless", System.getProperty("headless", "false"));
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
        ExtentTest test = extentReports.createTest(result.getName());
        extentTest.set(test);
        test.log(Status.INFO, "Test Case '" + result.getName() + "' execution started.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Case '" + result.getName() + "' PASSED.");
        extentTest.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.log(Status.FAIL, "Test Case '" + result.getName() + "' FAILED.");
        test.log(Status.FAIL, "Failure Reason: " + describe(result.getThrowable()));

        // التقاط الصورة وربطها بالتقرير
        String screenshotPath = captureScreenshot(result.getName());
        if (screenshotPath != null) {
            test.addScreenCaptureFromPath(screenshotPath);
        }
        extentTest.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // لو الـ test اتعمله skip قبل ما يبدأ (مثلاً بسبب فشل الـ setUp) مفيش ExtentTest له لسه
        ExtentTest test = extentTest.get();
        if (test == null) {
            test = extentReports.createTest(result.getName());
        }
        test.log(Status.SKIP, "Test Case '" + result.getName() + "' SKIPPED."
                + (result.getThrowable() != null ? " Reason: " + describe(result.getThrowable()) : ""));
        extentTest.remove();
    }

    // فشل الـ @BeforeMethod / @AfterMethod كان بيتعمل منه skip للـ tests من غير ما يتسجل السبب في التقرير
    @Override
    public void onConfigurationFailure(ITestResult result) {
        ExtentTest test = extentReports.createTest("Configuration failure: " + result.getMethod().getMethodName());
        test.log(Status.FAIL, "Configuration method '" + result.getMethod().getMethodName()
                + "' in " + result.getTestClass().getName() + " FAILED.");
        test.log(Status.FAIL, "Failure Reason: " + describe(result.getThrowable()));

        String screenshotPath = captureScreenshot("config_" + result.getMethod().getMethodName());
        if (screenshotPath != null) {
            test.addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // كتابة البيانات بشكل نهائي وإغلاق التقرير
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    private static String describe(Throwable throwable) {
        return throwable == null ? "no exception recorded" : String.valueOf(throwable.getMessage());
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
