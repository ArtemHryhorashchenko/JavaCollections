package com.shpp.p2p.cs.ahryhorashchenko.assignment17;

import java.util.EmptyStackException;

@SuppressWarnings("unchecked")
/**
 * A class that is a stack (FILO)
 *
 * @param <Objects> type of the elements at the stack
 */
public class MyStack<Objects> {

    /**
     * Default size of first array
     */
    private final static int DEFAULT_SIZE_OF_ARRAY = 100;

    /**
     * Message for output the index out of bounds
     */
    private final static String INDEX_OUT_OF_BOUNDS = "IndexOutOfBoundsException index ";

    /**
     * Message for output the out of length
     */
    private final static String OUT_FOR_LENGTH = " out for length ";

    /**
     * Size of the queue (quantity of elements in array)
     */
    private int size;

    /**
     * Array which is used to store data
     */
    private Object[] arrayStack;

    /**
     * Constructor for the class with empty params
     * When it is called, memory is allocated for an array of default length
     */
    public MyStack() {
        this.arrayStack = new Object[DEFAULT_SIZE_OF_ARRAY];
    }

    /**
     * Set the size for the stack
     *
     * @param size size which need to set for the stack
     */
    public void setSize(int size) {
        if (size > this.size) {
            this.size = size;
            while (this.size > arrayStack.length) {
                resizeArray();
            }
        } else {
            this.size = size;
            System.arraycopy(arrayStack, 0, arrayStack, 0, size);
        }
    }

    /**
     * Return the size of the array
     *
     * @return int variable which contains the size of the array
     */
    public int size() {
        return this.size;
    }

    /**
     * Resize the array ( make size of the array bigger)
     */
    private void resizeArray() {
        Object[] newElements = new Object[arrayStack.length * 2];
        System.arraycopy(arrayStack, 0, newElements, 0, arrayStack.length);
        arrayStack = newElements;
    }

    /**
     * Add object to the end of the array
     *
     * @param object Object which need to add to array
     */
    public void add(Objects object) {
        size++;
        if (size == arrayStack.length) {
            resizeArray();
        }
        arrayStack[this.size - 1] = object;
    }

    /**
     * Return the information about array length
     *
     * @return If size == 0(array is empty) return true else return false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Translates the array into a string and returns it
     *
     * @return the string with all elements of the array
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (size == 0) {
            return sb.append("[]").toString();
        }
        sb.append("[");
        for (int i = 0; i < this.size; i++) {
            Object object = arrayStack[i];
            sb.append(object);
            if (i != this.size - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    /**
     * Remove element from the end of array and return it
     *
     * @return Object which was removed from the array
     */
    public Objects pop() {
        if (!isEmpty()) {
            Object obj = this.arrayStack[this.size - 1];
            Object[] newArray = new Object[this.size - 1];
            System.arraycopy(arrayStack, 0, newArray, 0, this.size - 1);
            arrayStack = newArray;
            this.size--;
            return (Objects) obj;
        }
        throw new EmptyStackException();
    }

    /**
     * Returns the object at the end of the stack, but does not delete it.
     *
     * @return the object at the end of stack
     */
    public Objects peek() {
        return (Objects) arrayStack[this.size - 1];
    }

    /**
     * Add element for the end of the stack
     *
     * @param object element which need to add to the stack
     * @return element which was added to the stack
     */
    public Objects push(Objects object) {
        size++;
        if (size == arrayStack.length) {
            resizeArray();
        }
        arrayStack[this.size - 1] = object;
        return object;
    }

    /**
     * Searches for an item in the stack.
     * If found, its offset from the top of the stack is returned.
     * Otherwise, -1 is returned.
     *
     * @param object element which need to find
     * @return quantity of pop operations for cum to element
     */
    public int search(Objects object) {
        for (int i = 1; i <= this.size; i++) {
            if (arrayStack[this.size - i] == object || arrayStack[this.size - i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Clear the stack make size - 0, and create a new array with length 0
     */
    public void clear() {
        arrayStack = new Object[DEFAULT_SIZE_OF_ARRAY];
        size = 0;
    }

}
