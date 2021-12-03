package com.shpp.p2p.cs.ahryhorashchenko.assignment17;

import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
/**
 * Class that implements a queue that works on the
 * principle of first come in first come out (FIFO)
 *
 * @param <Objects> the type of elements which will contains
 */
public class MyQueue<Objects> {

    /**
     * Default size of first array
     */
    private final static int DEFAULT_SIZE_OF_ARRAY = 11;

    /**
     * Size of the queue (quantity of elements in array)
     */
    private int size;

    /**
     * Array which is used to store data
     */
    private Object[] elements;

    /**
     * Constructor for the class with empty params
     * When it is called, memory is allocated for an array of length 0
     */
    public MyQueue() {
        this.elements = new Object[DEFAULT_SIZE_OF_ARRAY];
    }

    /**
     * Add object to the end of the array
     *
     * @param object Object which need to add to array
     */
    public void add(Objects object) {
        size++;
        if (this.size == elements.length) {
            resizeArray();
        }
        elements[size - 1] = object;
    }

    /**
     * Resize the array ( make size of the array for 1 bigger)
     */
    private void resizeArray() {
        Object[] newElements = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, size - 1);
        elements = newElements;
    }

    /**
     * Return the size of the array(queue)
     *
     * @return int variable which contains the size of the array(queue)
     */
    public int size() {
        return size;
    }

    /**
     * Clear the queue make size - 0, and create a new array with length 0
     */
    public void clear() {
        elements = new Object[DEFAULT_SIZE_OF_ARRAY];
        size = 0;
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
        for (int i = 0; i < size; i++) {
            Object object = elements[i];
            sb.append(object);
            if (i != size - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    /**
     * Looks whether the entered object in an array if
     * so returns the true if not that a false
     *
     * @param object entered object
     * @return True if array contains o and false if not contains
     */
    public boolean contains(Objects object) {
        for (Object element : elements) {
            if (element == object || element.equals(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Take the first element from the queue and return it
     * If the queue is empty generate exception NoSuchElementException
     *
     * @return element from the head of the queue
     */
    public Objects element() {
        if (size > 0) {
            return (Objects) elements[0];
        }
        throw new NoSuchElementException();
    }

    /**
     * Take the first element from the queue remove and return it
     * If the queue is empty generate exception NoSuchElementException
     *
     * @return Element removed from queue head
     */
    public Objects remove() {
        if (this.size > 0) {
            Object obj = this.elements[0];
            this.size--;
            Object[] newArray = new Object[this.size];
            System.arraycopy(elements, 1, newArray, 0, this.size);
            elements = newArray;
            return (Objects) obj;
        }
        throw new NoSuchElementException();
    }

    /**
     * Return element from the head of the queue
     * If the queue is empty return null
     *
     * @return element from the head of the queue or null if queue is empty
     */
    public Objects peek() {
        if (this.size > 0) {
            return (Objects) this.elements[0];
        }
        return null;
    }

    /**
     * Take the first element from the queue remove and return it
     * If the queue is empty return null
     *
     * @return Element removed from queue head
     */
    public Objects poll() {
        if (this.size > 0) {
            Object obj = this.elements[0];
            this.size--;
            Object[] newArray = new Object[elements.length];
            if (this.size != 0) {
                System.arraycopy(elements, 1, newArray, 0, this.size);
            }
            elements = newArray;
            return (Objects) obj;
        }
        return null;
    }

    /**
     * Try add element to the end of the queue
     *
     * @param object object which need to add for the queue
     * @return true if the object was added successfully and false if not
     */
    public boolean offer(Objects object) {
        try {
            add(object);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
