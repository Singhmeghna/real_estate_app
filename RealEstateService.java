package com.accgroupproject.realestate.services;  // Package declaration for the RealEstate services.

import java.util.List;  // Importing the List class for handling collections.
import java.util.Optional;  // Importing the Optional class for handling optional values.

import org.springframework.beans.factory.annotation.Autowired;  // Importing the Autowired annotation for dependency injection.
import org.springframework.data.mongodb.core.MongoTemplate;  // Importing MongoTemplate for MongoDB operations.
import org.springframework.data.mongodb.core.aggregation.Aggregation;  // Importing Aggregation for MongoDB aggregation framework.
import org.springframework.data.mongodb.core.aggregation.AggregationResults;  // Importing AggregationResults to capture results of MongoDB aggregation.
import org.springframework.data.mongodb.core.query.Criteria;  // Importing Criteria for defining query conditions.
import org.springframework.stereotype.Service;  // Importing Service annotation to indicate this class as a service.

import com.accgroupproject.realestate.RealEstate;  // Importing RealEstate class.
import com.accgroupproject.realestate.RealEstateRepository;  // Importing RealEstateRepository interface.

@Service  // Marking this class as a service component.
public class RealEstateService {
    private final RealEstateRepository realEstateRepository;  // Dependency injection for RealEstateRepository.
    private final MongoTemplate mongoTemplate;  // Dependency injection for MongoTemplate.

    @Autowired  // Annotation for constructor injection.
    public RealEstateService(RealEstateRepository realEstateRepository, MongoTemplate mongoTemplate) {
        this.realEstateRepository = realEstateRepository;  // Initializing RealEstateRepository.
        this.mongoTemplate = mongoTemplate;  // Initializing MongoTemplate.
    }

    // Method to increment the number of visits for a RealEstate entity.
    public void incrementNumberOfVisits(String id) {
        Optional<RealEstate> optionalRealEstate = realEstateRepository.findById(id);  // Retrieving RealEstate by ID.
        if (optionalRealEstate.isPresent()) {  // Checking if RealEstate is present.
            RealEstate realEstate = optionalRealEstate.get();  // Extracting RealEstate from Optional.
            realEstate.setNumberOfVisits(realEstate.getNumberOfVisits() + 1);  // Incrementing the number of visits.
            realEstateRepository.save(realEstate);  // Saving the updated RealEstate.
        }
    }

    // Method to get the count of RealEstate entities by type using MongoDB aggregation.
    public Long getCountByType(String type) {
        AggregationResults<TypeCount> results = mongoTemplate.aggregate(
            Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is(type)),  // Matching documents by type.
                Aggregation.group().count().as("count")  // Grouping and counting the matched documents.
            ),
            RealEstate.class,  // Input class for the aggregation.
            TypeCount.class  // Output class for the aggregation results.
        );

        List<TypeCount> mappedResults = results.getMappedResults();  // Extracting mapped results.
        return mappedResults.isEmpty() ? 0L : mappedResults.get(0).getCount();  // Returning the count or 0 if no results.
    }

    // Inner class to represent the result of type-based counting in MongoDB aggregation.
    public class TypeCount {
        private String type;  // Type field for aggregation.
        private Long count;  // Count field for aggregation.

        // Getter and setter methods for the type field.
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }

        // Getter and setter methods for the count field.
        public Long getCount() {
            return count;
        }
        public void setCount(Long count) {
            this.count = count;
        }
    }
}
