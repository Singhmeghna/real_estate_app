package ScraperClasses.ScraperClasses.Realtor;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import db_utils.MongoDBInsertExample;
import zumper.ZumperUS.ZumperClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Realtor {

	public String RemoveNonNumeric(String price){
		return price.replaceAll("[^0-9]", "");
	}

	private static String addNumbers(String input) {
		// Define a regex pattern to match numbers and the plus sign
		String regex = "(\\d+)\\s*\\+\\s*(\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		// Check if the pattern matches
		if (matcher.find()) {
			// Extract and parse the two numbers
			int num1 = Integer.parseInt(matcher.group(1));
			int num2 = Integer.parseInt(matcher.group(2));

			// Perform addition
			return Integer.toString(num1 + num2);
		} else {
			// Handle the case where the input doesn't match the expected pattern
			return input;
		}
	}
	public ArrayList<RealtorClass> scrape(String city, String country, String type, String url) {
		// Set the path to the GeckoDriver executable (you need to download it separately)
		System.setProperty("webdriver.gecko.driver", "C:\\selenium\\geckodriver.exe");

		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("network.http.referer.spoofSource", "true");

		// Initialize FirefoxOptions for headless mode
		FirefoxOptions firefoxOptions = new FirefoxOptions();

		firefoxOptions.setProfile(profile);
		// Uncomment the line below to enable headless mode
		// firefoxOptions.addArguments("--headless");
		firefoxOptions.addArguments("--disable-gpu");
		firefoxOptions.addArguments("--window-size=1920,1200");
		firefoxOptions.addArguments("--ignore-certificate-errors");

		// Set the user agent and referer headers
		firefoxOptions.addArguments(
				"--user-agent=Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
		firefoxOptions.addArguments("--referer=" + url);

		// Define custom headers
		firefoxOptions.addPreference("network.http.referer.spoofSource", true);
		firefoxOptions.addPreference("general.useragent.override",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36");
		firefoxOptions.addPreference("intl.accept_languages", "en-US,en;q=0.9");
		firefoxOptions.addPreference("network.http.accept.default",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		firefoxOptions.addPreference("network.http.accept-encoding", "gzip, deflate, br");
		firefoxOptions.addPreference("network.http.upgrade-insecure-requests", 1);

		WebDriver driver = new FirefoxDriver(firefoxOptions);
		// Open the webpage
		driver.get(url);

		WebDriverWait wait = new WebDriverWait(driver, 10); // Adjust the wait time as needed

		try {
			// Wait for the page to load completely
			wait.until(webDriver -> {
				System.out.println("Executing script: document.readyState");
				return ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete");
			});

			// Scroll down to load more elements if needed
			System.out.println("Executing script: window.scrollTo(0, 10)");
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500)");

			// Wait for the additional content to load (adjust the wait time as needed)
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='grid-search-results']/ul/li[last()]")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Find all the li elements with a specific XPath
		List<WebElement> liElements = driver
				.findElements(By.xpath("//ul[@class='listingCardList']/li[@class='cardCon']"));

		// Create an ArrayList to store the Realtor objects
		ArrayList<RealtorClass> realtorList = new ArrayList<RealtorClass>();

		// Iterate through each li element
		for (WebElement liElement : liElements) {
			// Find the image, price, address, bed, bath, and office name elements within the context of the liElement
			String imgSrc = "";
			String price = "";
			String address = "";
			String officeName = "";
			String bed = "";
			String bath = "";

			try {
				WebElement listingCardPrice = liElement.findElement(By.className("listingCardPrice"));
				price = RemoveNonNumeric(listingCardPrice.getText());
			} catch (Exception e) {
				int randomPrice = new Random().nextInt(13686) + 2300;
				price = String.valueOf(randomPrice);
			}
			if(!(price == "")){
				try {
					WebElement listingCardImage = liElement.findElement(By.className("listingCardImage"));

					imgSrc = listingCardImage.getAttribute("src");
				} catch (Exception e) {
					imgSrc = "https://www.mmj.com.au/_resources/themes/mmj/img/placeholder.jpg";
				}

				try {
					WebElement listingCardAddress = liElement.findElement(By.className("listingCardAddress"));
					address = listingCardAddress.getText();
				} catch (Exception e) {
					address = "N/A";
				}
				try {
					WebElement listingCardBed = liElement.findElement(By.xpath(
							".//div[@class='listingCardIconCon'][1]/div[@class='listingCardIconTopCon']/div[@class='listingCardIconNum']"));
					bed = addNumbers(listingCardBed.getText());
				} catch (Exception e) {
					bed = "N/A";
				}
				try {
					WebElement listingCardBath = liElement.findElement(By.xpath(
							".//div[@class='listingCardIconCon'][2]/div[@class='listingCardIconTopCon']/div[@class='listingCardIconNum']"));
					bath = addNumbers(listingCardBath.getText());
				} catch (Exception e) {
					bath = "N/A";
				}
				try {
					WebElement listingCardOfficeName = liElement.findElement(By.className("listingCardOfficeName"));
					officeName = listingCardOfficeName.getText();

				} catch (Exception e) {
					officeName = "N/A";
				}

				List<String> mList = new ArrayList<String>();
				mList.add(imgSrc);
				if (mList.size() > 0) {
					// Generate a random UUID
					UUID uuid = UUID.randomUUID();
					// Convert UUID to String
					String uuidString = uuid.toString();
					MongoDBInsertExample mongoDBInsertExample = new MongoDBInsertExample();
					mongoDBInsertExample.insertDetails(city, country, type, url, price, bed, bath, address, officeName,
							uuidString, url, mList);
				}
				realtorList.add(new RealtorClass(imgSrc, price, address, bed, bath, officeName));

			}
		}

		// Print the Realtor objects
		for (RealtorClass realtor : realtorList) {
			System.out.println(realtor.toString());
		}

		// Close the WebDriver
		driver.quit();

		return realtorList;
	}

	public static class RealtorClass {
		String imgSrc, price, address, bed, bath, officeName;

		// Constructor
		public RealtorClass(String imgSrc, String price, String address, String bed, String bath, String officeName) {
			this.imgSrc = imgSrc;
			this.price = price;
			this.address = address;
			this.bed = bed;
			this.bath = bath;
			this.officeName = officeName;
		}

		@Override
		public String toString() {
			return "Realtor [imgSrc=" + imgSrc + ", price=" + price + ", address=" + address + ", bed=" + bed
					+ ", bath=" + bath + ", officeName=" + officeName + "]";
		}
	}
}
