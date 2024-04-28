// Package declaration for the SearchFrequencyService class
package com.accgroupproject.realestate.searchingfrequency;

// Import statement for Spring Framework's Service annotation
import org.springframework.stereotype.Service;

// Import statements for Java utility classes
import java.util.HashMap;
import java.util.Map;

// Service annotation to indicate that this class is a Spring service component
@Service
public class SearchFrequencyService {

    // Map to store search queries and their corresponding frequencies
    private final Map<String, Integer> searchFrequencyMap = new HashMap<>();

    // Method to increment the search frequency for a given query
    public void incrementSearchFrequency(String query) {
        // Using getOrDefault to safely retrieve the current frequency or default to 0 if not present
        searchFrequencyMap.put(query, searchFrequencyMap.getOrDefault(query, 0) + 1);
    }

    // Method to retrieve the entire search frequency map
    public Map<String, Integer> getSearchFrequency() {
        return searchFrequencyMap;
    }
}
