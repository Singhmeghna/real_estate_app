package com.accgroupproject.realestate;  // Package declaration for the RealEstate controller.

import org.bson.Document;  // Importing Document for MongoDB documents.
import org.springframework.beans.factory.annotation.Autowired;  // Importing Autowired annotation for dependency injection.
import org.springframework.http.ResponseEntity;  // Importing ResponseEntity for HTTP responses.
import org.springframework.ui.Model;  // Importing Model for MVC model attributes.
import org.springframework.web.bind.annotation.GetMapping;  // Importing GetMapping for handling GET requests.
import org.springframework.web.bind.annotation.PathVariable;  // Importing PathVariable for extracting values from the URI.
import org.springframework.web.bind.annotation.PostMapping;  // Importing PostMapping for handling POST requests.
import org.springframework.web.bind.annotation.PutMapping;  // Importing PutMapping for handling PUT requests.
import org.springframework.web.bind.annotation.RequestBody;  // Importing RequestBody for extracting request body content.
import org.springframework.web.bind.annotation.RequestMapping;  // Importing RequestMapping for defining request mappings.
import org.springframework.web.bind.annotation.RequestParam;  // Importing RequestParam for extracting query parameters.
import org.springframework.web.bind.annotation.ResponseBody;  // Importing ResponseBody for indicating a method return value should be bound to the web response body.
import org.springframework.web.bind.annotation.RestController;  // Importing RestController for combining @Controller and @ResponseBody annotations.

import com.accgroupproject.realestate.WordCompletion.WordCompletion;  // Importing WordCompletion for word completion functionality.
import com.accgroupproject.realestate.pageranking.AVLTree;  // Importing AVLTree for page ranking.
import com.accgroupproject.realestate.patternmatching.UniquePatternMatching;  // Importing UniquePatternMatching for pattern matching.
import com.accgroupproject.realestate.rbt.RedBlackTree;  // Importing RedBlackTree for Red-Black Tree data structure.
import com.accgroupproject.realestate.searchingfrequency.SearchFrequencyService;  // Importing SearchFrequencyService for search frequency tracking.
import com.accgroupproject.realestate.services.InvertedIndexService;  // Importing InvertedIndexService for inverted index operations.
import com.accgroupproject.realestate.services.RealEstateService;  // Importing RealEstateService for real estate-related operations.
import com.accgroupproject.realestate.services.RealEstateService.TypeCount;  // Importing TypeCount for type counting operations.
import com.accgroupproject.realestate.sorter.RealEstateSorter;  // Importing RealEstateSorter for sorting real estate entities.
import com.accgroupproject.realestate.topterms.MaxHeap.Item;  // Importing Item for MaxHeap items.
import com.accgroupproject.realestate.topterms.TopTermService;  // Importing TopTermService for top terms functionality.
import com.accgroupproject.realestate.util.Utilities;  // Importing Utilities for utility functions.

import java.net.MalformedURLException;  // Importing MalformedURLException for URL-related exceptions.
import java.net.URL;  // Importing URL for handling URLs.
import java.util.ArrayList;  // Importing ArrayList for dynamic arrays.
import java.util.Collections;  // Importing Collections for utility methods on collections.
import java.util.HashMap;  // Importing HashMap for key-value pairs.
import java.util.HashSet;  // Importing HashSet for a collection of unique elements.
import java.util.Hashtable;  // Importing Hashtable for key-value pairs.
import java.util.LinkedHashSet;  // Importing LinkedHashSet for a collection of unique elements with predictable iteration order.
import java.util.List;  // Importing List for handling collections.
import java.util.Map;  // Importing Map for key-value pairs.
import java.util.Optional;  // Importing Optional for handling optional values.
import java.util.Set;  // Importing Set for a collection of unique elements.
import java.util.stream.Collectors;  // Importing Collectors for operations on streams.

@RestController  // Indicates that this class is a REST controller.
@RequestMapping("/api/real-estate")  // Base mapping for the URI.
public class RealEstateController {

    @Autowired
    private final RealEstateRepository realEstateRepository;  // Dependency injection for RealEstateRepository.

    @Autowired
    private InvertedIndexService invertedIndexService;  // Dependency injection for InvertedIndexService.

    @Autowired
    private WordCompletion wordCompletionService;  // Dependency injection for WordCompletion service.

    @Autowired
    private SearchFrequencyService searchFrequencyService;  // Dependency injection for SearchFrequencyService.

    @Autowired
    private RealEstateService realEstateService;  // Dependency injection for RealEstateService.

    @Autowired
    private TopTermService topTermService;  // Dependency injection for TopTermService.

    @Autowired
    public RealEstateController(RealEstateRepository realEstateRepository, InvertedIndexService invertedIndexService,
                                WordCompletion wordCompletionService, SearchFrequencyService searchFrequencyService) {
        this.realEstateRepository = realEstateRepository;
        this.invertedIndexService = invertedIndexService;
        this.wordCompletionService = wordCompletionService;
        this.searchFrequencyService = searchFrequencyService;
    }

    @GetMapping("/top-10")
    public ResponseEntity<List<RealEstate>> getTop10Documents(@RequestParam String country) {
        List<RealEstate> top10Documents = realEstateRepository.findTop10ByCountryOrderByNumberOfVisitsDesc(country);

        return ResponseEntity.ok().body(top10Documents);
    }

    @PutMapping("/increment-visits/{id}")
    public ResponseEntity<String> incrementNumberOfVisits(@PathVariable String id) {
        realEstateService.incrementNumberOfVisits(id);
        return ResponseEntity.ok().body("Number of visits incremented for ID: " + id);
    }

    @GetMapping("/rankPages")
    public List<RealEstate> rankPages(@RequestParam(required = false) String searchKeyword) {
        Set<String> matchingDocuments = invertedIndexService.search(searchKeyword);
        List<RealEstate> mRealEstates = new ArrayList<RealEstate>();
        mRealEstates.addAll(invertedIndexService.getSearchedDocumentDetails(searchKeyword, matchingDocuments));
        List<RealEstate> rankedDocuments = new AVLTree().performPageRanking(mRealEstates, searchKeyword, "canada");
        return rankedDocuments;
    }

    @GetMapping("/search-frequency")
    public ResponseEntity<Map<String, Integer>> getSearchFrequency() {
        Map<String, Integer> searchFrequency = searchFrequencyService.getSearchFrequency();
        return ResponseEntity.ok().body(searchFrequency);
    }

    @GetMapping("/complete/{prefix}")
    public List<String> autoComplete(@PathVariable String prefix) {
        return wordCompletionService.autoComplete(prefix);
    }

    @GetMapping("/all")
    public List<RealEstate> getAllRealEstate() {
        return realEstateRepository.findAll();
    }

    @GetMapping("/getTotalPropertyCount")
    public Map<String, Long> getTotalPropertyCount() {
        Map<String, Long> obj = new HashMap<String, Long>();
        obj.put("individual_house", realEstateService.getCountByType("individual_house"));
        obj.put("townhouse", realEstateService.getCountByType("townhouse"));
        obj.put("apartment", realEstateService.getCountByType("ap
