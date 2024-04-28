package ScraperClasses.ScraperClasses.RealtorDotCom;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RealtorDotCom {
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
        firefoxOptions.addArguments("--referer=https://www.realtor.com/international/ca/toronto-ontario/rent/");


		WebDriver driver = new FirefoxDriver(firefoxOptions);

		// Open the webpage
	    driver.get("https://www.realtor.com/international/ca/toronto-ontario/rent/");

	    // Find all the li elements with a specific XPath
	    List<WebElement> liElements = driver.findElements(By.xpath("//div[@class='listings-wrapper']/ul[@class='tier-one-listing-table']/li"));

		ArrayList<RealtorDotComClass> RealtorDotComList = new ArrayList<RealtorDotComClass>();

		// Iterate through each li element
		for (WebElement liElement : liElements) {
			// Find the image, price, address, bed, bath, and office name elements within
			// the context of the liElement
			try {
				String imgSrc, price, address, bed, bath, officeName;
				try {
				    imgSrc = liElement.findElement(By.tagName("img")).getAttribute("src");
				} catch (NoSuchElementException e) {
				    // Handle the case where the img element is not found
				    imgSrc = "N/A"; // Provide a default value or handle the exception as needed
				}

				try {
				    bed = liElement.findElement(By.xpath(".//li[@class='feature'][1]/span[@class='num']")).getText();
				} catch (NoSuchElementException e) {
				    // Handle the case where the bed element is not found
				    bed = "N/A"; // Provide a default value or handle the exception as needed
				}

				try {
				    bath = liElement.findElement(By.xpath(".//li[@class='feature'][2]/span[@class='num']")).getText();
				} catch (NoSuchElementException e) {
				    // Handle the case where the bath element is not found
				    bath = "N/A"; // Provide a default value or handle the exception as needed
				}

				try {
				    address = liElement.findElement(By.xpath(".//address[@class='address']/a")).getText();
				} catch (NoSuchElementException e) {
				    // Handle the case where the address element is not found
				    address = "N/A"; // Provide a default value or handle the exception as needed
				}

				try {
				    officeName = liElement.findElement(By.xpath(".//li[@class='feature'][3]//strong")).getText();
				} catch (NoSuchElementException e) {
				    // Handle the case where the officeName element is not found
				    officeName = "N/A"; // Provide a default value or handle the exception as needed
				}

				try {
				    price = liElement.findElement(By.xpath(".//div[@class='price']//strong")).getText();
				} catch (NoSuchElementException e) {
				    // Handle the case where the price element is not found
				    price = "N/A"; // Provide a default value or handle the exception as needed
				}
				RealtorDotComList.add(new RealtorDotComClass(imgSrc, price, address, bed, bath, officeName));
			}catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}

		// Print the Realtor objects
		for (RealtorDotComClass RealtorDotCom : RealtorDotComList) {
			System.out.println(RealtorDotCom.toString());
		}

		System.out.println("printing li elements");
		System.out.println(liElements.size());
		// Close the WebDriver
		driver.quit();
	}

	public static class RealtorDotComClass {
		String imgSrc, price, address, bed, bath, officeName;

		// Constructor
		public RealtorDotComClass(String imgSrc, String price, String address, String bed, String bath, String officeName) {
			this.imgSrc = imgSrc;
			this.price = price;
			this.address = address;
			this.bed = bed;
			this.bath = bath;
			this.officeName = officeName;
		}

		@Override
		public String toString() {
			return "RealtorDotCom [imgSrc=" + imgSrc + ", price=" + price + ", address=" + address + ", bed=" + bed
					+ ", bath=" + bath + ", officeName=" + officeName + "]";
		}
	}
}
