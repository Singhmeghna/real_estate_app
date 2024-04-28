package com.accgroupproject.realestate;  // Package declaration for the RealEstate repository.

import java.util.List;  // Importing List class for handling collections.
import java.util.Optional;  // Importing Optional class for handling optional values.

import org.springframework.data.mongodb.repository.MongoRepository;  // Importing MongoRepository for MongoDB CRUD operations.

public interface RealEstateRepository extends MongoRepository<RealEstate, String>, RealEstateRepositoryCustom {
    List<RealEstate> findByCity(String city);  // Method to find RealEstate entities by city.
    Optional<RealEstate> findById(String id);  // Method to find a RealEstate entity by ID.
    List<RealEstate> findTop10ByCountryOrderByNumberOfVisitsDesc(String country);  // Method to find top 10 RealEstate entities by country, ordered by the number of visits.
}
