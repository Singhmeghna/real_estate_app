package canada;

import ScraperClasses.ScraperClasses.DubizzleUAE.DubizzleUAE;
import ScraperClasses.ScraperClasses.Realtor.Realtor;
import ScraperClasses.ScraperClasses.RealtorDotCom.RealtorDotCom;
import ScraperClasses.ScraperClasses.RentCafe.RentCafe;
import acres99.Acres99;
import trulia.TruliaUS;
import zillow.ZillowUS;
import zumper.ZumperUS;

public class Scheduler {

	public static void main(String[] args) {
		System.out.print("Starting");
		
//
		new DubizzleUAE().scrape("dubai", "uae", "apartment",
				"https://dubai.dubizzle.com/en/property-for-rent/residential/apartmentflat/");
		new DubizzleUAE().scrape("dubai", "uae", "individual_house",
				"https://dubai.dubizzle.com/en/property-for-rent/residential/villahouse/");
		new DubizzleUAE().scrape("dubai", "uae", "townhouse",
				"https://dubai.dubizzle.com/en/property-for-rent/residential/townhouse/");
				
		new DubizzleUAE().scrape("sharjah", "uae", "apartment",
				"https://sharjah.dubizzle.com/property-for-rent/residential/apartmentflat/");
		new DubizzleUAE().scrape("sharjah", "uae", "individual_house",
				"https://sharjah.dubizzle.com/property-for-rent/residential/villahouse/");
		new DubizzleUAE().scrape("sharjah", "uae", "townhouse",
				"https://sharjah.dubizzle.com/property-for-rent/residential/townhouse/");

		new DubizzleUAE().scrape("abudhabi", "uae", "apartment",
				"https://abudhabi.dubizzle.com/property-for-rent/residential/apartmentflat/");
		new DubizzleUAE().scrape("abudhabi", "uae", "individual_house",
				"https://abudhabi.dubizzle.com/property-for-rent/residential/villahouse/");
		new DubizzleUAE().scrape("abudhabi", "uae", "townhouse",
				"https://abudhabi.dubizzle.com/property-for-rent/residential/townhouse/");

		
		
		// countries & cities
		// US - New york, los angles, houston, chicago
		

		// India - 99 Acres 
		//image scraping issue - in indian site
//		//rent house
//		new Acres99().scrape("chennai","india","individual_house","https://www.99acres.com/search/property/buy/chennai?city=32&keyword=chennai&preference=S&area_unit=1&res_com=R");
//		new Acres99().scrape("delhi","india","individual_house","https://www.99acres.com/search/property/buy/delhi?city=32&keyword=delhi&preference=S&area_unit=1&res_com=R");
//		new Acres99().scrape("mumbai","india","individual_house","https://www.99acres.com/search/property/buy/mumbai?city=12&keyword=mumbai&preference=S&area_unit=1&res_com=R");
//
//		//apartments
//		new Acres99().scrape("chennai","india","apartment","https://www.99acres.com/search/property/buy/residential-apartments/mumbai-all?city=12&property_type=1&preference=S&area_unit=1&budget_min=0&res_com=R&isPreLeased=N");
//		new Acres99().scrape("delhi","india","apartment","https://www.99acres.com/search/property/buy/residential-apartments/delhi?city=12&property_type=1&preference=S&area_unit=1&budget_min=0&res_com=R&isPreLeased=N");
//		new Acres99().scrape("mumbai","india","apartment","https://www.99acres.com/search/property/buy/residential-apartments/chennai?city=32&property_type=1&preference=S&area_unit=1&budget_min=0&res_com=R&isPreLeased=N");
//
//		//townhouse
//		new Acres99().scrape("chennai","india","townhouse","https://www.99acres.com/search/property/buy/independent-house/delhi?keyword=delhi&property_type=2&preference=S&area_unit=1&budget_min=0&res_com=R&isPreLeased=N");
//		new Acres99().scrape("delhi","india","townhouse","https://www.99acres.com/search/property/buy/independent-house/chennai?keyword=chennai&property_type=2&preference=S&area_unit=1&budget_min=0&res_com=R&isPreLeased=N");
//		new Acres99().scrape("mumbai","india","townhouse","https://www.99acres.com/search/property/buy/independent-house/mumbai?keyword=mumbai&property_type=2&preference=S&area_unit=1&budget_min=0&res_com=R&isPreLeased=N");

		
		//canada rentcafe
		// house
		new RentCafe().scrape("toronto","canada","individual_house","https://www.rentcafe.com/houses-for-rent/ca/on/toronto/");
		new RentCafe().scrape("windsor","canada","individual_house","https://www.rentcafe.com/houses-for-rent/ca/on/windsor/");
		new RentCafe().scrape("montreal","canada","individual_house","https://www.rentcafe.com/houses-for-rent/ca/qc/montreal/");
		new RentCafe().scrape("vancover","canada","individual_house","https://www.rentcafe.com/houses-for-rent/ca/bc/vancover/");

		//apartment
		new RentCafe().scrape("toronto","canada","apartment","https://www.rentcafe.com/apartments-for-rent/ca/on/toronto/?PropertyType=Apartment");
		new RentCafe().scrape("windsor","canada","apartment","https://www.rentcafe.com/apartments-for-rent/ca/on/windsor/?PropertyType=Apartment");
		new RentCafe().scrape("montreal","canada","apartment","https://www.rentcafe.com/apartments-for-rent/ca/qc/montreal/?PropertyType=Apartment");
		new RentCafe().scrape("vancover","canada","apartment","https://www.rentcafe.com/apartments-for-rent/ca/bc/vancover/?PropertyType=Apartment");

		// townhouse

		new RentCafe().scrape("toronto","canada","townhouse","https://www.rentcafe.com/townhomes-for-rent/ca/on/toronto/");
		new RentCafe().scrape("windsor","canada","townhouse","https://www.rentcafe.com/townhomes-for-rent/ca/on/windsor/");
		new RentCafe().scrape("montreal","canada","townhouse","https://www.rentcafe.com/townhomes-for-rent/ca/qc/montreal/");
		new RentCafe().scrape("vancover","canada","townhouse","https://www.rentcafe.com/townhomes-for-rent/ca/bc/vancover/");
		//canada realtor. ca

		new Realtor().scrape("toronto","canada","apartment","https://www.realtor.ca/on/toronto/real-estate");
		new Realtor().scrape("windsor","canada","apartment","https://www.realtor.ca/on/windsor/real-estate");
		new Realtor().scrape("montreal","canada","apartment","https://www.realtor.ca/qc/montreal/real-estate");
		new Realtor().scrape("vancover","canada","apartment","https://www.realtor.ca/bc/vancover/real-estate");


		//house
		new RealtorDotCom().scrape("toronto","canada","individual_house","https://www.realtor.com/international/ca/toronto-ontario/rent/house/");
		new RealtorDotCom().scrape("montreal","canada","individual_house","https://www.realtor.com/international/ca/montreal-quebec/rent/house/");
		new RealtorDotCom().scrape("windsor","canada","individual_house","https://www.realtor.com/international/ca/windsor-heights-ontario/rent/house/");

		//apartment

		new RealtorDotCom().scrape("toronto","canada","apartment","https://www.realtor.com/international/ca/toronto-ontario/rent/apartment/");
		new RealtorDotCom().scrape("montreal","canada","apartment","https://www.realtor.com/international/ca/montreal-quebec/rent/apartment/");
		new RealtorDotCom().scrape("windsor","canada","apartment","https://www.realtor.com/international/ca/windsor-heights-ontario/rent/apartment/");
		//townhouse


		new RealtorDotCom().scrape("toronto","canada","townhouse","https://www.realtor.com/international/ca/toronto-ontario/rent/townhouse/");
		new RealtorDotCom().scrape("montreal","canada","townhouse","https://www.realtor.com/international/ca/montreal-quebec/rent/townhouse/");
		new RealtorDotCom().scrape("windsor","canada","townhouse","https://www.realtor.com/international/ca/windsor-heights-ontario/rent/townhouse/");
		
		
		
//		//canada realtor .dot com 
//		
//		//house
//		new RealtorDotCom().scrape("https://www.realtor.com/international/ca/toronto-ontario/rent/house/");
//		new RealtorDotCom().scrape("https://www.realtor.com/international/ca/montreal-quebec/rent/house/");
//		new RealtorDotCom().scrape("https://www.realtor.com/international/ca/windsor-heights-ontario/rent/house/");
//
//		//apartment
//
//		new RealtorDotCom().scrape("https://www.realtor.com/international/ca/toronto-ontario/rent/apartment/");
//		new RealtorDotCom().scrape("https://www.realtor.com/international/ca/montreal-quebec/rent/apartment/");
//		new RealtorDotCom().scrape("https://www.realtor.com/international/ca/windsor-heights-ontario/rent/apartment/");
//		//townhouse
//		
//
//		new RealtorDotCom().scrape("https://www.realtor.com/international/ca/toronto-ontario/rent/townhouse/");
//		new RealtorDotCom().scrape("https://www.realtor.com/international/ca/montreal-quebec/rent/townhouse/");
//		new RealtorDotCom().scrape("https://www.realtor.com/international/ca/windsor-heights-ontario/rent/townhouse/");
//		
		// US - Zumper 
		
		//rent house
		new ZumperUS().scrape("chicago","us","individual_house","https://www.zumper.com/apartments-for-rent/chicago-il?property-types=13,21");
		new ZumperUS().scrape("houston","us","individual_house","https://www.zumper.com/apartments-for-rent/houston-tx?property-types=13,21");
		new ZumperUS().scrape("new_york","us","individual_house","https://www.zumper.com/apartments-for-rent/new-york-ny?property-types=13,21");
		new ZumperUS().scrape("los_angeles","us","individual_house","https://www.zumper.com/apartments-for-rent/los-angeles-ca?property-types=13,21");


		//apartments
		new ZumperUS().scrape("chicago","us","apartment","https://www.zumper.com/apartments-for-rent/chicago-il?property-types=4,15,5,14,9");
		new ZumperUS().scrape("houston","us","apartment","https://www.zumper.com/apartments-for-rent/houston-tx?property-types=4,15,5,14,9");
		new ZumperUS().scrape("new_york","us","apartment","https://www.zumper.com/apartments-for-rent/new-york-ny?property-types=4,15,5,14,9");
		new ZumperUS().scrape("los_angeles","us","apartment","https://www.zumper.com/apartments-for-rent/los-angeles-ca?property-types=4,15,5,14,9");

		//townhouse

		new ZumperUS().scrape("chicago","us","townhouse","https://www.zumper.com/apartments-for-rent/chicago-il?property-types=2,14");
		new ZumperUS().scrape("houston","us","townhouse","https://www.zumper.com/apartments-for-rent/houston-tx?property-types=2,14");
		new ZumperUS().scrape("new_york","us","townhouse","https://www.zumper.com/apartments-for-rent/new-york-ny?property-types=2,14");
		new ZumperUS().scrape("los_angeles","us","townhouse","https://www.zumper.com/apartments-for-rent/los-angeles-ca?property-types=2,14");

		
		// US - zillow 
		//rent house
		new ZillowUS().scrape("new_york","us","townhouse","https://www.zillow.com/new-york-ny/rent-houses");
		new ZillowUS().scrape("houston","us","individual_house","https://www.zillow.com/houston-tx/rent-houses");
		new ZillowUS().scrape("chicago","us","individual_house","https://www.zillow.com/chicago-il/rent-houses");
		new ZillowUS().scrape("los_angeles","us","individual_house","https://www.zillow.com/los-angeles-ca/rent-houses");
//		//apartments
		new ZillowUS().scrape("new_york","us","apartment","https://www.zillow.com/new-york-ny/apartments");
		new ZillowUS().scrape("houston","us","apartment","https://www.zillow.com/houston-tx/apartments");
		new ZillowUS().scrape("chicago","us","apartment","https://www.zillow.com/chicago-il/apartments");
		new ZillowUS().scrape("los_angeles","us","apartment","https://www.zillow.com/los-angeles-ca/apartments");
//		//townhouse
		new ZillowUS().scrape("new_york","us","townhouse","https://www.zillow.com/new-york-ny/rentals");
		new ZillowUS().scrape("houston","us","townhouse","https://www.zillow.com/houston-tx/rentals");
		new ZillowUS().scrape("chicago","us","townhouse","https://www.zillow.com/chicago-il/rentals");
		new ZillowUS().scrape("los_angeles","us","townhouse","https://www.zillow.com/los-angeles-ca/rentals");


		// Canada - zillow
		//rent house
		new ZillowUS().scrape("toronto","canada","individual_house","https://www.zillow.com/toronto-on/rent-houses");
		new ZillowUS().scrape("windsor","canada","individual_house","https://www.zillow.com/windsor-on/rent-houses");
		new ZillowUS().scrape("montreal","canada","individual_house","https://www.zillow.com/montreal-qc/rent-houses");
		new ZillowUS().scrape("vancouver","canada","individual_house","https://www.zillow.com/vancouver-bc/rent-houses");
//
//		//apartments
		new ZillowUS().scrape("toronto","canada","apartment","https://www.zillow.com/toronto-on/apartments");
		new ZillowUS().scrape("windsor","canada","apartment","https://www.zillow.com/windsor-on/apartments");
		new ZillowUS().scrape("montreal","canada","apartment","https://www.zillow.com/montreal-qc/apartments");
		new ZillowUS().scrape("vancouver","canada","apartment","https://www.zillow.com/vancouver-bc/apartments");
//
//		//townhouse
		new ZillowUS().scrape("toronto","canada","townhouse","https://www.zillow.com/toronto-on/rentals");
		new ZillowUS().scrape("windsor","canada","townhouse","https://www.zillow.com/windsor-on/rentals");
		new ZillowUS().scrape("montreal","canada","townhouse","https://www.zillow.com/montreal-qc/rentals");
		new ZillowUS().scrape("vancouver","canada","townhouse","https://www.zillow.com/vancouver-bc/rentals");


		// Canada - Toronto, windsor, montreal, vancouver
		// india - delhi, mumbai, chennai, bangalore
		
		
		
		// rental type
		// Apartments, houses, condos
		System.out.print("end");
		
		
	}
}
