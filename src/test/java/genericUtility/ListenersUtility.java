package genericUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListenersUtility implements ITestListener 
{
	private static ExtentReports report;
	private ExtentTest test;

	public  void onStart(ITestContext context) {
		System.out.println("====== Suite/Test Started ======");
		if (report == null) {  
			// create the report only once
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			ExtentSparkReporter spark = new ExtentSparkReporter("./reports/API_Automation_" + timeStamp + ".html");
			spark.config().setDocumentTitle("API Automation Testing");
			spark.config().setReportName("REST Assured Test Report");
			spark.config().setTheme(Theme.DARK);
			report = new ExtentReports();
			report.attachReporter(spark);
			report.setSystemInfo("Application", "Shopper API");
			report.setSystemInfo("User Name", System.getProperty("user.name"));
			report.setSystemInfo("Operating System", System.getProperty("os.name"));
			report.setSystemInfo("Java Version", System.getProperty("java.version"));
		}

		List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
		if (!groups.isEmpty()) {
			report.setSystemInfo("Groups", groups.toString());
		}
	}
	public void onTestStart(ITestResult result) {
		test = report.createTest(result.getTestClass().getName() + " : " + result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.INFO, "====On Test Start==== :"+result.getName());
	}
	public void onTestSuccess(ITestResult result) {
		System.out.println("====== Test passed: " + result.getName());
		test = report.createTest(result.getTestClass().getName() + " : " + result.getName());
		test.log(Status.PASS, "Test case passed: " + result.getName());
	}
	public void onTestFailure(ITestResult result) {
		System.out.println("====== Test failed: " + result.getName());
		test = report.createTest(result.getTestClass().getName() + " : " + result.getName());
		test.log(Status.FAIL, "Test case failed: " + result.getName());
		test.fail(result.getThrowable());   // shows the assertion error / stack trace
	}
	public void onTestSkipped(ITestResult result) {
		System.out.println("====== Test skipped: " + result.getName());
		test = report.createTest(result.getTestClass().getName() + " : " + result.getName());
		test.log(Status.SKIP, "Test case skipped: " + result.getName());
		if (result.getThrowable() != null) {
			test.skip(result.getThrowable());
		}
	}
	public void onFinish(ITestContext context) {
		System.out.println("====== Suite/Test Finished ======");
		report.flush();
	}
}