package com.shpp.p2p.cs.ahryhorashchenko.assignment17.Tests;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyArrayList;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the testing MyArrayList
 */
public class TestMyArrayList extends TestsMyLinkedList {
    /**
     * Size of iterations for cycle in testing
     */
    private final static int SIZE_FOR_CYCLE_FOR_TESTS = 1000;

    /**
     * Errors for every method of my data structure
     */
    private final static String CONSTRUCTOR_ERROR = "Error: method constructor is going wrong";
    private final static String SET_ERROR = "Error: method set() is going wrong";
    private final static String ADD_ERROR = "Error: method add is going wrong";
    private final static String SIZE_ERROR = "Error: method size is going wrong";
    private final static String REMOVE_WITH_INDEX_ERROR = "Error: method remove(with index) is going wrong";
    private final static String CONTAINS_ERROR = "Error: method contains is going wrong";
    private final static String INDEXOF_ERROR = "Error: method indexOf is going wrong";
    private final static String AT_INDEX = " at index ";

    /**
     * Name of my data structures
     */
    private final static String MY_ARRAY_LIST = "MyArrayList:\n";

    /**
     * Testing the class MyArrayList for main methods
     *
     * @return the results of testing in boolean format if all good true else false
     */
    public boolean testMyArrayList() {
        System.out.println(MY_ARRAY_LIST);

        if (!testArrayConstructorWithParameters()) {
            return false;
        }
        if (!testArrayRemoveAddGetSize()) {
            return false;
        }
        if (!testArraySetContainsIndexOf()) {
            return false;
        }

        System.out.println("\nAll tests with MyArrayList passed successfully\n");
        return true;
    }

    /**
     * Testing constructor with parameters from the MyArrayClass
     *
     * @return true if tests passed successfully and false if no
     */
    private boolean testArrayConstructorWithParameters() {
        MyArrayList<String> firstList = new MyArrayList<>();
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            firstList.add(i + "hello");
        }
        MyArrayList<String> secondList = new MyArrayList<>(firstList);
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            if (!secondList.get(i).equals(firstList.get(i))) {
                System.out.println(CONSTRUCTOR_ERROR);
                return false;
            }
        }
        System.out.println("Testing MyArrayList: testing of \"constructor with parameters\" passed successfully");
        return true;
    }

    /**
     * Testing methods: remove, add, get, size at MyArrayList class
     *
     * @return true if tests passed successfully and false if no
     */
    private boolean testArrayRemoveAddGetSize() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        ArrayList<Integer> javaArrayList = new ArrayList<>();

        /* Testing add, size */
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myArrayList.add(i);
            javaArrayList.add(i);
            if (i != 0) {
                if (!myArrayList.get(i - 1).equals(javaArrayList.get(i - 1))) {
                    System.out.println(ADD_ERROR + AT_INDEX + i);
                    return false;
                }
            }

            if (myArrayList.size() != javaArrayList.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyArrayList: testing of methods: \"add, size of list\" passed successfully");

        /* Testing remove, get, size */
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            if (!myArrayList.remove(0).equals(javaArrayList.remove(0))) {
                System.out.println(REMOVE_WITH_INDEX_ERROR + AT_INDEX + i);
                return false;
            }
            if (myArrayList.size() > 0) {
                if (!myArrayList.get(0).equals(javaArrayList.get(0))) {
                    System.out.println(REMOVE_WITH_INDEX_ERROR + AT_INDEX + i);
                    return false;
                }
            }
            if (myArrayList.size() != javaArrayList.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyArrayList: testing of methods: \"remove, get(with index) and size of list\" " +
                "passed successfully");

        return true;
    }

    /**
     * Testing methods: set, indexOf and contains at myArrayList class
     *
     * @return true if tests passed successfully and false if no
     */
    private boolean testArraySetContainsIndexOf() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        ArrayList<Integer> javaArrayList = new ArrayList<>();

        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myArrayList.add(0, i);
            javaArrayList.add(0, i);
        }
        /* Testing indexOf And Contains */
        Random random = new Random();
        int c = random.nextInt(SIZE_FOR_CYCLE_FOR_TESTS - 1);
        if (!myArrayList.contains(c)) {
            System.out.println(CONTAINS_ERROR);
            return false;
        }

        if (myArrayList.indexOf(c) != SIZE_FOR_CYCLE_FOR_TESTS - c - 1) {
            System.out.println(INDEXOF_ERROR);
            return false;
        }

        if (myArrayList.contains(SIZE_FOR_CYCLE_FOR_TESTS + 1)) {
            System.out.println(CONTAINS_ERROR);
            return false;
        }

        for (int i = 1; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            if (myArrayList.indexOf(i) != javaArrayList.indexOf(i)) {
                System.out.println(INDEXOF_ERROR + AT_INDEX + i);
                return false;
            }
            if (myArrayList.contains(i * -1)) {
                System.out.println(CONTAINS_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyArrayList: testing of methods: \"indexOf " +
                "and contains\" passed successfully");

        /* Testing set, get, size*/
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            for (int j = 0; j < SIZE_FOR_CYCLE_FOR_TESTS; j++) {
                myArrayList.set(j, i);
                javaArrayList.set(j, i);
                if (myArrayList.size() != javaArrayList.size()) {
                    System.out.println(SIZE_ERROR + AT_INDEX + i);
                    return false;
                }
                if (j != 0) {
                    if (!myArrayList.get(j - 1).equals(javaArrayList.get(j - 1))) {
                        System.out.println(SET_ERROR + AT_INDEX + i);
                        return false;
                    }
                }
                if (j != myArrayList.size() - 1) {
                    if (!myArrayList.get(j + 1).equals(javaArrayList.get(j + 1))) {
                        System.out.println(SET_ERROR + AT_INDEX + i);
                        return false;
                    }
                }

            }
            for (int j = 0; j < SIZE_FOR_CYCLE_FOR_TESTS; j++) {
                if (!myArrayList.get(j).equals(javaArrayList.get(j))) {
                    System.out.println(SET_ERROR + AT_INDEX + i);
                    return false;
                }
            }
        }
        System.out.println("Testing MyArrayList: testing of methods: \"set, " +
                "size and get(with index)\" passed successfully");
        return true;
    }
}
