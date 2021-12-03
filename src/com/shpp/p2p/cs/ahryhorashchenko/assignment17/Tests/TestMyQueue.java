package com.shpp.p2p.cs.ahryhorashchenko.assignment17.Tests;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for the testing MyQueue
 */
public class TestMyQueue {
    /**
     * Size of iterations for cycle in testing
     */
    private final static int SIZE_FOR_CYCLE_FOR_TESTS = 1000;

    /**
     * Errors for every method of my data structure
     */
    private final static String OFFER_ERROR = "Error: method offer is going wrong";
    private final static String ELEMENT_ERROR = "Error: method element is going wrong";
    private final static String SIZE_ERROR = "Error: method size is going wrong";
    private final static String PEEK_ERROR = "Error: method peek is going wrong";
    private static final String POLL_ERROR = "Error: method poll is going wrong";
    private final static String ADD_ERROR = "Error: method add is going wrong";
    private final static String CONTAINS_ERROR = "Error: method contains is going wrong";
    private final static String REMOVE_ERROR = "Error: method remove is going wrong";
    private final static String AT_INDEX = " at index ";

    /**
     * Name of my data structures
     */
    private final static String MY_QUEUE = "MyQueue:\n";

    /**
     * Testing the class MyQueue for main methods
     *
     * @return the results of testing in boolean format if all good true else false
     */
    public boolean testMyQueue() {
        System.out.println(MY_QUEUE);
        return testingMyQueue();
    }

    /**
     * Testing MyQueue for main methods
     *
     * @return True if testing will be done or false if something goes wrong
     */
    private boolean testingMyQueue() {
        MyQueue<String> myQueue = new MyQueue<>();
        Queue<String> javaQueue = new LinkedList<>();
        String word = "hello";
        if (!testingQueueRemoveContains()) {
            return false;
        }
        /* Testing offer, peek, size*/
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myQueue.offer(word + i);
            javaQueue.offer(word + i);
            if (!myQueue.peek().equals(javaQueue.peek())) {
                if (myQueue.poll().equals(javaQueue.poll())) {
                    System.out.println(PEEK_ERROR + AT_INDEX + i);
                    return false;
                }
                System.out.println(OFFER_ERROR + AT_INDEX + i);
                return false;
            }

            if (!myQueue.poll().equals(javaQueue.poll())) {
                System.out.println(POLL_ERROR + AT_INDEX + i);
                return false;
            }

            if (myQueue.size() != javaQueue.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyQueue: testing of method: \"offer, peek\" passed successfully");
        myQueue.clear();
        javaQueue.clear();
        if (myQueue.size() != javaQueue.size()) {
            System.out.println(SIZE_ERROR);
            return false;
        }
        System.out.println("Testing MyQueue: testing of method: \"size\" passed successfully");

        /* Testing add, element, poll*/
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myQueue.add(word + i);
            javaQueue.add(word + i);
            /* Testing element */
            if (!myQueue.element().equals(javaQueue.element())) {
                if (myQueue.peek().equals(javaQueue.element())) {
                    System.out.println(ELEMENT_ERROR + AT_INDEX + i);
                    return false;
                }
                System.out.println(ADD_ERROR + AT_INDEX + i);
                return false;
            }
            if (myQueue.size() != javaQueue.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }

            /* Testing poll */
            if (!myQueue.poll().equals(javaQueue.poll())) {
                System.out.println(POLL_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyQueue: testing of method: \"add, element, poll\" passed successfully");

        System.out.println("\nAll tests with MyQueue passed successfully\n");
        return true;
    }

    /**
     * Testing methods: remove and contains at my queue class
     *
     * @return true if tests passed successfully and false if no
     */
    private boolean testingQueueRemoveContains() {
        MyQueue<String> myQueue = new MyQueue<>();
        Queue<String> javaQueue = new LinkedList<>();
        String word = "hello";
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            myQueue.add(word + i);
            javaQueue.add(word + i);
        }

        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            if (myQueue.contains(word + i)) {
                if (!myQueue.remove().equals(javaQueue.remove())) {
                    System.out.println(REMOVE_ERROR + AT_INDEX + i);
                    return false;
                }
            } else {
                System.out.println(CONTAINS_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyQueue: testing of method: \"remove, contains\" passed successfully");
        return true;
    }
}
