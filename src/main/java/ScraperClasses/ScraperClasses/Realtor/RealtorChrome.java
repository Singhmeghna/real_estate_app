package ScraperClasses.ScraperClasses.Realtor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RealtorChrome {
  public static void main(String[] args) {
    // Set the path to the ChromeDriver executable (you need to download it separately)
    System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");

    // Initialize ChromeOptions for headless mode
    ChromeOptions chromeOptions = new ChromeOptions();
    // chromeOptions.addArguments("--headless"); // Enable headless mode

    // Initialize the WebDriver with the ChromeOptions
    WebDriver driver = new ChromeDriver(chromeOptions);

    // Open the webpage
    driver.get("https://www.realtor.ca/on/waterloo/real-estate");
    WebDriverWait wait = new WebDriverWait(driver, 10);

    // Find all the li elements with a specific XPath
    List<WebElement> liElements = driver.findElements(By.xpath("//ul[@class='listingCardList']/li[@class='cardCon']"));

    // Create an ArrayList to store the Realtor objects
    ArrayList<RealtorClass> realtorList = new ArrayList<RealtorClass>();

    // Iterate through each li element
    for (WebElement liElement : liElements) {
        // Find the image, price, address, bed, bath, and office name elements within the context of the liElement
        WebElement listingCardImage = liElement.findElement(By.className("listingCardImage"));
        WebElement listingCardPrice = liElement.findElement(By.className("listingCardPrice"));
        WebElement listingCardAddress = liElement.findElement(By.className("listingCardAddress"));
        WebElement listingCardBed = liElement.findElement(By.xpath(".//div[@class='listingCardIconCon'][1]/div[@class='listingCardIconTopCon']/div[@class='listingCardIconNum']"));
        WebElement listingCardBath = liElement.findElement(By.xpath(".//div[@class='listingCardIconCon'][2]/div[@class='listingCardIconTopCon']/div[@class='listingCardIconNum']"));
        WebElement listingCardOfficeName = liElement.findElement(By.className("listingCardOfficeName"));

        String imgSrc = listingCardImage.getAttribute("src");
        String price = listingCardPrice.getText();
        String address = listingCardAddress.getText();
        String bed = listingCardBed.getText();
        String bath = listingCardBath.getText();
        String officeName = listingCardOfficeName.getText();

        realtorList.add(new RealtorClass(imgSrc, price, address, bed, bath, officeName));
    }

    // Print the Realtor objects
    for (RealtorClass realtor : realtorList) {
        System.out.println(realtor.toString());
    }

    // Close the WebDriver
    driver.quit();
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
      return "Realtor [imgSrc=" + imgSrc + ", price=" + price + ", address=" + address + ", bed=" + bed + ", bath="
        + bath + ", officeName=" + officeName + "]";
    }
  }
}
