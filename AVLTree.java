package com.accgroupproject.realestate.pageranking;  // Package declaration for the AVLTree class.

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.accgroupproject.realestate.RealEstate;  // Importing RealEstate class from a different package.

class AVLNode {
    String title;
    int rank;
    AVLNode left, right;
    int height;
    int frequency;  // Add frequency to keep track of occurrences

    AVLNode(String title, int rank) {
        this.title = title;
        this.rank = rank;
        this.height = 1;
        this.frequency = 1;
    }
}

public class AVLTree {
    AVLNode root;

    // Function to get height of a node
    private int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Function to get the balance factor of a node
    private int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    // Function to right rotate a subtree rooted with y
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Function to left rotate a subtree rooted with x
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Get the node with the minimum value in a subtree
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // Insert a node with title and rank into the AVL tree
    public AVLNode insert(AVLNode node, String title, int rank) {
        // Perform normal BST insertion
        if (node == null)
            return new AVLNode(title, rank);

        if (title.compareTo(node.title) < 0)
            node.left = insert(node.left, title, rank);
        else if (title.compareTo(node.title) > 0)
            node.right = insert(node.right, title, rank);
        else
            node.frequency++;  // Increment frequency if title already exists

        // Update height of current node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node to check whether it became unbalanced
        int balance = getBalance(node);

        // If the node is unbalanced, there are four cases

        // Left Left Case
        if (balance > 1 && title.compareTo(node.left.title) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && title.compareTo(node.right.title) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && title.compareTo(node.left.title) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && title.compareTo(node.right.title) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Return the (unchanged) node pointer
        return node;
    }

    // Recursive function to perform pagerank and update ranks in the AVL tree
    private AVLNode performPagerank(AVLNode node, int rank) {
        if (node != null) {
            // Perform pagerank on the left subtree
            node.left = performPagerank(node.left, rank);

            // Update the rank of the current node
            node.rank = rank++;

            // Perform pagerank on the right subtree
            node.right = performPagerank(node.right, rank);
        }
        return node;
    }

    // Function to initiate pagerank and update ranks in the AVL tree
    public void initiatePagerank() {
        performPagerank(root, 1);
    }

    // Recursive function to search for nodes containing the keyword
    private void searchKeyword(AVLNode node, List<AVLNode> result, String keyword) {
        if (node != null) {
            searchKeyword(node.left, result, keyword);

            // Check if the title contains the keyword
            if (node.title.toLowerCase().contains(keyword)) {
                result.add(node);
            }

            searchKeyword(node.right, result, keyword);
        }
    }

    // Function to get nodes containing the keyword
    public List<AVLNode> getNodesWithKeyword(String keyword) {
        List<AVLNode> result = new ArrayList<>();
        searchKeyword(root, result, keyword);
        return result;
    }

    private void inorder(AVLNode node, List<AVLNode> sortedList) {
        if (node != null) {
            inorder(node.left, sortedList);
            sortedList.add(node);
            inorder(node.right, sortedList);
        }
    }

    // Function to get the sorted list of nodes based on ranks and total frequency
    public List<AVLNode> getSortedNodes() {
        List<AVLNode> sortedList = new ArrayList<>();
        inorder(root, sortedList);

        // Sort based on rank and total frequency
        sortedList.sort(Comparator.comparingInt((AVLNode node) -> node.rank).reversed()
                .thenComparingInt(node -> node.frequency).reversed());

        return sortedList;
    }

    public List<RealEstate> getRealEstateWithMatchingTitle(List<AVLNode> sortedNodes, List<RealEstate> mRealEstate, String country) {
        List<RealEstate> mRealEstateOutput = new ArrayList<>();
        for (AVLNode node : sortedNodes) {
            for (RealEstate mRE : mRealEstate) {
                String matcher = mRE.getTitle();
                if (country.toLowerCase().equals("uae")) {
                    matcher = mRE.getDesc();
                }
                if (node.title.toLowerCase().equals(matcher.toLowerCase())) {
                    // System.out.println("Rank: " + node.rank + ", Title: " + node.title);
                    mRealEstateOutput.add(mRE);
                    break;  // Break to avoid adding the same mRE multiple times if it has duplicate titles
                }
            }
        }

        return mRealEstateOutput;
    }

    public List<RealEstate> performPageRanking(List<RealEstate> mRealEstate, String query, String country) {
        AVLTree avlTree = new AVLTree();
        List<AVLNode> nodes = new ArrayList<AVLNode>();
        for (RealEstate mRE : mRealEstate) {

            if (country.toLowerCase().equals("uae")) {
                nodes.add(new AVLNode(mRE.getDesc().toLowerCase(), 0));
            } else {
                nodes.add(new AVLNode(mRE.getTitle(), 0));

            }
        }

        // Insert nodes into AVL tree
        int rank = 1;
        for (AVLNode node : nodes) {
                avlTree.root = avlTree.insert(avlTree.root, node.title, rank++);
        }

        // Initiate pagerank
        avlTree.initiatePagerank();

        // Get nodes containing the keyword "software engineering"
        List<AVLNode> softwareEngineeringNodes = avlTree.getNodesWithKeyword(query);

        // Print sorted nodes containing the keyword "software engineering"
        List<RealEstate> mREOutput = avlTree.getRealEstateWithMatchingTitle(softwareEngineeringNodes, mRealEstate, country);
        
        
        return mREOutput;

    }
}
