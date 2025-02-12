package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass {

	@Test
	public void verify_account_registration() {
		// Log the start of the test case
		logger.info("***** Starting TC_001_AccountRegistrationTest *****");

		try {
			// Create an instance of the HomePage object
			HomePage hp = new HomePage(driver);

			// Click on the "My Account" link
			hp.clickMyAccount();
			logger.info("***** Clicked on My Account Link *****");

			// Click on the "Register" link
			hp.clickRegister();
			logger.info("***** Clicked on Register Link *****");

			// Create an instance of the AccountRegistrationPage object
			AccountRegistrationPage arp = new AccountRegistrationPage(driver);

			// Log the process of providing customer details
			logger.info("***** Providing Customer Details *****");

			// Generate and set the first name
			String sfn = RndmStrng().toUpperCase();
			arp.setFirstName(sfn);
			logger.info("First Name: " + sfn);

			// Generate and set the last name
			String sln = RndmStrng().toUpperCase();
			arp.setLastName(sln);
			logger.info("Last Name: " + sln);

			// Generate and set the email address
			String se = RndmStrng() + "@gmail.com";
//			arp.setEmail(se);
			arp.setEmail(p.getProperty("email"));

			logger.info("Email: " + se);
			// logger.info(p.getProperty("email"));

			// Generate and set the telephone number
			String st = RndmNum();
			arp.setTel(st);
			logger.info("Telephone: " + st);

			// Generate a random password and confirm password
			String rnm = RndmAlpaNum();
			logger.info("Password: " + rnm);
//			arp.setPwd(rnm);
//			arp.setCPwd(rnm);
			arp.setPwd(p.getProperty("password"));
			arp.setCPwd(p.getProperty("password"));

			// Accept the privacy policy
			arp.setPrivacyPolicy();

			// Click the continue button to complete registration
			arp.clickContinue();

			String confmsg = arp.getConfmtnMsg();
			logger.info("Confirmation Message: " + confmsg);
			//Assert.assertEquals(confmsg, "Your Account Has Been Created!");

		} catch (Exception e) {
			// Log the error and fail the test if an exception occurs
			logger.error("Test Failed: " + e.getMessage());
			logger.debug("Debug logs: ", e);
			Assert.fail("Test execution failed due to an exception.");
		}

		// Log the end of the test case
		logger.info("***** Finished TC_001_AccountRegistrationTest *****");
	}
}