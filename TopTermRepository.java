// Package declaration for the TopTermRepository interface
package com.accgroupproject.realestate.topterms;

// Import statements for Spring Framework dependencies
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

// MongoDB repository interface for TopTerm entities
public interface TopTermRepository extends MongoRepository<TopTerm, String> {

// Custom query method to find a TopTerm by its key and country
    TopTerm findByKey(String key, String country);
}