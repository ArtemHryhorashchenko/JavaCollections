package com.shpp.p2p.cs.ahryhorashchenko.assignment17.Tests;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyStack;

import java.util.Stack;

/**
 * Class for the testing MyStack
 */
public class TestMyStack {

    /**
     * Size of iterations for cycle in testing
     */
    private final static int SIZE_FOR_CYCLE_FOR_TESTS = 1000;

    /**
     * Errors for every method of my data structure
     */
    private final static String PUSH_ERROR = "Error: method push is going wrong";
    private final static String SET_SIZE_ERROR = "Error: method set size is going wrong";
    private final static String POP_ERROR = "Error: method pop is going wrong";
    private final static String SEARCH_ERROR = "Error: method search is going wrong";
    private final static String SIZE_ERROR = "Error: method size is going wrong";
    private final static String PEEK_ERROR = "Error: method peek is going wrong";
    private final static String AT_INDEX = " at index ";

    /**
     * Name of my data structures
     */
    private final static String MY_STACK = "MyStack:\n";

    /**
     * Testing the class MyStack for main methods
     *
     * @return the results of testing in boolean format if all good true else false
     */
    public boolean testMyStack() {
        System.out.println(MY_STACK);
        return testingMyStack();
    }

    /**
     * Testing MyStack for main methods
     *
     * @return True if testing will be done or false if something goes wrong
     */
    private boolean testingMyStack() {
        MyStack<Integer> mst = new MyStack<>();
        Stack<Integer> st = new Stack<>();

        for (int i = 1; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            /* Testing setSize */
            mst.setSize(i);
            st.setSize(i);
            if (mst.size() != st.size()) {
                if (mst.size() != i) {
                    System.out.println(SET_SIZE_ERROR + AT_INDEX + i);
                    return false;
                }
                System.out.println(SIZE_ERROR + AT_INDEX + i);
                return false;
            }
        }
        mst.setSize(0);
        st.setSize(0);
        if (mst.size() != st.size()) {
            System.out.println(SET_SIZE_ERROR);
            return false;
        }
        System.out.println("Testing MyStack: testing of method: \"set size\" passed successfully");

        /* Testing push*/
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            mst.push(i);
            st.push(i);
            if (!mst.peek().equals(st.peek())) {
                System.out.println(PUSH_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyStack: testing of method: \"push\" passed successfully");

        /* Testing search*/
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            int first = mst.search(i);
            int second = st.search(i);
            if (first != second) {
                System.out.println(SEARCH_ERROR + AT_INDEX + i);
                return false;
            }
        }
        System.out.println("Testing MyStack: testing of method: \"search\" passed successfully");

        /* Testing pop, peek*/
        for (int i = 0; i < SIZE_FOR_CYCLE_FOR_TESTS; i++) {
            if (!mst.peek().equals(st.peek())) {
                System.out.println(PEEK_ERROR + AT_INDEX + i);
                return false;
            }
            if (!mst.pop().equals(st.pop())) {
                System.out.println(POP_ERROR + AT_INDEX + i);
                return false;
            }
        }
        mst.clear();
        if (mst.size() != 0) {
            return false;
        }
        System.out.println("Testing MyStack: testing of method: \"pop, peek\" passed successfully");
        System.out.println("\nAll tests with MyStack passed successfully\n");
        return true;
    }
}
