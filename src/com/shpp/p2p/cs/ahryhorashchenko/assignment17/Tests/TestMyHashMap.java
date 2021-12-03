package com.shpp.p2p.cs.ahryhorashchenko.assignment17.Tests;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyHashMap;

import java.util.HashMap;
import java.util.Set;

/**
 * Class for the testing MyHashMap
 */
public class TestMyHashMap {
    /**
     * Size of iterations for cycle in testing
     */
    private final static int SIZE_FOR_CYCLE_FOR_TESTS = 1000;

    /**
     * Errors for every method of my data structure
     */
    private final static String REMOVE_ERROR = "Error: method remove is going wrong";
    private static final String PUT_ERROR = "Error: method put is going wrong";
    private static final String KEY_SET_ERROR = "Error: method keySet is going wrong";
    private final static String GET_ERROR = "Error: method get with index is going wrong";
    private final static String SIZE_ERROR = "Error: method size is going wrong";
    private final static String CONTAINS_ERROR = "Error: method contains is going wrong";
    private final static String AT_INDEX = " at index ";

    /**
     * Name of my data structures
     */
    private final static String MY_HASH_MAP = "MyHashMap:\n";

    /**
     * Testing MyHashMap for main methods of this data structure
     *
     * @return true if tests passed successfully and false if no
     */
    public boolean testMyHashMap() {
        System.out.println(MY_HASH_MAP);

        if (!testMyHashPutSizeContains()) {
            return false;
        }
        if (!testMyHashRemoveGetKeySet()) {
            return false;
        }
        System.out.println("\nAll tests with MyHashMap passed successfully\n");
        return true;
    }

    /**
     * Testing MyHashMap for methods: put, size, contains
     *
     * @return true if tests passed successfully and false if no
     */
    private boolean testMyHashPutSizeContains() {
        MyHashMap<Integer, String> myHashMap = new MyHashMap<>();
        HashMap<Integer, String> javaHashMap = new HashMap<>();
        String word = "hello";
        for (Integer i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myHashMap.put(i, word + i);
            javaHashMap.put(i, word + i);
            if (myHashMap.size() != javaHashMap.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }

            if (!myHashMap.containsKey(i) && !javaHashMap.containsKey(i)) {
                System.out.println(CONTAINS_ERROR + AT_INDEX + i);
                return false;
            }

        }
        System.out.println("Testing MyHashMap: testing of method: \"put, size, contains\" passed successfully");
        return true;
    }

    /**
     * Testing MyHashMap for methods: remove, get, keySet
     *
     * @return true if tests passed successfully and false if no
     */
    private boolean testMyHashRemoveGetKeySet() {
        MyHashMap<Integer, String> myHashMap = new MyHashMap<>();
        HashMap<Integer, String> javaHashMap = new HashMap<>();
        String word = "hello";
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myHashMap.put(i, word + i);
            javaHashMap.put(i, word + i);
        }
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myHashMap.put(i, word + i);
            javaHashMap.put(i, word + i);
        }
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myHashMap.put(i, word + i);
            javaHashMap.put(i, word + i);
        }
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myHashMap.put(i, word + word + i);
            javaHashMap.put(i, word + word + i);
            if (!myHashMap.remove(i).equals(javaHashMap.remove(i))) {
                System.out.println(REMOVE_ERROR + AT_INDEX + i);
                return false;
            }
            if (myHashMap.size() != javaHashMap.size()) {
                System.out.println(myHashMap.size());
                System.out.println(javaHashMap.size());
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyHashMap: testing of method: \"remove\" passed successfully");

        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myHashMap.put(i, word + i);
            javaHashMap.put(i, word + i);
        }

        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            if (!myHashMap.get(i).equals(javaHashMap.get(i))) {
                if (!myHashMap.remove(i).equals(javaHashMap.remove(i))) {
                    System.out.println(GET_ERROR + AT_INDEX + i);
                    return false;
                }
                System.out.println(PUT_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyHashMap: testing of method: \"get\" passed successfully");

        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            Set st = myHashMap.keySet();
            if (!st.contains(i)) {
                System.out.println(KEY_SET_ERROR);
                return false;
            }
        }
        System.out.println("Testing MyHashMap: testing of method: \"keySet\" passed successfully");

        return true;
    }
}
