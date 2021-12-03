package com.shpp.p2p.cs.ahryhorashchenko.assignment17;

@SuppressWarnings("unchecked")

/**
 * Class that implements an ArrayList
 *
 * @param <Objects> the type of elements which will contains
 */
public class MyArrayList<Objects> {

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
    private int size = 0;

    /**
     * Array which is used to store data
     */
    private Object[] elements;

    /**
     * Constructor for the class with empty params
     * When it is called, memory is allocated for an array of length 0
     */
    public MyArrayList() {
        this.elements = new Object[DEFAULT_SIZE_OF_ARRAY];
    }

    /**
     * Constructor into which you can throw the same class and it will be copied to this one
     * When it is called, memory is allocated for an array of length 0
     *
     * @param createdArrayList ready class from which will copy the data to the new arraylist
     */
    public MyArrayList(MyArrayList<Objects> createdArrayList) {
        if (createdArrayList.size > 0) {
            this.elements = new Object[createdArrayList.size];
            for (int i = 0; i < createdArrayList.size; i++) {
                size++;
                this.elements[i] = createdArrayList.get(i);
            }
        } else {
            this.elements = new Object[0];
        }
    }

    /**
     * Add element for index entering from user if this index not out of array length
     *
     * @param object object which need to add
     * @param index  the index to which you want to add this item
     */
    public void add(int index, Objects object) {
        size++;
        if (!isIndexOutOfBounds(index)) {
            if (size == elements.length) {
                resizeArray();
            }
            if (index == 0) {
                System.arraycopy(elements, 0, elements, 1, size);
            } else {
                System.arraycopy(elements, 0, elements, 0, index);
                System.arraycopy(elements, index, elements, index + 1, size - index - 1);
            }
            elements[index] = object;
        } else {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS + index + OUT_FOR_LENGTH + this.size);
        }
    }

    /**
     * Set the element of entering index to entered element
     *
     * @param object Object which need to set for entered index
     * @param index  index of the element from array
     */
    public void set(int index, Objects object) {
        if (!isIndexOutOfBounds(index)) {
            elements[index] = object;
        } else {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS + index + OUT_FOR_LENGTH + this.size);
        }
    }

    /**
     * Add object to the end of the array
     *
     * @param object Object which need to add to array
     */
    public void add(Objects object) {
        size++;
        if (size == elements.length) {
            resizeArray();
        }
        elements[this.size - 1] = object;
    }

    /**
     * Resize the array ( make size of the array for 1 bigger)
     */
    private void resizeArray() {
        Object[] newElements = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, this.size - 1);
        elements = newElements;
    }

    /**
     * Remove element from the array by the entering index of this element
     *
     * @param index index of the element which need to remove from array
     * @return Object which was removed from the array
     */
    public Objects remove(int index) {
        Objects element = (Objects) elements[index];
        if (!isIndexOutOfBounds(index)) {
            if (this.size >= 2) {
                if (index == 0) {
                    System.arraycopy(elements, index + 1, elements, 0, size - 1);
                } else if (index == size - 1) {
                    System.arraycopy(elements, 0, elements, 0, size - 1);
                } else {
                    System.arraycopy(elements, 0, elements, 0, index);
                    System.arraycopy(elements, index + 1, elements, index, size - index - 1);
                }
                this.size--;
            } else {
                this.elements = new Object[DEFAULT_SIZE_OF_ARRAY];
                size = 0;
            }
            return element;
        }
        throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS + index + OUT_FOR_LENGTH + this.size);


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
     * Get element from the array by the entering index and return this element
     *
     * @param index index of the element
     * @return object which is on this index
     */
    public Objects get(int index) {
        if (!isIndexOutOfBounds(index)) {
            return (Objects) elements[index];
        }
        throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS + index + OUT_FOR_LENGTH + this.size);
    }

    /**
     * Clear the array make size - 0, and create a new array with length 0
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
        for (int i = 0; i < this.size; i++) {
            Object object = elements[i];
            sb.append(object);
            if (i != this.size - 1) {
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
        for (int i = 0; i < size; i++) {
            if (elements[i] == object || elements[i].equals(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Look for entering index if it out of the size or less than zero
     *
     * @param index index which entered
     * @return true if it does not go beyond 0 and the size if it goes
     */
    private boolean isIndexOutOfBounds(int index) {
        return (index >= this.size || index < 0);
    }

    /**
     * Look for every element if anyone == entered element return index of this element else return -1
     *
     * @param object element which need to find at the array
     * @return index of the element if such an element exists and -1 if not
     */
    public int indexOf(Objects object) {
        if (size > 0) {
            for (int i = 0; i < this.size; i++) {
                if (elements[i] == object || elements[i].equals(object)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
