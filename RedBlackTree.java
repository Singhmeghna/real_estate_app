package com.accgroupproject.realestate.rbt;

import java.util.HashMap;
import java.util.Map;

enum Color {
    RED, BLACK
}

class Node {
    String key;
    long data;
    Node parent;
    Node left;
    Node right;
    Color color;

    public Node(String key, long data) {
        this.key = key;
        this.data = data;
        this.color = Color.RED;
        this.parent = null;
        this.left = null;
        this.right = null;
    }
}

public class RedBlackTree {
    private Node root;
    private Node nil; // Sentinel node

    public RedBlackTree() {
        nil = new Node("", 0);
        nil.color = Color.BLACK;
        root = nil;
    }

    // Left Rotate
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != nil) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == nil) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    // Right Rotate
    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;

        if (x.right != nil) {
            x.right.parent = y;
        }

        x.parent = y.parent;

        if (y.parent == nil) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }

        x.right = y;
        y.parent = x;
    }

    // Insert a value into the tree
    public void insert(String key, long value) {
        Node newNode = new Node(key, value);
        Node y = nil;
        Node x = root;

        while (x != nil) {
            y = x;
            if (newNode.key.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        newNode.parent = y;

        if (y == nil) {
            root = newNode;
        } else if (newNode.key.compareTo(y.key) < 0) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }

        newNode.left = nil;
        newNode.right = nil;
        newNode.color = Color.RED;

        insertFixup(newNode);
    }

    private void insertFixup(Node z) {
        while (z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.left;
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }

        root.color = Color.BLACK;
    }

    // In-order traversal
    public Map<String, Long> inOrderTraversal() {
        Map<String, Long> result = new HashMap<>();
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(Node node, Map<String, Long> result) {
        if (node != nil) {
            inOrderTraversal(node.left, result);
            result.put(node.key, node.data);
            inOrderTraversal(node.right, result);
        }
    }

//    public static void main(String[] args) {
//        RedBlackTree rbTree = new RedBlackTree();
//        Map<String, Long> websiteUrlCountMap = new HashMap<>();
//        websiteUrlCountMap.put("sharjah.dubizzle.com", 10L);
//        websiteUrlCountMap.put("dubai.dubizzle.com", 24L);
//        websiteUrlCountMap.put("www.zumper.com", 12L);
//        websiteUrlCountMap.put("www.rentcafe.com", 15L);
//        websiteUrlCountMap.put("abudhabi.dubizzle.com", 8L);
//        Map<String, Long> result = null;
//        for (Map.Entry<String, Long> entry : websiteUrlCountMap.entrySet()) {
//            rbTree.insert(entry.getKey(), entry.getValue());
//            result = rbTree.inOrderTraversal();
//        }
//        // System.out.println(result);
//    }
}
