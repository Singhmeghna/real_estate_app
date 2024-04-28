package com.accgroupproject.realestate.topterms;  // Package declaration for the MaxHeap class.

import java.util.ArrayList;  // Importing ArrayList for using dynamic arrays.
import java.util.List;  // Importing List for defining the heap structure.

public class MaxHeap {

    private List<Item> heap;  // Declaring a list to store heap items.

    // Constructor for initializing an empty heap.
    public MaxHeap() {
        this.heap = new ArrayList<>();
    }

    // Method to add an item to the heap and maintain the heap property.
    public void push(Item item) {
        heap.add(item);
        heapifyUp();
    }

    // Method to remove and return the root item from the heap and maintain the heap property.
    public Item pop() {
        if (heap.isEmpty()) {
            return null;
        }

        if (heap.size() == 1) {
            return heap.remove(0);
        }

        Item root = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        heapifyDown();
        return root;
    }

    // Helper method to heapify the heap upwards.
    private void heapifyUp() {
        int currentIndex = heap.size() - 1;
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            if (heap.get(currentIndex).getCount() > heap.get(parentIndex).getCount()) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    // Helper method to heapify the heap downwards.
    private void heapifyDown() {
        int currentIndex = 0;
        while (true) {
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;
            int largestIndex = currentIndex;

            if (leftChildIndex < heap.size()
                    && heap.get(leftChildIndex).getCount() > heap.get(largestIndex).getCount()) {
                largestIndex = leftChildIndex;
            }

            if (rightChildIndex < heap.size()
                    && heap.get(rightChildIndex).getCount() > heap.get(largestIndex).getCount()) {
                largestIndex = rightChildIndex;
            }

            if (largestIndex != currentIndex) {
                swap(currentIndex, largestIndex);
                currentIndex = largestIndex;
            } else {
                break;
            }
        }
    }

    // Helper method to swap two items in the heap.
    private void swap(int i, int j) {
        Item temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Static nested class representing an item in the heap.
    public static class Item {
        private String id;
        private String key;
        private String country;
        private int count;

        // Constructor for creating an item with specified values.
        public Item(String id, String key, int count, String country) {
            this.id = id;
            this.key = key;
            this.count = count;
            this.country = country;
        }

        // Getters and setters for the item properties.
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public String getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        // toString method for converting the item to a string representation.
        @Override
        public String toString() {
            return "Item{" + "id='" + id + '\'' + ", key='" + key + '\'' + ", count=" + count + '}';
        }
    }
}
