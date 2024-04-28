package com.accgroupproject.realestate.sorter;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.accgroupproject.realestate.RealEstate;

// RealEstateSorter class responsible for sorting a list of RealEstate objects

public class RealEstateSorter {

// Method to remove non-numeric characters from a price string

    public String removeNonNumeric(String price) {
        try {

// Attempt to remove non-numeric characters from the price
            price = price.replaceAll("[^0-9]", "");
        } catch (Exception e) {

// Handle any exceptions by setting the price to an empty string
            price = "";
        }

// If the resulting price is empty, generate a random price
        if (price.isEmpty()) {
            int randomPrice = new Random().nextInt(13686) + 2300;
            price = String.valueOf(randomPrice);
        }

        return price;
    }

 // Method to sort a list of RealEstate objects based on their prices
    public List<RealEstate> sortRealEstateList(List<RealEstate> realEstateList, boolean ascending) {
// Comparator based on the numeric values of prices after removing non-numeric characters
        Comparator<RealEstate> comparator = Comparator
                .comparingLong(re -> Long.parseLong(removeNonNumeric(re.getPrice())));

// If sorting in descending order, reverse the comparator
        if (!ascending) {
            comparator = comparator.reversed();
        }

// Sort the list using the comparator and convert it to a new list
        List<RealEstate> sortedList = realEstateList.stream().sorted(comparator).toList();

        return sortedList;
    }
}
