package com.accgroupproject.realestate.services;  // Package declaration for the InvertedIndexService class.

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.accgroupproject.realestate.RealEstate;
import com.accgroupproject.realestate.searchingfrequency.SearchFrequencyService;
import com.accgroupproject.realestate.util.Utilities;

@Service
public class InvertedIndexService {

    private final MongoTemplate mongoTemplate;  // MongoDB template for database operations.
    private final SearchFrequencyService searchFrequencyService;  // Service for handling search frequency.
    private final TrieNode root;  // Root node of the Trie structure.

    @Autowired
    public InvertedIndexService(MongoTemplate mongoTemplate, SearchFrequencyService searchFrequencyService) {
        this.mongoTemplate = mongoTemplate;  // Injecting the MongoDB template.
        this.searchFrequencyService = searchFrequencyService;  // Injecting the search frequency service.
        this.root = new TrieNode();  // Initializing the Trie root node.
        buildInvertedIndex(); // Call the method during initialization
    }

    // Method for searching documents based on a query.
    public Set<String> search(String query) {
        TrieNode current = root;  // Starting at the root of the Trie.

        // Normalize the query by converting to lowercase and removing unnecessary spaces
        query = query.toLowerCase().trim().replaceAll("\\s+", " ");

        Set<String> result = new HashSet<>();  // Set to store document IDs as results.

        // Traverse the Trie while considering partial matches
        for (char c : query.toCharArray()) {
            if (current.children.containsKey(c)) {
                current = current.children.get(c);  // Move to the next node in Trie.
            } else {
                // No exact match, consider partial matches
                for (TrieNode child : current.children.values()) {
                    collectDocumentIds(child, result);  // Collect document IDs for partial matches.
                }
                return result;  // Return the result set.
            }
        }

        // Collect all document IDs from the current node and its children
        collectDocumentIds(current, result);  // Collect document IDs for exact matches.

        return result;  // Return the result set.
    }

    // Helper method to collect document IDs from a TrieNode and its children.
    private void collectDocumentIds(TrieNode node, Set<String> result) {
        result.addAll(node.documentIds);  // Add all document IDs from the node to the result set.

        for (TrieNode child : node.children.values()) {
            collectDocumentIds(child, result);  // Recursively collect document IDs from children.
        }
    }

    // Method for getting details of documents based on query and document IDs.
    public List<Map<String, Object>> getDocumentDetails(String query, Set<String> documentIds) {
        List<Map<String, Object>> documentDetails = new ArrayList<>();  // List to store document details.

        for (String documentId : documentIds) {
            Map<String, Object> details = new HashMap<>();  // Map to store details of a single document.
            details.put("id", documentId);  // Adding document ID to details map.
            details.put("title", Utilities.highlightCitiesAndZipCodes(getDocumentTitle(documentId)));  // Adding highlighted title.
            details.put("description", Utilities.highlightCitiesAndZipCodes(getDocumentDescription(documentId)));  // Adding highlighted description.
            details.put("imageUrls", getImageURL(documentId));  // Adding image URLs.
            details.put("highlightedContent", getHighlightedContent(documentId, query));  // Adding highlighted content.
            documentDetails.add(details);  // Adding document details map to the list.
        }

        return documentDetails;  // Return the list of document details.
    }

    // Method for building the inverted index during initialization.
    public void buildInvertedIndex() {
        // Retrieve all documents from MongoDB
        for (RealEstate document : mongoTemplate.findAll(RealEstate.class)) {
            // Extracting information for indexing
            String id = document.getId();  // Extracting document ID.
            String title = document.getTitle();  // Extracting document title.
            String description = document.getDesc();  // Extracting document description.

            // Indexing title
            indexDocument(root, id, title);  // Indexing document title.

            // Indexing description
            indexDocument(root, id, description);  // Indexing document description.
        }
    }

    // Method for indexing a document in the Trie.
    private void indexDocument(TrieNode node, String documentId, String content) {
        // Splitting content into words (you may need to modify this based on your specific requirements)
        String[] words = content.toLowerCase().split("\\s+");  // Splitting content into words.

        for (String word : words) {
            insertWord(node, word, documentId);  // Inserting each word into the Trie.
            searchFrequencyService.incrementSearchFrequency(word);  // Incrementing search frequency for the word.
        }
    }

    // Method for inserting a word into the Trie.
    private void insertWord(TrieNode node, String word, String documentId) {
        TrieNode current = node;

        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());  // Adding a new node if not present.
            current = current.children.get(c);  // Moving to the next node.
        }

        current.documentIds.add(documentId);  // Adding the document ID to the leaf node.
    }

    // Method for getting details of searched documents based on document IDs.
    public List<RealEstate> getSearchedDocumentDetails(String query, Set<String> documentIds) {
        List<RealEstate> documentDetails = new ArrayList<>();  // List to store document details.

        for (String documentId : documentIds) {
            RealEstate document = mongoTemplate.findById(documentId, RealEstate.class);  // Retrieving document from MongoDB.
            documentDetails.add(document);  // Adding document to the list.
        }
        return documentDetails;  // Return the list of document details.
    }

    // Method for getting the title of a document based on document ID.
    public String getDocumentTitle(String documentId) {
        RealEstate document = mongoTemplate.findById(documentId, RealEstate.class);  // Retrieving document from MongoDB.
        return (document != null) ? document.getTitle() : null;  // Returning the document title if present, otherwise null.
    }

    // Method for getting the description of a document based on document ID.
    public String getDocumentDescription(String documentId) {
        RealEstate document = mongoTemplate.findById(documentId, RealEstate.class);  // Retrieving document from MongoDB.
        return (document != null) ? document.getDesc() : null;  // Returning the document description if present, otherwise null.
    }

    // Method for getting the image URLs of a document based on document ID.
    public List<String> getImageURL(String documentId) {
        RealEstate document = mongoTemplate.findById(documentId, RealEstate.class);  // Retrieving document from MongoDB.
        return (document != null) ? document.getImg_urls() : null;  // Returning the image URLs if present, otherwise null.
    }

    // Method for getting the highlighted content of a document based on document ID and query.
    public String getHighlightedContent(String documentId, String query) {
        RealEstate document = mongoTemplate.findById(documentId, RealEstate.class);  // Retrieving document from MongoDB.
        String content = (document != null) ? document.getTitle() : null;  // Extracting document content.

        if (content != null && query != null) {
            // Normalize the content and query to lowercase
            content = content.toLowerCase();
            query = query.toLowerCase();

            // Splitting content into words
            String[] words = content.split("\\s+");

            // Use a StringBuilder for constructing the output text
            StringBuilder outputTextBuilder = new StringBuilder(content);

            // Highlighting the query word in the content
            for (String word : words) {
                if (word.contains(query)) {
                    int startIndex = outputTextBuilder.indexOf(word);
                    int endIndex = startIndex + word.length();

                    outputTextBuilder.replace(startIndex, endIndex,
                            "<span style=\"background-color: yellow;\">" + word + "</span>");  // Highlighting the word.
                }
            }

            return outputTextBuilder.toString();  // Return the highlighted content.
        }

        return "";  // Return an empty string if content or query is null.
    }

    // Method for getting the total number of documents in the database.
    public int getTotalDocuments() {
        return mongoTemplate.findAll(RealEstate.class).size();  // Return the total number of documents.
    }

    // Method for getting all document IDs in the database.
    public List<String> getAllDocumentIds() {
        List<String> documentIds = new ArrayList<>();  // List to store document IDs.

        for (RealEstate document : mongoTemplate.findAll(RealEstate.class)) {
            documentIds.add(document.getId());  // Adding document ID to the list.
        }

        return documentIds;  // Return the list of document IDs.
    }

    // Method for suggesting a corrected query based on the given query and a bag of words.
    public String suggestCorrectedQuery(String query, List<String> bagOfWords) {
        TrieNode current = root;  // Starting at the root of the Trie.
        query = query.toLowerCase().trim().replaceAll("\\s+", " ");  // Normalize the query.

        // If the query is already in the index, no need for correction
        if (search(query).contains(query)) {
            return query;  // Return the original query.
        }

        // Calculate Levenshtein distance and find the closest match in the index
        int minDistance = Integer.MAX_VALUE;
        String closestMatch = query;
        for (String indexedQuery : bagOfWords) {
            int distance = calculateLevenshteinDistance(query, indexedQuery);  // Calculate Levenshtein distance.
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = indexedQuery;  // Update closest match if a closer one is found.
            }
        }

        return closestMatch;  // Return the closest matched query.
    }

    // Method for getting all indexed queries in the Trie.
    private List<String> getAllIndexedQueries() {
        List<String> indexedQueries = new ArrayList<>();  // List to store indexed queries.
        collectIndexedQueries(root, "", indexedQueries);  // Collect indexed queries from the Trie.
        return indexedQueries;  // Return the list of indexed queries.
    }

    // Helper method to recursively collect indexed queries from a TrieNode.
    private void collectIndexedQueries(TrieNode node, String currentQuery, List<String> indexedQueries) {
        if (!node.documentIds.isEmpty()) {
            indexedQueries.add(currentQuery);  // Add the current query if documents are indexed at this node.
        }

        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collectIndexedQueries(entry.getValue(), currentQuery + entry.getKey(), indexedQueries);  // Recursively collect queries.
        }
    }

    // Method for calculating Levenshtein distance between two words.
    private int calculateLevenshteinDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];  // Matrix for dynamic programming.

        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;  // Initializing the first row.
                } else if (j == 0) {
                    dp[i][j] = i;  // Initializing the first column.
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(word1.charAt(i - 1), word2.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);  // Filling the matrix based on previous values.
                }
            }
        }

        return dp[word1.length()][word2.length()];  // Returning the Levenshtein distance.
    }

    // Method for calculating the cost of substitution between two characters.
    private int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;  // Cost is 0 if characters are the same, otherwise 1.
    }

    // Method for finding the minimum value among a set of numbers.
    private int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);  // Using stream to find the minimum value.
    }
}
