package testCases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.ExtentReporter;

public class TC_002_LoginTest extends BaseClass {

	@Test
	public void verify_account_Login() {
		// Log the start of the test case

		ExtentTest test = ExtentReporter.getTest(); // Get test instance from ExtentReporter
		test.info("***** Test Started: TC_002_LoginTest *****");
		logger.info("***** Starting TC_002_LoginTest *****");
		test.info("Test Case Started: TC_002_LoginTest");

		try {
			// Create an instance of the HomePage object
			HomePage hp = new HomePage(driver);

			// Click on the "My Account" link
			hp.clickMyAccount();
			logger.info("***** Clicked on My Account Link *****");
			test.info("Clicked on My Account Link");

			// Click on the "Login" link
			hp.clickLogin();
			logger.info("***** Clicked on Login Link *****");
			test.info("Clicked on Login Link");

			// Create an instance of the LoginPage object
			LoginPage lp = new LoginPage(driver);

			lp.setEmail(p.getProperty("email"));
			logger.info(p.getProperty("email"));
			test.info("Entered Email: " + p.getProperty("email"));

			lp.setPassword(p.getProperty("password"));
			logger.info(p.getProperty("password"));
			test.info("Entered Password");

			lp.clickLogin();
			logger.info("***** Clicked on Login Button *****");
			test.info("Clicked on Login Button");

			// Log successful execution
			logger.info("***** Test Case Execution Completed *****");
			test.pass("Test Case Passed Successfully");
		} catch (Exception e) {
			test.fail("Test Case Failed: " + e.getMessage());
		}
	}

}
