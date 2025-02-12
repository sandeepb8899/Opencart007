package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.logging.log4j.LogManager; // Log4j for logging
import org.apache.logging.log4j.Logger; // Log4j for logging
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	protected static WebDriver driver; // WebDriver instance
	public Logger logger; // Log4j Logger instance
	public Properties p; // Properties object to handle configuration

	@BeforeClass
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws IOException {

		// Loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);

		logger = LogManager.getLogger(this.getClass()); // Initializing Log4j logger

		// Initializing WebDriver based on browser parameter
		switch (br.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver(); // Launch Chrome browser
			break;
		case "edge":
			driver = new EdgeDriver(); // Launch Edge browser
			break;
		case "firefox":
			driver = new FirefoxDriver(); // Launch Firefox browser
			break;
		default:
			System.out.println("Invalid browser name..");
			return;
		}

		// Browser settings
		driver.manage().deleteAllCookies(); // Clear cookies before starting session
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Set implicit wait

		// Launching application URL from properties file
		driver.manage().window().maximize(); // Maximize window for better visibility
		driver.get(p.getProperty("appURL1")); // Open application URL
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit(); // Closes all browser windows and ends the session
		}
	}

	// Method to generate a random 5-letter string (Alphabets only)
	public String RndmStrng() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // Character set for random string
		Random rand = new Random();
		StringBuilder sb = new StringBuilder(5);
		for (int i = 0; i < 5; i++) {
			sb.append(chars.charAt(rand.nextInt(chars.length()))); // Append random character

			// Alternate Solution 1: Using Apache Commons Text
			// RandomStringGenerator generator = new RandomStringGenerator.Builder()
			// .withinRange('A', 'Z')
			// .build();
			// return generator.generate(5);

			// Alternate Solution 2: Using Apache Commons Lang (Deprecated)
			// return RandomStringUtils.randomAlphabetic(5);
		}
		return sb.toString(); // Return generated string
	}

	// Method to generate a random 10-digit number
	public String RndmNum() {
		String digits = "0123456789"; // Numeric characters for random number
		Random rand = new Random();
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < 10; i++) {
			sb.append(digits.charAt(rand.nextInt(digits.length()))); // Append random digit

			// Alternate Solution 1: Using Math.random()
			// return String.valueOf((long) (Math.random() * 1_000_000_0000L));

			// Alternate Solution 2: Using SecureRandom for better security
			// SecureRandom secureRand = new SecureRandom();
			// StringBuilder sb = new StringBuilder(10);
			// for (int i = 0; i < 10; i++) {
			// sb.append(secureRand.nextInt(10));
			// }
			// return sb.toString();

			// Alternate Solution 3: Using Apache Commons Lang (Deprecated)
			// return RandomStringUtils.randomNumeric(10);
		}
		return sb.toString(); // Return generated number
	}

	// Method to generate a random alphanumeric string in format "XXXXX@1234567890"
	public String RndmAlpaNum() {
		return RndmStrng() + "@" + RndmNum(); // Concatenating random string and number with '@' separator
	}
	
	public WebDriver getDriver() {
	    return driver;
	}

}
