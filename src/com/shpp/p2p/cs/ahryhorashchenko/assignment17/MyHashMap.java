package com.shpp.p2p.cs.ahryhorashchenko.assignment17;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Data structure for working with the Key-Value
 *
 * @param <Key>   Type of object for the key
 * @param <Value> Type of object for the value
 */
public class MyHashMap<Key, Value> {
    /**
     * Default size of array for MeHshMap
     */
    private final static int DEFAULT_SIZE_OF_ARRAY = 16;

    /**
     * Load factor for hash map this is the best option for hashmap operation
     */
    private final static double LOAD_FACTOR = 0.75;

    /**
     * Array with hash nodes
     */
    private HashNode<Key, Value>[] elementsTable;

    /**
     * Quantity of elements which entered to MyHashMap
     */
    private int size;

    private boolean reducingForRemove = false;

    /**
     * Constructor for MyHashMap in which create an array for entering objects
     */
    public MyHashMap() {
        this.elementsTable = new HashNode[DEFAULT_SIZE_OF_ARRAY];
    }

    /**
     * Clear the MyHashMap and resets size
     */
    public void clear() {
        this.elementsTable = new HashNode[DEFAULT_SIZE_OF_ARRAY];
        size = 0;
    }

    /**
     * Put Key and Value to the array
     *
     * @param key   the key on which the value will be located
     * @param value value of this key
     * @return value which was entered
     */
    public Value put(Key key, Value value) {
        double currentLoadFactor = (double) size/ elementsTable.length;
        if (currentLoadFactor >= LOAD_FACTOR) {
            increaseSizeArray();
        }
        HashNode<Key, Value> newHashNode = new HashNode<>(key, value, null);

        /* No items with this index */
        if (elementsTable[getIndex(key)] == null) {
            elementsTable[getIndex(key)] = newHashNode;
        } else { /* Item with this index is present and another key */
            HashNode<Key, Value> oldHashNode = elementsTable[getIndex(key)];
            if (Objects.equals(oldHashNode.key,key)) {
                value = oldHashNode.value;
                newHashNode.next = oldHashNode.next;
                elementsTable[getIndex(key)] = newHashNode;
                return value;
            }
            while (oldHashNode.next != null) {
                if(Objects.equals(oldHashNode.next.key,key)){
                    value = oldHashNode.next.value;
                    newHashNode.next = oldHashNode.next.next;
                    oldHashNode.next = newHashNode;
                    return value;
                }
                oldHashNode = oldHashNode.next;
            }

            oldHashNode.next = newHashNode;
        }
        size++;
        return value;
    }

    /**
     * If all cells are used the array is resized
     */
    private void increaseSizeArray() {
        HashNode<Key, Value>[] oldArray = elementsTable;
        elementsTable = new HashNode[elementsTable.length * 2];
        size = 0;
        transfer(oldArray);
    }

    /**
     * Creates new elements from old ones and changes
     * indexes to new ones and adds them to the new array
     *
     * @param oldArray old Array from which takes elements and put to new
     */
    private void transfer(HashNode<Key, Value>[] oldArray) {
        for(HashNode<Key, Value> node: oldArray){
            while (node != null){
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    /**
     * Key set for using at the programs
     *
     * @return Set with all entered keys before
     */
    public Set<Key> keySet() {
        Set<Key> keys = new LinkedHashSet<>();
        for (HashNode<Key, Value> node : elementsTable) {
            while (node != null) {
                keys.add(node.key);
                node = node.next;
            }
        }
        return keys;
    }

    /**
     * Return the value of key if key present and null if key isn`t present
     *
     * @param key the key which corresponds to some value
     * @return value of the key
     */
    public Value get(Key key) {
        HashNode<Key, Value> currentNode = elementsTable[getIndex(key)];
        while (currentNode != null) {
            if (Objects.equals(currentNode.key, key)) {
                return currentNode.value;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    /**
     * Remove object by the key
     *
     * @param key key which need to delete
     * @return this key value and null if this key isn`t present at the array
     */
    public Value remove(Key key) {
        HashNode<Key, Value> currentNode = elementsTable[getIndex(key)];
        Value value;
        if(Objects.equals(currentNode.key,key)){
            value = currentNode.value;
            elementsTable[getIndex(key)] = currentNode.next;
        }else {
            while (currentNode.next != null ){
                if (Objects.equals(currentNode.next.key,key)) {
                    break;
                }
                currentNode = currentNode.next;
            }

            if (currentNode.next == null) {
                return null;
            }

            value = currentNode.next.value;

            currentNode.next = currentNode.next.next;
        }
        checkSizeForReducing();
        size--;
        return value;
    }

    /**
     * Checks the conformity of the number of elements to
     * the size if the elements are 25 percent or less then the size of the array decreases
     */
    private void checkSizeForReducing() {
        if(elementsTable.length > 100){
            double indexOfLoadForUnRecize = 0.25;
            double indexOfLoad = (double) size / elementsTable.length;
            if(indexOfLoad <= indexOfLoadForUnRecize){
                reduceSizeArray();
            }
        }
    }

    /**
     * Reduce size of the array for elements in 2 times
     */
    private void reduceSizeArray(){
        HashNode<Key, Value>[] oldArray = elementsTable;
        elementsTable = new HashNode[elementsTable.length / 2];
        size = 1;
        transfer(oldArray);
    }

    /**
     * Count the index for the entered key and return it
     *
     * @param key key which index need to count
     * @return index of the key
     */
    private int getIndex(Key key) {
        return key == null ? 0 : key.hashCode() & (elementsTable.length - 1);
    }

    /**
     * Looking at the emptiness of the program if it is empty return true if no return false
     *
     * @return true if array empty and false if not empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Looking for the all keys and return true if any key == entered key
     * or return false if no one key != entered key
     *
     * @param key entered key which need to find
     * @return true if key present and false if not
     */
    public boolean containsKey(Key key) {
        if(get(key) != null){
            return true;
        }
        return false;
    }

    /**
     * Make from the MyHashMap the string line and return it
     *
     * @return String line with the element of MyHashMap
     */
    public String toString() {
        StringBuilder arrayString = new StringBuilder();
        arrayString.append("{");
        int index = 0;
        int i = 0;
        while (index < elementsTable.length) {
            HashNode<Key, Value> currentNode = elementsTable[index];
            if (currentNode != null) {
                arrayString.append(currentNode.key + "=" + currentNode.value);
                i++;
                if (i != size) {
                    arrayString.append(", ");
                }
                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                    while (currentNode != null) {
                        arrayString.append(currentNode.key + "=" + currentNode.value);
                        i++;
                        if (i != size) {
                            arrayString.append(", ");
                        }
                        currentNode = currentNode.next;
                    }
                }
            }
            index++;
        }
        arrayString.append("}");
        return arrayString.toString();
    }

    /**
     * Return the int variable in which stored value of
     * the size of elements entered to the program
     *
     * @return the size of the program
     */
    public int size() {
        return size;
    }


    /**
     * A class to represent a single-direction node for working with MyHashMap
     *
     * @param <Key>   Type of object for the key
     * @param <Value> Type of object for the value
     */
    private static class HashNode<Key, Value> {

        /**
         * The key on which the value will be located
         */
        Key key;

        /**
         * The value for the key
         */
        Value value;

        /**
         * Next node of the current node
         */
        HashNode<Key, Value> next;

        /**
         * Constructor for the HashNode
         *
         * @param key       key which using for the HashNode
         * @param value     value which tied for the key
         * @param next      next HashNode of this HashNode
         */
        HashNode(Key key, Value value, HashNode<Key, Value> next) {
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }
}
