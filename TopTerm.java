// Package declaration for the TopTerm class
package com.accgroupproject.realestate.topterms;

// Import statement for Spring Data MongoDB annotations
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// MongoDB Document annotation specifying the collection name
@Document(collection = "top_terms_prod")
public class TopTerm {
    
    // MongoDB-generated identifier for the document
    @Id
    private String id;

    // Fields representing attributes of a TopTerm document
    private String key;
    private String country;
    private int count;

    // Getter method for retrieving the document's identifier
    public String getId() {
        return id;
    }

    // Getter and setter methods for the 'country' attribute
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    // Setter method for setting the document's identifier
    public void setId(String id) {
        this.id = id;
    }

    // Getter and setter methods for the 'key' attribute
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    // Getter and setter methods for the 'count' attribute
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    // Additional getter and setter methods for other attributes can be added if needed
}
