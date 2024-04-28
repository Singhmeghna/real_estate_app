package com.accgroupproject.realestate;  // Package declaration for the RealEstate repository.

import org.springframework.beans.factory.annotation.Autowired;  // Importing Autowired annotation for dependency injection.
import org.springframework.data.domain.Sort;  // Importing Sort class for sorting criteria.
import org.springframework.data.mongodb.core.MongoTemplate;  // Importing MongoTemplate for MongoDB operations.
import org.springframework.data.mongodb.core.query.Criteria;  // Importing Criteria for defining query conditions.
import org.springframework.data.mongodb.core.query.Query;  // Importing Query for constructing MongoDB queries.

import java.util.List;  // Importing List class for handling collections.
import java.util.Optional;  // Importing Optional class for handling optional values.
import java.util.regex.Pattern;  // Importing Pattern for regular expressions.

public class RealEstateRepositoryCustomImpl implements RealEstateRepositoryCustom {

    private final MongoTemplate mongoTemplate;  // Dependency injection for MongoTemplate.

    public RealEstateRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;  // Initializing MongoTemplate.
    }

    // Method to find RealEstate entities based on specified filters.
    @Override
    public List<RealEstate> findByFilters(String city, String country, String price, String bed, String bath, String type) {
        Query query = new Query();  // Creating a MongoDB query.

        if (city != null) {
            query.addCriteria(Criteria.where("city").is(city));  // Adding criteria for city.
        }

        if (country != null) {
            query.addCriteria(Criteria.where("country").is(country));  // Adding criteria for country.
        }

        if (price != null) {
            // Add criteria for price range if needed
            // Example: query.addCriteria(Criteria.where("price").gte(minPrice).lte(maxPrice));
        }

        if (bed != null) {
            query.addCriteria(Criteria.where("bed").is(bed));  // Adding criteria for bed.
        }

        if (bath != null) {
            query.addCriteria(Criteria.where("bath").is(bath));  // Adding criteria for bath.
        }

        if (type != null) {
            query.addCriteria(Criteria.where("type").is(type));  // Adding criteria for type.
        }

        return mongoTemplate.find(query, RealEstate.class);  // Executing the query and returning results.
    }

    // Method for performing auto-suggestion based on title.
    @Override
    public List<RealEstate> autoSuggest(String title) {
        Query query = new Query();  // Creating a MongoDB query.

        if (title != null) {
            // Use a regular expression for partial string matching
            Criteria criteria = new Criteria().orOperator(
                    Criteria.where("title").regex(Pattern.compile(title, Pattern.CASE_INSENSITIVE)),
                    Criteria.where("city").regex(Pattern.compile(title, Pattern.CASE_INSENSITIVE)),
                    Criteria.where("desc").regex(Pattern.compile(title, Pattern.CASE_INSENSITIVE))
                );

            query.addCriteria(criteria);  // Adding criteria for auto-suggestion.
        }

        return mongoTemplate.find(query, RealEstate.class);  // Executing the query and returning results.
    }

    // Method for searching and filtering RealEstate entities based on a partial string.
    @Override
    public List<RealEstate> searchAndFilter(String title) {
        Query query = new Query();  // Creating a MongoDB query.

        if (title != null) {
            // Use a regular expression for partial string matching
            Criteria criteria = new Criteria().orOperator(
                    Criteria.where("title").regex(Pattern.compile(title, Pattern.CASE_INSENSITIVE)),
                    Criteria.where("city").regex(Pattern.compile(title, Pattern.CASE_INSENSITIVE)),
                    Criteria.where("desc").regex(Pattern.compile(title, Pattern.CASE_INSENSITIVE))
                );

            query.addCriteria(criteria);  // Adding criteria for search and filter.
        }

        return mongoTemplate.find(query, RealEstate.class);  // Executing the query and returning results.
    }

    // Method to find RealEstate entities by city and type for scraping purposes.
    @Override
    public List<RealEstate> findByFiltersScrape(String city, String type) {
        Query query = new Query();  // Creating a MongoDB query.

        if (city != null) {
            query.addCriteria(Criteria.where("city").is(city));  // Adding criteria for city.
        }

        if (type != null) {
            query.addCriteria(Criteria.where("type").is(type));  // Adding criteria for type.
        }

        return mongoTemplate.find(query, RealEstate.class);  // Executing the query and returning results.
    }

    // Method to find RealEstate entities by city and country.
    @Override
    public List<RealEstate> getCityAndCountry(String city, String country) {
        Query query = new Query();  // Creating a MongoDB query.

        if (city != null) {
            query.addCriteria(Criteria.where("city").is(city));  // Adding criteria for city.
        }

        if (country != null) {
            query.addCriteria(Criteria.where("country").is(country));  // Adding criteria for country.
        }

        return mongoTemplate.find(query, RealEstate.class);  // Executing the query and returning results.
    }
}
