// Package declaration for the UniquePatternMatching class
package com.accgroupproject.realestate.patternmatching;

// Import statements for MongoDB-related classes
import com.mongodb.client.*;

import org.bson.Document;

// Import statements for Java utility classes
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Class for unique pattern matching features in MongoDB collection
public class UniquePatternMatching {

// Constants for field names in the MongoDB collection
    private static final String FIELD_CITY = "city";
    private static final String FIELD_COUNTRY = "country";
    private static final String FIELD_TYPE = "type";

// Method to perform unique pattern matching on the MongoDB collection
    public Hashtable<String, Hashtable<String, Integer>> performPatternMatching() {

// Create an instance of UniquePatternMatching
    	UniquePatternMatching mPatternMatching = new UniquePatternMatching();

// MongoDB connection string
        String connectionString = "mongodb+srv://acc_real_estate:U3RulWV4my1egCRp@cluster0.tdacs3z.mongodb.net/?retryWrites=true&w=majority";

// Hashtable to store results
        Hashtable<String, Hashtable<String, Integer>> mHashtable = new Hashtable<String, Hashtable<String, Integer>>();

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
// Connect to the MongoDB database           
 MongoDatabase database = mongoClient.getDatabase("acc_real_estate_db");

// Choose the MongoDB collection to query
            MongoCollection<Document> collection = database.getCollection("acc_real_estate_qa");
//            MongoCollection<Document> collection = database.getCollection("acc_real_estate_dev");
            

            // Feature 1: Display total number of documents in the collection
            long totalDocuments = collection.countDocuments();
            // System.out.println("Total number of documents in the collection: " + totalDocuments);

            // Feature 2: Display unique cities, countries, and types in the collection
            mHashtable.put(FIELD_CITY, mPatternMatching.displayUniqueFieldValues(collection, FIELD_CITY));
            mHashtable.put(FIELD_COUNTRY, mPatternMatching.displayUniqueFieldValues(collection, FIELD_COUNTRY));
            mHashtable.put(FIELD_TYPE, mPatternMatching.displayUniqueFieldValues(collection, FIELD_TYPE));

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        
        return mHashtable;
    }

    private Set<String> searchRegex(MongoCollection<Document> collection, String field, String regex) {
        Set<String> matchingDocuments = new HashSet<>();
        Pattern pattern = Pattern.compile(regex);

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String fieldValue = document.getString(field);

                if (fieldValue != null) {
                    Matcher matcher = pattern.matcher(fieldValue);
                    if (matcher.find()) {
                        matchingDocuments.add(document.getString("id"));
                    }
                }
            }
        }
        return matchingDocuments;
    }


    private Hashtable<String, Integer> displayUniqueFieldValues(MongoCollection<Document> collection, String field) {
        Set<String> uniqueValues = new HashSet<>();
        Hashtable<String, Integer> mHashtable = new Hashtable<String, Integer>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String fieldValue = document.getString(field);

                if (fieldValue != null && !fieldValue.isEmpty()) {
                    uniqueValues.add(fieldValue);
                }
            }
        }

		uniqueValues.forEach(value -> {
			Set<String> matchingDocuments = searchRegex(collection,field, value);
			mHashtable.put(value, matchingDocuments.size());
		});
		
		return mHashtable;
    }

    // Feature 3: Search using KMP algorithm
    private Set<String> searchKMP(MongoCollection<Document> collection, String field, String pattern) {
        Set<String> matchingDocuments = new HashSet<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String fieldValue = document.getString(field);

                if (fieldValue != null) {
                    List<Integer> matches = kmpSearch(fieldValue, pattern);
                    if (!matches.isEmpty()) {
                        matchingDocuments.add(document.getString("id"));
                    }
                }
            }
        }

        return matchingDocuments;
    }

    // KMP algorithm for pattern searching
    private List<Integer> kmpSearch(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int[] lps = calculateLPSArray(pattern);

        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == pattern.length()) {
                matches.add(i - j);
                j = lps[j - 1];
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return matches;
    }

    // Helper method to calculate the Longest Proper Prefix which is also Suffix (LPS) array
    private int[] calculateLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}
