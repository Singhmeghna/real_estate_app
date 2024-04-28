package com.accgroupproject.realestate.util;  // Package declaration for the KMPAlgorithm class.

public class KMPAlgorithm {

    // Method to compute the Longest Prefix Suffix (LPS) array for the given pattern.
    public static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int length = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // Method to perform Knuth-Morris-Pratt (KMP) string search algorithm.
    public static int kmpSearch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        int[] lps = computeLPSArray(pattern);

        int i = 0, j = 0;

        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == m) {
                return i - j; // Pattern found at index i-j
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return -1; // Pattern not found in text
    }

    // Main method for testing the KMP algorithm with a sample word and pattern.
    public static void main(String[] args) {
        String word = "628 Fleet St, Suite 920, toronto , ON M5V 1A8";
        String pattern = "toronto";

        int result = kmpSearch(word.toLowerCase(), pattern.toLowerCase());

        if (result != -1) {
            System.out.println("Pattern found at index " + result);
        } else {
            System.out.println("Pattern not found in the word.");
        }
    }
}
