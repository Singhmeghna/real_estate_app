// Package declaration for the SchedulerDynamic class
package canada;

// Import statements for Java utility classes
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Import statements for scraper classes
import ScraperClasses.ScraperClasses.Realtor.Realtor;
import ScraperClasses.ScraperClasses.RealtorDotCom.RealtorDotCom;
import ScraperClasses.ScraperClasses.RentCafe.RentCafe;
import accproject.scheduler.db.GetSchedulerTimeStamp;
import zillow.ZillowUS;

// Class definition for the SchedulerDynamic
public class SchedulerDynamic {

    // Instance variables for city and categoryOption
    String city;
    int categoryOption;

    // Constructor to initialize city and categoryOption
    public SchedulerDynamic(String city, int categoryOption) {
        this.categoryOption = categoryOption;
        this.city = city;
        scrape(); // Call the scrape method
    }

    // Method to initiate scraping process
    private void scrape() {
        System.out.println("Starting");
        GetSchedulerTimeStamp.saveQueryToMongo(city);

        // Create a map to store city-province information
        Map<String, String[]> cityProvinceMap = new HashMap<>();
        cityProvinceMap.put("toronto", new String[]{"Ontario", "ON"});
        // Add more city-province mappings as needed

        // Validate if the entered city is in the dictionary
        if (!cityProvinceMap.containsKey(city)) {
            System.out.println("Invalid city. Exiting program.");
            System.exit(0);
        }

        // Get the province and province ISO code based on the entered city
        String[] provinceInfo = cityProvinceMap.get(city);
        String fullProvinceName = provinceInfo[0];
        String provinceISO = provinceInfo[1];

        // Replace white spaces in full province name with "-"
        fullProvinceName = fullProvinceName.replace(" ", "-");

        // Map the selected option to the corresponding category
        String category;
        switch (categoryOption) {
            case 1:
                category = "individual_house";
                break;
            case 2:
                category = "apartment";
                break;
            case 3:
                category = "townhouse";
                break;
            default:
                System.out.println("Invalid category. Exiting program.");
                System.exit(0);
                return;
        }

        // Call the CanadaRealtimeScraper method
        canadaRealtimeScraper(city, category, provinceISO, fullProvinceName);

        // Print the end message
        System.out.println("End");
    }

    // Method to perform real-time scraping based on category
    private void canadaRealtimeScraper(String city, String category, String province, String provinceFullName) {

        try {
            // Invoke scraping based on category and source websites
            if (category.equals("individual_house"))
                new RentCafe().scrape(city, "canada", category,
                        "https://www.rentcafe.com/houses-for-rent/ca/" + province + "/" + city + "/");

            if (category.equals("apartment"))
                new RentCafe().scrape(city, "canada", category, "https://www.rentcafe.com/apartments-for-rent/ca/"
                        + province + "/" + city + "/?PropertyType=Apartment");

            if (category.equals("townhouse"))
                new RentCafe().scrape(city, "canada", category,
                        "https://www.rentcafe.com/townhomes-for-rent/ca/" + province + "/" + city + "/");

            if (category.equals("individual_house"))
                new Realtor().scrape(city, "canada", category,
                        "https://www.realtor.ca/" + province + "/" + city + "/real-estate");

            if (category.equals("individual_house"))
                new RealtorDotCom().scrape(city, "canada", category, "https://www.realtor.com/international/" + province
                        + "/" + city + "-" + provinceFullName + "/rent/house/");

            if (category.equals("apartment"))
                new RealtorDotCom().scrape(city, "canada", category, "https://www.realtor.com/international/" + province
                        + "/" + city + "-" + provinceFullName + "/rent/apartment/");

            if (category.equals("townhouse"))
                new RealtorDotCom().scrape(city, "canada", category, "https://www.realtor.com/international/" + province
                        + "/" + city + "-" + provinceFullName + "/rent/townhouse/");

            if (category.equals("individual_house"))
                new ZillowUS().scrape(city, "canada", category,
                        "https://www.zillow.com/" + provinceFullName + "-" + province + "/rent-houses");

            if (category.equals("apartment"))
                new ZillowUS().scrape(city, "canada", category,
                        "https://www.zillow.com/" + provinceFullName + "-" + province + "/apartments");

            if (category.equals("townhouse"))
                new ZillowUS().scrape(city, "canada", category,
                        "https://www.zillow.com/" + provinceFullName + "-" + province + "/rentals");

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // Main method to start the program
    public static void main(String args[]) {
        Scanner mScanner = new Scanner(System.in);
        System.out.println("Cozy Rentals Scraper");
        System.out.print("Enter the city name: ");
        String city = mScanner.nextLine();
        String timeStamp = GetSchedulerTimeStamp.getTimestampFromMongo(city);

        if (timeStamp != null) {
            System.out.println("It looks like the scraping is already done for the city: " + city + " on " + timeStamp);

            System.out.print("Confirm with YES/NO:");
            String prompt = mScanner.nextLine();

            if (prompt.equalsIgnoreCase("YES")) {
                System.out.print("Select the category (1: individual_house, 2: apartment, 3: townhouse): ");
                int categoryOption = mScanner.nextInt();

                SchedulerDynamic mDynamic = new SchedulerDynamic(city, categoryOption);
            } else {
                System.out.println("Exiting the Program");
                System.exit(0);
            }
        } else {
            System.out.print("Select the category (1: individual_house, 2: apartment, 3: townhouse): ");
            int categoryOption = mScanner.nextInt();

            SchedulerDynamic mDynamic = new SchedulerDynamic(city, categoryOption);
        }
    }
}
