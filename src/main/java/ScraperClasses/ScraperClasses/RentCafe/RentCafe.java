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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RentCafe {
	public static void main(String[] args) {
		// Set the path to the GeckoDriver executable (you need to download it
		// separately)
		System.setProperty("webdriver.gecko.driver", "C:\\selenium\\geckodriver.exe");

		// Initialize FirefoxOptions for headless mode
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		// Uncomment the line below to enable headless mode
		// firefoxOptions.addArguments("--headless");
//        firefoxOptions.addArguments("--headless");
        firefoxOptions.addArguments("--disable-gpu");
        firefoxOptions.addArguments("--window-size=1920,1200");
        firefoxOptions.addArguments("--ignore-certificate-errors");

        // Set the user agent and referer headers
        firefoxOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        firefoxOptions.addArguments("--referer=https://www.rentcafe.com/houses-for-rent/ca/on/toronto/");


		WebDriver driver = new FirefoxDriver(firefoxOptions);

		// Open the webpage
	    driver.get("https://www.rentcafe.com/houses-for-rent/ca/on/toronto/");
	    // Find all the li elements with a specific XPath
	    List<WebElement> liElements = driver.findElements(By.xpath("//section[@id='results']/ul[@class='listings']/li"));

		ArrayList<RentCafeClass> RentCafeList = new ArrayList<RentCafeClass>();

		// Iterate through each li element
		for (WebElement liElement : liElements) {
			// Find the image, price, address, bed, bath, and office name elements within
			// the context of the liElement
			try {

			String imgSrc = liElement.findElement(By.xpath(
					".//img[@class='cursor-pointer photo property']")).getAttribute("src");

	        
			String price = liElement.findElement(By.className(
					"data-rent")).getText();

			String bed = liElement.findElement(By.xpath(
					".//td[@class='data-beds']")).getText();

			String bath = "";
//			String bath = liElement.findElement(By.xpath(
//					".//ul[contains(@class, 'StyledPropertyCardHomeDetailsList')]/li[2]/b")).getText();

			String address = liElement.findElement(By.xpath(
					".//span[@class='listing-address building-address']")).getText();

			String officeName = liElement.findElement(By.xpath(
					".//h2[@class='listing-name building-name']/a")).getText();
//			String imgSrc = listingCardImage.getAttribute("src");
//			String price = listingCardPrice.getText();
//			String address = listingCardAddress.getText();
//			String bed = listingCardBed
//			String bath = listingCardBath.getText();
//			String officeName = listingCardOfficeName.getText();

			RentCafeList.add(new RentCafeClass(imgSrc, price, address, bed, bath, officeName));
			}catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}

		// Print the Realtor objects
		for (RentCafeClass RentCafe : RentCafeList) {
			System.out.println(RentCafe.toString());
		}

		// Close the WebDriver
		driver.quit();
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
