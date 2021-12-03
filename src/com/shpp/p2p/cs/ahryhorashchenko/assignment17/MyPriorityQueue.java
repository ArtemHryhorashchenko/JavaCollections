package com.shpp.p2p.cs.ahryhorashchenko.assignment17;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")

/**
 * The class which implements PriorityQueue only with Class String
 */
public class MyPriorityQueue<Objects extends Comparable<Objects>>{

    /**
     * Size of the queue (quantity of elements in array)
     */
    private int size;

    /**
     * Default size of array for store data
     */
    private final static int DEFAULT_SIZE_OF_ARRAY = 11;

    /**
     * Array which is used to store data
     */
    private Object[] queueArray;

    private Objects object1;

    /**
     * Constructor in which memory is allocated for the array
     */
    public MyPriorityQueue() {
        queueArray = new Object[DEFAULT_SIZE_OF_ARRAY];
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
     * Looking at the emptiness of the program if it is empty return true if no return false
     *
     * @return true if array empty and false if not empty
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Add element to the array by comparing it with other elements
     *
     * @param object object which need to add
     */
    public void add(Objects object) {
        if(object == null){
            throw new NullPointerException();
        }
        if (size == queueArray.length) {
            resizeArray();
        }
        object1 = object;
        queueArray[size] = object1;
        siftUp();
        size++;
    }

    /**
     * Method of checking the entered element with its "father" and if the "son"
     * has a lower priority then changes its position with the position of the father
     */
    private void siftUp() {
        int index = size;
        if(size > 0) {
            while (object1.compareTo((Objects) queueArray[(index - 1) / 2]) < 0) {
                queueArray[index] = queueArray[(index - 1) / 2];
                queueArray[(index - 1) / 2] = object1;
                index = (index - 1) / 2;
            }
        }
    }

    /**
     * Resize array in 2 times if size == array length
     */
    private void resizeArray() {
        Object[] newElements = new Object[queueArray.length * 2];
        System.arraycopy(queueArray, 0, newElements, 0, size);
        queueArray = newElements;
    }

    /**
     * Take element from the head of the queue and return it
     *
     * @return element from the head of the queue
     */
    public Objects peek() {
        if (this.size > 0) {
            return (Objects) queueArray[0];
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
            this.size--;
            Objects obj = (Objects) queueArray[0];
            queueArray[0] = queueArray[size];
            queueArray[size] = null;
            siftDown();
            checkSizeForReducing();
            return obj;
        }
        throw new NoSuchElementException();
    }

    /**
     * Checks the conformity of the number of elements to
     * the size if the elements are 25 percent or less then the size of the array decreases
     */
    private void checkSizeForReducing() {
        if(queueArray.length > 100){
            double indexForReduceSize = 0.25;
            double indexOfLoad = (double) size / queueArray.length;
            if(indexOfLoad <= indexForReduceSize){
                reduceSizeArray();
            }
        }
    }

    /**
     * Reduce size of the array for elements in 2 times
     */
    private void reduceSizeArray(){
        Object[] newArray = new Object[queueArray.length/2];
        System.arraycopy(queueArray,0,newArray,0,size);
        queueArray = newArray;
    }

    /**
     * Method of descent down if if the element is less
     * in priority than his son then he changes places
     * with him if not then the method stops working
     */
    private void siftDown() {
        int index = 0;
        int lessSonIndex;
        while (2 * index + 1 < size) {
            Objects left = (Objects) queueArray[2 * index + 1];
            Objects right = (Objects) queueArray[2 * index + 2];
            Objects father = (Objects) queueArray[index];
            object1 = father;
            lessSonIndex = 2 * index + 1;
            if (2 * index + 2 < size && left.compareTo(right) > 0) {
                lessSonIndex = 2 * index + 2;
            }
            if (father.compareTo((Objects) queueArray[lessSonIndex]) < 0) {
                break;
            }
            queueArray[index] = queueArray[lessSonIndex];
            queueArray[lessSonIndex] = father;
            index = lessSonIndex;
        }
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
            Object object = queueArray[i];
            sb.append(object);
            if (i != size - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    /**
     * Clear the MyHashMap and resets size
     */
    public void clear(){
        size = 0;
        queueArray = new Object[DEFAULT_SIZE_OF_ARRAY];
    }

    /**
     * Looks whether the entered object in an array if
     * so returns the true if not that a false
     *
     * @param object entered object
     * @return True if array contains object and false if not contains
     */
    public boolean contains(Objects object){
        for(Object element: queueArray){
            if(element == object || element.equals(object)){
                return true;
            }
        }
        return false;
    }
}
