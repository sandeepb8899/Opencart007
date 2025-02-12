package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[@title='My Account']")
	WebElement drpdownMyaccount;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement lnkRegister;
	
	@FindBy(xpath = "//a[normalize-space()='Login']")
	WebElement lnkLogin;

	public void clickMyAccount() {

		drpdownMyaccount.click();
	}

	public void clickRegister() {

		lnkRegister.click();
	}
	
	public void clickLogin() {

		lnkLogin.click();
	}
}

