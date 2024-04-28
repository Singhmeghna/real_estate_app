// Package declaration for the WordCompletion class
package com.accgroupproject.realestate.WordCompletion;


// Import statements for Spring Framework and other required libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import com.accgroupproject.realestate.RealEstate;

// Import statements for collections and lists
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

// Service annotation to indicate that this class is a Spring service
@Service
public class WordCompletion {


// Autowired constructor to inject the MongoTemplate bean
	private final MongoTemplate mongoTemplate;
	private TreeMap<String, String> wordMap; 


	@Autowired
	public WordCompletion(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
		this.wordMap = new TreeMap<>();
		performBuildWordMap();
	}

// Method to build the word map from MongoDB documents
	public void performBuildWordMap() {
		// Retrieve all documents from MongoDB
		for (RealEstate document : mongoTemplate.findAll(RealEstate.class)) {
			// Extracting information for indexing
			String title = document.getTitle();
			String description = document.getDesc();
			// Adding words to the TreeMap for word completion
			addWordsToMap(title);
			addWordsToMap(description);
		}
	}
 // Method to add words to the word map
	private void addWordsToMap(String content) {
		String[] wordsArray = content.toLowerCase().split("\\s+");
		for (String word : wordsArray) {
			wordMap.put(word, word);
		}
	}
// Method for auto-completion given a prefix
	public List<String> autoComplete(String prefix) {
		return getWordCompletions(prefix.toLowerCase());
	}

// Method to retrieve word completions for a given prefix
	private List<String> getWordCompletions(String prefix) {
		List<String> completions = new ArrayList<>();
		for (String key : wordMap.tailMap(prefix).keySet()) {
			if (key.startsWith(prefix)) {
				completions.add(wordMap.get(key));
			} else {
				break; // No need to continue once the prefix doesn't match
			}
		}
		return completions;
	}
}
