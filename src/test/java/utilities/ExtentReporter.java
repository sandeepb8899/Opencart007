package utilities;

// Import necessary libraries
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

/**
 * ExtentReporter is a TestNG listener class that generates an Extent Report for
 * test execution results.
 */
public class ExtentReporter implements ITestListener {
	private static ExtentReports extent;
	private static ExtentSparkReporter sparkReporter;
	private static ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();
	private static Map<String, ExtentTest> testSuiteMap = new ConcurrentHashMap<>();
	private static String reportName;

	/**
	 * Initializes and returns a singleton instance of ExtentReports.
	 */
	public synchronized static ExtentReports getExtentInstance() {
		if (extent == null) {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			reportName = "Extent-Report-" + timeStamp + ".html";
			String reportPath = System.getProperty("user.dir") + "\\reports\\" + reportName;

			sparkReporter = new ExtentSparkReporter(reportPath);
			sparkReporter.config().setDocumentTitle("Automated Test Report");
			sparkReporter.config().setReportName("Test Execution Summary");
			sparkReporter.config().setTheme(Theme.STANDARD);

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Host Name", "Localhost");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("User", System.getProperty("user.name"));
		}
		return extent;
	}

	/**
	 * Called when a test suite starts. Initializes a test suite node in the report.
	 */
	@Override
	public void onStart(ITestContext context) {
		String suiteName = context.getSuite().getName();
		ExtentTest suiteNode = getExtentInstance().createTest(suiteName);
		testSuiteMap.put(context.getName(), suiteNode);

		suiteNode.info("Suite started: " + suiteName);
		suiteNode.assignCategory("Suite: " + suiteName);

		getExtentInstance().setSystemInfo("OS", context.getCurrentXmlTest().getParameter("os"));
		getExtentInstance().setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));
	}

	/**
	 * Called when a test method starts execution. Creates a node under the suite.
	 */
	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest suiteNode = testSuiteMap.get(result.getTestContext().getName());
		ExtentTest methodNode = suiteNode.createNode(result.getMethod().getMethodName());
		methodNode.assignCategory(result.getTestContext().getName());
		testNode.set(methodNode);
	}

	/**
	 * Called when a test method passes. Logs a success message.
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTest test = testNode.get();
		test.log(Status.PASS, result.getMethod().getMethodName() + " passed.");
		test.info("Execution Time: " + getExecutionTime(result) + " ms");
	}

	/**
	 * Called when a test method fails. Logs failure details and captures a
	 * screenshot.
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTest test = testNode.get();
		test.log(Status.FAIL, result.getMethod().getMethodName() + " failed.");
		test.log(Status.FAIL, result.getThrowable());

		try {
			String screenshotPath = captureScreenshot(result.getName());
			test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
		} catch (IOException e) {
			e.printStackTrace();
		}
		test.info("Execution Time: " + getExecutionTime(result) + " ms");
	}

	/**
	 * Called when a test method is skipped. Logs the skip reason.
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTest test = testNode.get();
		test.log(Status.SKIP, result.getMethod().getMethodName() + " skipped.");
		test.info("Reason: " + result.getThrowable());
		test.info("Execution Time: " + getExecutionTime(result) + " ms");
	}

	/**
	 * Called when a test suite finishes execution. Logs suite summary.
	 */
	@Override
	public void onFinish(ITestContext context) {
		ExtentTest suiteNode = testSuiteMap.get(context.getName());
		suiteNode.info("Suite finished: " + context.getSuite().getName());
		suiteNode.info("Passed: " + context.getPassedTests().size());
		suiteNode.info("Failed: " + context.getFailedTests().size());
		suiteNode.info("Skipped: " + context.getSkippedTests().size());

		getExtentInstance().flush();
		// openReport(); // Uncomment to automatically open the report after execution
	}

	/**
	 * Opens the generated Extent report in the default browser.
	 */
	private void openReport() {
		try {
			File reportFile = new File(System.getProperty("user.dir") + "\\reports\\" + reportName);
			Desktop.getDesktop().browse(reportFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Captures a screenshot and returns its file path.
	 */
	private String captureScreenshot(String testName) throws IOException {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String screenshotPath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timestamp
				+ ".png";
		BaseClass base = new BaseClass();
		WebDriver driver = base.getDriver();
		File sourceFile = ((TakesScreenshot) base.getDriver()).getScreenshotAs(OutputType.FILE);
		File targetFile = new File(screenshotPath);
		sourceFile.renameTo(targetFile);
		return screenshotPath;
	}

	/**
	 * Calculates and returns the execution time of a test method.
	 */
	private long getExecutionTime(ITestResult result) {
		return result.getEndMillis() - result.getStartMillis();
	}

	public static ExtentTest getTest() {
		return testNode.get();
	}

}
