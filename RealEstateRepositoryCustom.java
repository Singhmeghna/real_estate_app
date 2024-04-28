package com.accgroupproject.realestate;

import java.util.List;

public interface RealEstateRepositoryCustom {
	List<RealEstate> findByFilters(String city, String country, String price, String bed, String bath, String type);

	List<RealEstate> findByFiltersScrape(String city, String type);

	List<RealEstate> autoSuggest(String title);

	List<RealEstate> searchAndFilter(String title);

	List<RealEstate> getCityAndCountry(String city, String country);
}
