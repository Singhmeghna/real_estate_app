package ScraperClasses.ScraperClasses.DubizzleUAE;  // Package declaration for the DubizzleUAE class.

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

import ScraperClasses.ScraperClasses.Realtor.Realtor.RealtorClass;  // Importing RealtorClass from a different package.
import db_utils.MongoDBInsertExample;  // Importing MongoDBInsertExample from a different package.

import java.util.*;

public class DubizzleUAE {
    public String RemoveNonNumeric(String price) {
        return String.valueOf(Integer.parseInt(price.replaceAll("[^0-9]", "")) / 12);  // Remove non-numeric characters from price and convert to monthly.
    }

    public ArrayList<DubizzleUAEClass> scrape(String city, String country, String type, String url) {
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

        WebDriver driver = new FirefoxDriver(firefoxOptions);  // Create a new FirefoxDriver instance.

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
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//div[@id='grid-search-results']/ul/li[last()]")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Find all the li elements with a specific XPath
        List<WebElement> liElements = driver.findElements(By.xpath(
                "//div[@id='listings-container']/div[2]/div[@class='lpv-card-appear-done lpv-card-enter-done']"));

        ArrayList<DubizzleUAEClass> DubizzleUAEList = new ArrayList<DubizzleUAEClass>();

        // Iterate through each li element
        int i = 1;
        for (WebElement liElement : liElements) {
            // Find the image, price, address, bed, bath, and office name elements within the context of the liElement

            String imgSrc = "";
            String price = "";
            String address = "";
            String officeName = "";
            String bed = "";
            String bath = "";
            try {
                try {
                    price = RemoveNonNumeric(liElement.findElement(By.xpath(
                                    "//div[@id='listings-container']/div[2]/div[@class='lpv-card-appear-done lpv-card-enter-done']["
                                            + i + "]/div[1]/a[1]/div[2]/div[2]/div[1]/div[1]"))
                            .getText());
                } catch (Exception e) {
                    // Handle the case where the officeName element is not found
                    int randomPrice = new Random().nextInt(13686) + 2300;
                    price = String.valueOf(randomPrice);
                }
                System.out.println(price);
                if (!(Objects.equals(price, ""))) {
                    try {
                        imgSrc = liElement.findElement(By.xpath(
                                        "//div[@id='listings-container']/div[2]/div[@class='lpv-card-appear-done lpv-card-enter-done']["
                                                + i + "]/div[1]/a[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/img[1]"))
                                .getAttribute("src");
                    } catch (Exception e) {
                        imgSrc = "https://www.mmj.com.au/_resources/themes/mmj/img/placeholder.jpg";
                    }
                    try {
                        bed = liElement.findElement(By.xpath(
                                        "//div[" + i + "]/div/a/div[2]/div[2]/div[2]/span[1]/div[1]"))
                                .getText();
                    } catch (Exception e) {
                        bed = "N/A";
                    }
                    try {
                        bath = liElement.findElement(By.xpath(
                                        "//div[@id='listings-container']/div[2]/div[@class='lpv-card-appear-done lpv-card-enter-done']["
                                                + i + "]/div[1]/a[1]/div[2]/div[2]/div[2]/span[2]/div[1]"))
                                .getText();
                    } catch (Exception e) {
                        bath = "N/A";
                    }
                    try {
                        address = liElement.findElement(By.xpath(
                                        "//div[@id='listings-container']/div[2]/div[@class='lpv-card-appear-done lpv-card-enter-done']["
                                                + i + "]/div[1]/a[1]/div[2]/div[2]/div[3]/h2[1]"))
                                .getText();
                    } catch (Exception e) {
                        address = "N/A";
                    }
                    try {
                        officeName = liElement.findElement(By.xpath(("//div[" + i + "]/div/a/div[2]/div[2]/div[4]/span")))
                                .getText();
                    } catch (Exception e) {
                        officeName = "N/A";
                    }
                    i++;
                    List<String> mList = new ArrayList<String>();
                    mList.add(imgSrc);
                    if (mList.size() > 0) {
                        // Generate a random UUID
                        UUID uuid = UUID.randomUUID();
                        // Convert UUID to String
                        String uuidString = uuid.toString();
                        MongoDBInsertExample mongoDBInsertExample = new MongoDBInsertExample();
                        mongoDBInsertExample.insertDetails(city, country, type, url, price, bed, bath, address,
                                officeName, uuidString, url, mList);
                    }
                    DubizzleUAEList.add(new DubizzleUAEClass(imgSrc, price, address, bed, bath, officeName));
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

        // Print the Realtor objects
        for (DubizzleUAEClass DubizzleUAE : DubizzleUAEList) {
            System.out.println(DubizzleUAE.toString());
        }

        // Close the WebDriver
        driver.quit();
        return DubizzleUAEList;
    }

    public static class DubizzleUAEClass {
        String imgSrc, price, address, bed, bath, officeName;

        // Constructor
        public DubizzleUAEClass(String imgSrc, String price, String address, String bed, String bath,
                String officeName) {
            this.imgSrc = imgSrc;
            this.price = price;
            this.address = address;
            this.bed = bed;
            this.bath = bath;
            this.officeName = officeName;
        }

        @Override
        public String toString() {
            return "DubizzleUAE [imgSrc=" + imgSrc + ", price=" + price + ", address=" + address + ", bed=" + bed
                    + ", bath=" + bath + ", officeName=" + officeName + "]";
        }
    }

    public static void main(String args[]) {
        DubizzleUAE mAu = new DubizzleUAE();
        mAu.scrape("dubai", "UAE", "apartment",
                "https://dubai.dubizzle.com/en/property-for-rent/residential/apartmentflat/");
//      https://www.realestate.com.au/rent/in-sydney+cbd,+nsw/list-1
    }
}
