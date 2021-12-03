package com.shpp.p2p.cs.ahryhorashchenko.assignment17.Tests;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyLinkedList;

import java.util.*;

/**
 * Class for the testing MyLinkedList
 */
public class TestsMyLinkedList {
    /**
     * Size of iterations for cycle in testing
     */
    private final static int SIZE_FOR_CYCLE_FOR_TESTS = 1000;

    /**
     * Errors for every method of my data structure
     */
    private final static String SIZE_ERROR = "Error: method size is going wrong";
    private final static String REMOVE_LAST_ERROR = "Error: method remove last is going wrong";
    private final static String REMOVE_FIRST_ERROR = "Error: method remove first is going wrong";
    private final static String GET_LAST_ERROR = "Error: method get last is going wrong";
    private final static String ADD_WITH_INDEX_ERROR = "Error: method add with index is going wrong";
    private final static String ADD_FIRST_ERROR = "Error: method add first is going wrong";
    private final static String ADD_LAST_ERROR = "Error: method add last is going wrong";
    private final static String CONTAINS_ERROR = "Error: method contains is going wrong";
    private final static String INDEXOF_ERROR = "Error: method indexOf is going wrong";
    private final static String REMOVE_WITH_INDEX_ERROR = "Error: method remove(with index) is going wrong";
    private final static String AT_INDEX = " at index ";

    /**
     * Name of my data structures
     */
    private final static String MY_LINKED_LIST = "MyLinkedList:\n";

    /**
     * Testing the class MyLinkedList for main methods
     *
     * @return the results of testing in boolean format if all good true else false
     */
    public boolean testMyLinkedList() {
        System.out.println(MY_LINKED_LIST);
        if (!testLinkedAddGetSize()) {
            return false;
        }
        if (!testLinkedRemoveContainsIndexOf()) {
            return false;
        }
        System.out.println("\nAll tests with MyLinkedList passed successfully\n");
        return true;
    }

    /**
     * Testing methods: remove, contains, indexOf at MyLinkedList class
     *
     * @return true if tests passed successfully and false if no
     */
    private boolean testLinkedRemoveContainsIndexOf() {
        MyLinkedList<Integer> myLinkList = new MyLinkedList<>();
        LinkedList<Integer> javaLinkList = new LinkedList<>();
        MyLinkedList<Integer> secondMyLinkList = new MyLinkedList<>();
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myLinkList.add(i);
            javaLinkList.add(i);
            secondMyLinkList.add(i);
        }
        Random random = new Random();
        int c = random.nextInt(SIZE_FOR_CYCLE_FOR_TESTS - 1);
        if (!myLinkList.contains(c)) {
            System.out.println(CONTAINS_ERROR);
            return false;
        }
        if (myLinkList.indexOf(c) != c) {
            System.out.println(INDEXOF_ERROR);
            return false;
        }

        /* Testing indexOf And Contains */
        if (myLinkList.contains(SIZE_FOR_CYCLE_FOR_TESTS + 1)) {
            System.out.println(CONTAINS_ERROR);
            return false;
        }
        for (int i = 1; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            if (myLinkList.indexOf(i) != javaLinkList.indexOf(i)) {
                System.out.println(INDEXOF_ERROR + AT_INDEX + i);
                return false;
            }
            if (myLinkList.contains(i * -1)) {
                System.out.println(CONTAINS_ERROR + AT_INDEX + i);
                return false;
            }

        }
        System.out.println("Testing MyLinkedList: testing of methods: \"indexOf " +
                "and contains\" passed successfully");

        /* Testing remove first and remove last*/
        for (int i = 0; i < myLinkList.size() / 2; i++) {
            c = random.nextInt(secondMyLinkList.size() - 1);

            if (c != secondMyLinkList.size() - 1) {
                int q = secondMyLinkList.get(c + 1);
                secondMyLinkList.remove(c);
                if (secondMyLinkList.get(c) != q) {
                    System.out.println(REMOVE_WITH_INDEX_ERROR + AT_INDEX + i);
                    return false;
                }
            } else {
                int q = secondMyLinkList.get(c - 1);
                secondMyLinkList.remove(c);
                if (secondMyLinkList.getLast() != q) {
                    System.out.println(REMOVE_WITH_INDEX_ERROR + AT_INDEX + i);
                    return false;
                }
            }

            if (!myLinkList.removeFirst().equals(javaLinkList.removeFirst())) {
                System.out.println(REMOVE_FIRST_ERROR + AT_INDEX + i);
                return false;
            }
            if (!myLinkList.removeLast().equals(javaLinkList.removeLast())) {
                System.out.println(REMOVE_LAST_ERROR + AT_INDEX + i);
                return false;
            }
            if (myLinkList.size() != javaLinkList.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
        }

        System.out.println("Testing MyLinkedList: testing of methods: \"removeLast, remove(with index), " +
                "removeFirst, and size of list\" passed successfully");

        return true;
    }

    /**
     * Testing methods: add, get, size at MyLinkedList class
     *
     * @return true if tests passed successfully and false if no
     */
    private boolean testLinkedAddGetSize() {
        MyLinkedList<Integer> myLinkList = new MyLinkedList<>();
        LinkedList<Integer> javaLinkList = new LinkedList<>();

        /* Testing addFirst, add with index, getFirst and size */
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            if (i % 2 == 0) {
                myLinkList.addFirst(i);
                javaLinkList.addFirst(i);
            } else {
                myLinkList.add(0, i);
                javaLinkList.add(0, i);
            }
            int first = myLinkList.getFirst();
            int second = javaLinkList.getFirst();
            if (first != second) {
                if (i % 2 == 0) {
                    System.out.println(ADD_FIRST_ERROR + AT_INDEX + i);
                } else {
                    System.out.println(ADD_WITH_INDEX_ERROR + AT_INDEX + i);
                }
                return false;
            }
            if (myLinkList.size() != javaLinkList.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyLinkedList: testing of methods: \"addFirst, add(with index), " +
                "getFirst and size of list\" passed successfully");

        /* Testing addLast, getLast */
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myLinkList.addLast(i);
            javaLinkList.addLast(i);
            int first = myLinkList.getLast();
            int second = javaLinkList.getLast();
            if (!myLinkList.get(myLinkList.size() - 2).equals(javaLinkList.get(javaLinkList.size() - 2))) {
                System.out.println(ADD_LAST_ERROR + AT_INDEX + i);
                return false;
            }
            if (first != second) {
                System.out.println(GET_LAST_ERROR + AT_INDEX + i);
                return false;
            }
            if (myLinkList.size() != javaLinkList.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyLinkedList: testing of methods: \"addLast, getLast, " +
                "get(with index) and size of list\" passed successfully");

        return true;
    }

}
