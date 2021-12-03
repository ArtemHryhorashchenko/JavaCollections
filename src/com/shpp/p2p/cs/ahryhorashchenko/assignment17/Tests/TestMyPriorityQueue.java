package com.shpp.p2p.cs.ahryhorashchenko.assignment17.Tests;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyPriorityQueue;

import java.util.PriorityQueue;

/**
 * Class for the testing MyPriorityQueue
 */
public class TestMyPriorityQueue {
    /**
     * Size of iterations for cycle in testing
     */
    private final static int SIZE_FOR_CYCLE_FOR_TESTS = 1000;

    /**
     * Errors for every method of my data structure
     */
    private final static String AT_INDEX = " at index ";
    private final static String PEEK_ERROR = "Error: method peek is going wrong";
    private static final String POLL_ERROR = "Error: method poll is going wrong";
    private final static String ADD_ERROR = "Error: method add is going wrong";
    private final static String SIZE_ERROR = "Error: method size is going wrong";

    /**
     * Name of my data structures
     */
    private final static String MY_PRIORITY_QUEUE = "MyPriorityQueue:\n";

    /**
     * Testing MyPriorityQueue for all main methods
     *
     * @return true if tests passed successfully and false if no
     */
    public boolean testMyPriorityQueue() {
        System.out.println(MY_PRIORITY_QUEUE);
        String word = "aSDAFasd";
        MyPriorityQueue<String> myPriorityQueue = new MyPriorityQueue<>();
        PriorityQueue<String> javaPriorityQueue = new PriorityQueue<>();
        for (int i = SIZE_FOR_CYCLE_FOR_TESTS; i > 0; i--) {
            myPriorityQueue.add(word + i);
            javaPriorityQueue.add(word + i);
            if (myPriorityQueue.size() != javaPriorityQueue.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
            /*if (!myPriorityQueue.poll().equals(javaPriorityQueue.poll())) {
                System.out.println(POLL_ERROR + AT_INDEX + i);
                return false;
            }*/
        }

        for (int i = SIZE_FOR_CYCLE_FOR_TESTS; i > SIZE_FOR_CYCLE_FOR_TESTS * -1; i--) {
            myPriorityQueue.add(word + i);
            javaPriorityQueue.add(word + i);
            if (myPriorityQueue.size() != javaPriorityQueue.size()) {
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
        }

        for (int i = 0; i < myPriorityQueue.size(); i++) {
            if (!myPriorityQueue.peek().equals(javaPriorityQueue.peek())) {
                if (myPriorityQueue.poll().equals(javaPriorityQueue.poll())) {
                    System.out.println(PEEK_ERROR + AT_INDEX + i);
                    return false;
                }
                System.out.println(ADD_ERROR + AT_INDEX + i);
                return false;
            }
            if (!myPriorityQueue.poll().equals(javaPriorityQueue.poll())) {
                System.out.println(POLL_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyPriorityQueue: testing of method: \"add, size, poll, peek\" passed successfully");
        System.out.println("\nAll tests with MyPriorityQueue passed successfully\n");
        return true;
    }

}
