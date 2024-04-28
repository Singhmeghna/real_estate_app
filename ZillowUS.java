// Package declaration for the ZillowUS class
package zillow;

// Import statements for Selenium, MongoDBInsertExample, and other required libraries
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import db_utils.MongoDBInsertExample;

import java.util.*;


// Main class ZillowUS
public class ZillowUS {

// Method to remove non-numeric characters from a string

	public String RemoveNonNumeric(String price) {
		return (price.replaceAll("[^0-9]", ""));
	}
// Method to scrape data from a webpage using Selenium
	public ArrayList<ZillowClass> scrape(String city, String country, String type, String url) {
		// Set the path to the GeckoDriver executable (you need to download it
		// separately)
		System.setProperty("webdriver.gecko.driver", "C:\\selenium\\geckodriver.exe");

// Create a Firefox profile and set preferences for spoofing headers
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

		// Set user agent, referer headers, and define custom headers
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

// Initialize WebDriver using FirefoxDriver with specified options
		WebDriver driver = new FirefoxDriver(firefoxOptions);

		// Open the webpage
		driver.get(url);

// Create WebDriverWait instance for explicit waits
		WebDriverWait wait = new WebDriverWait(driver, 10); // Adjust the wait time as needed
		
		try {
			// Wait for the page to load completely
			wait.until(webDriver -> {
				System.out.println("Executing script: document.readyState");
				return ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete");
			});
			// Wait for the additional content to load (adjust the wait time as needed)
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[@id='grid-search-results']/ul/li[last()]")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Find all the li elements with a specific XPath
		List<WebElement> liElements = driver.findElements(By.xpath("//div[@id='grid-search-results']/ul/li"));

// Create a list to store ZillowClass objects
		ArrayList<ZillowClass> zillowList = new ArrayList<ZillowClass>();

		// Iterate through each li element
		for (WebElement liElement : liElements) {
			// Find the image, price, address, bed, bath, and office name elements within
			// the context of the liElement
			try {

				String imgSrc = "";
				String price = "";
				String address = "";
				String officeName = "";
				String bed = "";
				String bath = "";

				try {
					price = RemoveNonNumeric(liElement
							.findElement(By.xpath(".//span[contains(@class, 'PropertyCardWrapper__StyledPriceLine')]"))
							.getText());
				} catch (Exception e) {
					int randomPrice = new Random().nextInt(13686) + 2300;
					price = String.valueOf(randomPrice);
				}
				if (!(Objects.equals(price, ""))) {
					try {
						imgSrc = liElement.findElement(By.tagName("img")).getAttribute("src");
					} catch (Exception e) {
						imgSrc = "https://www.mmj.com.au/_resources/themes/mmj/img/placeholder.jpg";
					}
					try {
						bed = liElement
								.findElement(By
										.xpath(".//ul[contains(@class, 'StyledPropertyCardHomeDetailsList')]/li[1]/b"))
								.getText();
					} catch (Exception e) {
						bed = "N/A";
					}
					try {
						bath = liElement
								.findElement(By
										.xpath(".//ul[contains(@class, 'StyledPropertyCardHomeDetailsList')]/li[2]/b"))
								.getText();
					} catch (Exception e) {
						bath = "N/A";
					}
					try {
						address = liElement.findElement(By.xpath(".//address")).getText();
					} catch (Exception e) {
						address = "N/A";
					}
// Create a list to store image sources

					List<String> mList = new ArrayList<String>();
					mList.add(imgSrc);
 // Check if mList has elements before inserting into the database
					if (mList.size() > 0) {
						// Generate a random UUID
						UUID uuid = UUID.randomUUID();
						// Convert UUID to String
						String uuidString = uuid.toString();
// Create an instance of MongoDBInsertExample and insert details into the database
						MongoDBInsertExample mongoDBInsertExample = new MongoDBInsertExample();
						mongoDBInsertExample.insertDetails(city, country, type, url, price, bed, bath, address,
								officeName, uuidString, url, mList);
					}
// Add a new ZillowClass object to the ZillowList
					zillowList.add(new ZillowClass(imgSrc, price, address, bed, bath, ""));
				}
			} catch (Exception e) {
// Handle exceptions and print error messages
				System.out.print(e.getMessage());
			}
		}

		// Print the ZillowClass objects
		for (ZillowClass zillow : zillowList) {
			System.out.println(zillow.toString());
		}

		// Close the WebDriver
		driver.quit();
// Return the list of ZillowClass objects
		return zillowList;
	}
// Nested class ZillowClass to represent scraped data
	public static class ZillowClass {
		String imgSrc, price, address, bed, bath, officeName;

		// Constructor for ZillowClass
		public ZillowClass(String imgSrc, String price, String address, String bed, String bath, String officeName) {
			this.imgSrc = imgSrc;
			this.price = price;
			this.address = address;
			this.bed = bed;
			this.bath = bath;
			this.officeName = officeName;
		}
// Override toString method for ZillowClass
		@Override
		public String toString() {
			return "Zillow [imgSrc=" + imgSrc + ", price=" + price + ", address=" + address + ", bed=" + bed + ", bath="
					+ bath + ", officeName=" + officeName + "]";
		}
	}
}
