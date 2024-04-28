package ScraperClasses.ScraperClasses.Realtor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.ArrayList;
import java.util.List;

public class RealtorOld {
  public static void main(String[] args) {
    // Set the path to the ChromeDriver executable (you need to download it separately)
    System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");

    // Initialize ChromeOptions for headless mode
    ChromeOptions chromeOptions = new ChromeOptions();
    // chromeOptions.addArguments("--headless"); // Enable headless mode
   
    WebDriver driver = new ChromeDriver(chromeOptions);

    // Open the webpage
    driver.get("https://www.realtor.ca/on/toronto/real-estate");

    // Find all the li elements with a specific XPath
    List<WebElement> liElements = driver.findElements(By.xpath("//ul[@class='listingCardList']/li[@class='cardCon']"));

    // Create an ArrayList to store the image sources
    ArrayList<String> imageSources = new ArrayList<>();

    // Iterate through each li element
    for (WebElement liElement : liElements) {
        // Find the image element within the li element (assuming the image is in an 'img' tag)
    	WebElement listingCardImage = liElement.findElement(By.className("listingCardImage"));
    	
//        [@class='listingCardImage']"));
        
        // Get the src attribute of the image and add it to the ArrayList
        String imgSrc = listingCardImage.getAttribute("src");
        imageSources.add(imgSrc);
    }

    // Print the image sources
    for (String src : imageSources) {
        System.out.println("Image Source: " + src);
    }

    // Close the WebDriver
    driver.quit();
  }
}
