package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	// Constructor to initialize WebDriver
	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

	// Locating WebElement using By locator
	WebElement txtFirstName = driver.findElement(By.id("input-firstname"));

	// Locating WebElements using @FindBy annotation
	@FindBy(id = "input-lastname")
	WebElement txtLastName;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;

	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txtTel;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPwd;

	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txtCnfmPwd;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement chkbxCnfrm;

	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfrmtn;

	// Method to enter first name
	public void setFirstName(String fname) {
		txtFirstName.sendKeys(fname);
	}

	// Method to enter last name
	public void setLastName(String lname) {
		txtLastName.sendKeys(lname);
	}

	// Method to enter email
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}

	// Method to enter telephone number
	public void setTel(String Tel) {
		txtTel.sendKeys(Tel);
	}

	// Method to enter password
	public void setPwd(String Pwd) {
		txtPwd.sendKeys(Pwd);
	}

	// Method to enter confirm password
	public void setCPwd(String CPwd) {
		txtCnfmPwd.sendKeys(CPwd);
	}

	// Method to check the privacy policy checkbox
	public void setPrivacyPolicy() {
		chkbxCnfrm.click();
	}

	// Method to click on the continue button
	public void clickContinue() {

		// Solution 1: Click the button directly
		btnContinue.click();

		// Other possible solutions for clicking the button
		// Solution 2: Submit the form if applicable
		// btnContinue.submit();

		// Solution 3: Use Actions class to move to the element and click
		// Actions act = new Actions(driver);
		// act.moveToElement(btnContinue).click().perform();

		// Solution 4: Use JavaScript Executor to click
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("arguments[0].click();", btnContinue);

		// Solution 5: Use the RETURN key to trigger click action
		// btnContinue.sendKeys(Keys.RETURN);

		// Solution 6: Wait for the button to be clickable and then click
		// WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// myWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
	}

	// Method to get confirmation message
	public String getConfmtnMsg() {
		try {
			return (msgConfrmtn.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
	}
}
