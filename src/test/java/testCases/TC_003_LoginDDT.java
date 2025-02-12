package testCases;

import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC_003_LoginDDT extends BaseClass {
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void verify_loginDDT(String email, String pwd, String exp) {
		
		HomePage hp = new HomePage(driver);

		// Click on the "My Account" link
		hp.clickMyAccount();

		// Click on the "Login" link
		hp.clickLogin();

		LoginPage lp = new LoginPage(driver);

		lp.setEmail(email);

		lp.setPassword(pwd);

		lp.clickLogin();
	}
}