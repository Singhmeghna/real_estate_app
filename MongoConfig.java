package com.accgroupproject.realestate;  // Package declaration for the MongoConfig class.

import org.springframework.context.annotation.Bean;  // Importing Bean for creating a bean in the Spring context.
import org.springframework.context.annotation.Configuration;  // Importing Configuration for specifying that this class is a configuration class.
import org.springframework.data.mongodb.core.MongoTemplate;  // Importing MongoTemplate for interacting with MongoDB.

@Configuration  // Indicating that this class contains bean definitions and can be processed by the Spring container.
public class MongoConfig {

    private final MongoTemplate mongoTemplate;  // Injecting the MongoTemplate bean.

    // Constructor for dependency injection.
    public MongoConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // Bean definition for RealEstateRepositoryCustom.
    @Bean
    public RealEstateRepositoryCustom realEstateRepositoryCustom() {
        return new RealEstateRepositoryCustomImpl(mongoTemplate);
    }
}
