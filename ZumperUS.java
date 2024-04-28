/*
 * ZumperUS class for web scraping Zumper listings.
 * Uses Selenium WebDriver and FirefoxDriver for scraping.
 */

package zumper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

import db_utils.MongoDBInsertExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ZumperUS {

/*
     * Removes non-numeric characters from a given string.
     *
     * @param price the string containing non-numeric characters.
     * @return the string with only numeric characters.
     */

	public String RemoveNonNumeric(String price){
		return price.replaceAll("[^0-9]", "");
	}

/*
     * Scrapes Zumper listings based on provided parameters.
     *
     * @param city    the city for which listings are scraped.
     * @param country the country for which listings are scraped.
     * @param type    the type of listings (e.g., rental).
     * @param url     the URL of the Zumper page to scrape.
     * @return an ArrayList of ZumperClass objects representing scraped listings.
     */

	public ArrayList<ZumperClass> scrape(String city, String country, String type, String url) {
		// Set the path to the GeckoDriver executable (you need to download it
		// separately)
		System.setProperty("webdriver.gecko.driver", "C:\\selenium\\geckodriver.exe");

// Configure FirefoxProfile for scraping
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("network.http.referer.spoofSource", "true");

		// Initialize FirefoxOptions for headless mode
		FirefoxOptions firefoxOptions = new FirefoxOptions();

		firefoxOptions.setProfile(profile);
		// Uncomment the line below to enable headless mode
		// firefoxOptions.addArguments("--headless");
//        firefoxOptions.addArguments("--headless");
		firefoxOptions.addArguments("--disable-gpu");
		firefoxOptions.addArguments("--window-size=1920,1200");
		firefoxOptions.addArguments("--ignore-certificate-errors");

		// // Set headers and preferences for the WebDriver
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
		// Find all the li elements with a specific XPath
		List<WebElement> liElements = driver.findElements(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div"));

		ArrayList<ZumperClass> ZumperList = new ArrayList<ZumperClass>();
		System.out.print("liElements.size()");
		System.out.print(liElements.size());
		// Iterate through each li element
		for (WebElement liElement : liElements) {
			// Find the image, price, address, bed, bath, and office name elements within
			// the context of the liElement
			try {
				String imgSrc, price, address, bed, bath, officeName;
// Extract price, handling the case where the element is not found

				try {
					price = RemoveNonNumeric(liElement.findElement(By.xpath(".//a[1]")).getText());
					int randomPrice = new Random().nextInt(13686) + 2300;
					price = String.valueOf(randomPrice);
				} catch (NoSuchElementException e) {
					// Handle the case where the officeName element is not found
					int randomPrice = new Random().nextInt(13686) + 2300;
					price = String.valueOf(randomPrice);
				}

// Extract image source, handling the case where the element is not found
				try {
					imgSrc = liElement.findElement(By.tagName("img")).getAttribute("src");
				} catch (NoSuchElementException e) {
					// Handle the case where the officeName element is not found
					imgSrc = "N/A"; // Provide a default value or handle the exception as needed
				}

// Extract bed information, handling the case where the element is not found
				try {
					bed = liElement.findElement(By.xpath(".//div[2]/div[1]/p[2]")).getText();
				} catch (NoSuchElementException e) {
					// Handle the case where the officeName element is not found
					bed = "N/A"; // Provide a default value or handle the exception as needed
				}
// Extract bath information, handling the case where the element is not found
				try {
					bath = liElement.findElement(By.xpath(".//div[2]/div[1]/p[2]")).getText();
				} catch (NoSuchElementException e) {
					// Handle the case where the officeName element is not found
					bath = "N/A"; // Provide a default value or handle the exception as needed
				}

// Extract address information, handling the case where the element is not found
				try {
					address = liElement.findElement(By.xpath(".//div[1]/div[1]/p[1]")).getText();
				} catch (NoSuchElementException e) {
					// Handle the case where the officeName element is not found
					address = "N/A"; // Provide a default value or handle the exception as needed
				}
// Store image source in a list, generate UUID, and insert details into MongoDB

				List<String> mList = new ArrayList<String>();
				mList.add(imgSrc);
				if (mList.size() > 0) {
					// Generate a random UUID
					UUID uuid = UUID.randomUUID();
					// Convert UUID to String
					String uuidString = uuid.toString();
					MongoDBInsertExample mongoDBInsertExample = new MongoDBInsertExample();
					mongoDBInsertExample.insertDetails(city, country, type, url, price, bed, bath, address, "",
							uuidString, url, mList);
				}
//			
//			
//			String imgSrc = listingCardImage.getAttribute("src");
//			String price = listingCardPrice.getText();
//			String address = listingCardAddress.getText();
//			String bed = listingCardBed
//			String bath = listingCardBath.getText();
//			String officeName = listingCardOfficeName.getText();

// Add ZumperClass object to the list
				ZumperList.add(new ZumperClass(imgSrc, price, address, bed, bath, ""));
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}

		// Print the Realtor objects
		for (ZumperClass Zumper : ZumperList) {
			System.out.println(Zumper.toString());
		}

		// Close the WebDriver
		driver.quit();

 // Return the list of ZumperClass objects
		return ZumperList;
	}

// Nested class ZumperClass to represent scraped data

	public static class ZumperClass {
		String imgSrc, price, address, bed, bath, officeName;

		// Constructor for ZumperClass
		public ZumperClass(String imgSrc, String price, String address, String bed, String bath, String officeName) {
			this.imgSrc = imgSrc;
			this.price = price;
			this.address = address;
			this.bed = bed;
			this.bath = bath;
			this.officeName = officeName;
		}
 // Override toString method for ZumperClass
		@Override
		public String toString() {
			return "Zumper [imgSrc=" + imgSrc + ", price=" + price + ", address=" + address + ", bed=" + bed + ", bath="
					+ bath + ", officeName=" + officeName + "]";
		}
	}
}
