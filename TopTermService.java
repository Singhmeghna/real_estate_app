// Package declaration for the TopTermService class
package com.accgroupproject.realestate.topterms;

// Import statements for Java utility classes and Spring Framework dependencies
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// Service class for managing top terms
@Service
public class TopTermService {
    // Autowired annotation to inject the TopTermRepository bean
    @Autowired
    private TopTermRepository topTermRepository;

    // Method to add or update a term in the repository
    public void addOrUpdateTerm(String term, String country) {
        // Check if the term already exists in the repository
        TopTerm existingTerm = topTermRepository.findByKey(term, country);

        // If the term doesn't exist, add a new entry; otherwise, increment the count
        if (existingTerm == null) {
            TopTerm newTerm = new TopTerm();
            newTerm.setKey(term);
            newTerm.setCountry(country);
            newTerm.setCount(1);
            topTermRepository.save(newTerm);
        } else {
            existingTerm.setCount(existingTerm.getCount() + 1);
            topTermRepository.save(existingTerm);
        }
    }

    // Method to get the top terms for a specific country
    public List<MaxHeap.Item> getTopTerms(String country) {
        // Get the top 10 terms based on count and specified sorting order
        Sort sort = Sort.by(Sort.Order.desc("count"));
        Pageable pageable = PageRequest.of(0, 10, sort);

        // Retrieve the top terms from the repository
        List<TopTerm> topTerms = topTermRepository.findAll(pageable).getContent();

        // Filter the top terms based on the specified country
        List<TopTerm> filteredTopTerms = new ArrayList<TopTerm>();
        for (TopTerm topTerm : topTerms) {
            if (country.toLowerCase().equals(topTerm.getCountry())) {
                filteredTopTerms.add(topTerm);
            }
        }

        // Create a max heap and insert elements from the filtered top terms
        MaxHeap maxHeap = new MaxHeap();
        for (TopTerm topTerm : filteredTopTerms) {
            maxHeap.push(new MaxHeap.Item(topTerm.getId(), topTerm.getKey(), topTerm.getCount(), topTerm.getCountry()));
        }

        // Pop elements from the max heap and return the result
        List<MaxHeap.Item> result = new ArrayList<>();
        MaxHeap.Item popped;
        while ((popped = maxHeap.pop()) != null) {
            result.add(popped);
        }

        return result;
    }
}
