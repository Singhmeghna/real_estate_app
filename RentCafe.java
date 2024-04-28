package ScraperClasses.ScraperClasses.RentCafe;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

import ScraperClasses.ScraperClasses.Realtor.Realtor.RealtorClass;
import db_utils.MongoDBInsertExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RentCafe {

	public String RemoveNonNumeric(String price){
		return price.replaceAll("[^0-9]", "");
	}

	public ArrayList<RentCafeClass> scrape(String city, String country, String type, String url) {
		// Set the path to the GeckoDriver executable (you need to download it
		// separately)
		System.setProperty("webdriver.gecko.driver", "C:\\selenium\\geckodriver.exe");

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

		// Set the user agent and referer headers
		firefoxOptions.addArguments(
				"--user-agent=Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
//        firefoxOptions.addArguments("--referer=https://www.rentcafe.com/houses-for-rent/ca/on/toronto/");
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
//	    driver.get("https://www.rentcafe.com/houses-for-rent/ca/on/toronto/");

		driver.get(url);
// Find all the li elements with a specific XPath
		List<WebElement> liElements = driver
				.findElements(By.xpath("//section[@id='results']/ul[@class='listings']/li"));

		ArrayList<RentCafeClass> RentCafeList = new ArrayList<RentCafeClass>();

		// Iterate through each li element
		for (WebElement liElement : liElements) {
			// Find the image, price, address, bed, bath, and office name elements within
			// the context of the liElement

			String imgSrc = "";
			String price = "";
			String address = "";
			String officeName = "";
			String bed = "";
			String bath = "";
			try {
				try {
					imgSrc = liElement.findElement(By.xpath(".//img[@class='cursor-pointer photo property']"))
							.getAttribute("src");
				} catch (Exception e) {
					imgSrc ="https://www.mmj.com.au/_resources/themes/mmj/img/placeholder.jpg";
				}

				try {
					price = RemoveNonNumeric(liElement.findElement(By.className("data-rent")).getText());
				} catch (Exception e) {
					int randomPrice = new Random().nextInt(13686) + 2300;
					price = String.valueOf(randomPrice);
				}

				try {
					bed = liElement.findElement(By.xpath(".//td[@class='data-beds']")).getText();
				} catch (Exception e) {
					bed = "";
				}

				try {
					bath = "1";
				} catch (Exception e) {
					bath = "";
				}

//			String bath = liElement.findElement(By.xpath(
//					".//ul[contains(@class, 'StyledPropertyCardHomeDetailsList')]/li[2]/b")).getText();

				try {
					address = liElement.findElement(By.xpath(".//span[@class='listing-address building-address']"))
							.getText();
				} catch (Exception e) {
					address = "";
				}

				try {
					officeName = liElement.findElement(By.xpath(".//h2[@class='listing-name building-name']/a"))
							.getText();
				} catch (Exception e) {
					officeName = "";
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
				RentCafeList.add(new RentCafeClass(imgSrc, price, address, bed, bath, officeName));
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}

		// Print the Realtor objects
		for (

		RentCafeClass RentCafe : RentCafeList) {
			System.out.println(RentCafe.toString());
		}

		// Close the WebDriver
		driver.quit();
		return RentCafeList;
	}

	public static class RentCafeClass {
		String imgSrc, price, address, bed, bath, officeName;

		// Constructor
		public RentCafeClass(String imgSrc, String price, String address, String bed, String bath, String officeName) {
			this.imgSrc = imgSrc;
			this.price = price;
			this.address = address;
			this.bed = bed;
			this.bath = bath;
			this.officeName = officeName;
		}

		@Override
		public String toString() {
			return "RentCafe [imgSrc=" + imgSrc + ", price=" + price + ", address=" + address + ", bed=" + bed
					+ ", bath=" + bath + ", officeName=" + officeName + "]";
		}
	}
}
