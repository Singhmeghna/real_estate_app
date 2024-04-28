// Package declaration for the TrieNode class
package com.accgroupproject.realestate.services;

// Import statements for Java utility classes
import java.util.*;

// Class representing a node in a Trie data structure
class TrieNode {

// Map to store child nodes with characters as keys
	Map<Character, TrieNode> children;

// Set to store document IDs associated with the current node
	Set<String> documentIds;

// Constructor to initialize TrieNode with an empty map and set
	TrieNode() {
		this.children = new HashMap<>();
		this.documentIds = new HashSet<>();
	}
}