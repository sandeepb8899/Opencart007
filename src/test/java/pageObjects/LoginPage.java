package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    // Constructor to initialize WebDriver
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Locating email input field
    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmailAddress;

    // Locating password input field
    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;

    // Locating login button
    @FindBy(xpath = "//input[@value='Login']")
    WebElement btnLogin;

    // Method to enter email address
    public void setEmail(String email) {
        txtEmailAddress.clear(); // Clears the existing text before entering new email
        txtEmailAddress.sendKeys(email);
    }

    // Method to enter password
    public void setPassword(String password) {
        txtPassword.clear(); // Clears the existing text before entering new password
        txtPassword.sendKeys(password);
    }

    // Method to click login button
    public void clickLogin() {
        btnLogin.click();
    }
}