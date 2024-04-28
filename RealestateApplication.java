package com.accgroupproject.realestate;  // Package declaration for the RealEstate application.

import org.springframework.boot.SpringApplication;  // Importing SpringApplication for bootstrapping the application.
import org.springframework.boot.autoconfigure.SpringBootApplication;  // Importing SpringBootApplication for enabling auto-configuration.
import org.springframework.context.annotation.ComponentScan;  // Importing ComponentScan for specifying base packages.

@SpringBootApplication  // Indicates that this class is a Spring Boot application.
@ComponentScan(basePackages = {"com.accgroupproject.realestate","com.accgroupproject.realestate.topterms", "com.accgroupproject.realestate.searchingfrequency.services"})
public class RealestateApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealestateApplication.class, args);  // Run the Spring Boot application.
    }

}
