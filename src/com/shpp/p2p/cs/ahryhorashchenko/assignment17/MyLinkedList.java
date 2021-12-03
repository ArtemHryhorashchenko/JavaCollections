package com.shpp.p2p.cs.ahryhorashchenko.assignment17;

@SuppressWarnings("unchecked")

/**
 * A class for presenting a list of nodes that are interconnected
 *
 * @param <Objects> type of elements which will be used at the linkedList
 */
public class MyLinkedList<Objects> {
    /**
     * Message for output the index out of bounds
     */
    private final static String INDEX_OUT_OF_BOUNDS = "IndexOutOfBoundsException index ";

    /**
     * Message for output the out of length
     */
    private final static String OUT_FOR_LENGTH = " out for length ";

    /**
     * First node of the MyLinkedList
     */
    private Node firstNode;

    /**
     * Last node of the MyLinkedList
     */
    private Node lastNode;

    /**
     * Size of the queue (quantity of elements in array)
     */
    private int size = 0;

    /**
     * Add element to the end of the MyLinkedList to the last LinkNode
     *
     * @param object element which need to add
     */
    public void add(Objects object) {
        addLast(object);
    }

    /**
     * Add element to the end of the MyLinkedList to the last LinkNode
     *
     * @param object element which need to add
     */
    public void addLast(Objects object) {
        Node newNode = new Node(lastNode, object, null);
        /* If the list was empty and add first element this element is the first and the last*/
        if (lastNode == null || size == 0) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
        }
        lastNode = newNode;
        size++;
    }

    /**
     * Add element to the first node of the MyLinkedList to the first LinkNode
     *
     * @param object element which need to add
     */
    public void addFirst(Objects object) {
        Node<Objects> newNode = new Node(null, object, firstNode);
        /* If the list was empty and add first element this element is the first and the last */
        if (this.firstNode == null || size == 0) {
            this.lastNode = newNode;
        } else {
            firstNode.prev = newNode;
        }
        this.firstNode = newNode;
        size++;
    }

    /**
     * Remove first node and return element which was at this node
     *
     * @return element of the first LinkNode
     */
    public Objects removeFirst() {
        Object obj = this.firstNode.element;
        this.firstNode = firstNode.next;
        this.size--;
        return (Objects) obj;
    }

    /**
     * Add the element by the entered index
     *
     * @param object element which need to add to linked list
     * @param index  index in which need to add this element
     */
    public void add(int index, Objects object) {
        if (!isIndexOutOfBounds(index)) {
            Node[] newFirstNode = new Node[index + 2];
            for (int i = 0; i < index; i++) {
                newFirstNode[i] = firstNode;
                firstNode = firstNode.next;
            }
            newFirstNode[index] = new Node(null, object, null);
            newFirstNode[index + 1] = firstNode;
            for (int i = newFirstNode.length - 1; i >= 0; i--) {
                if (i != newFirstNode.length - 1 && i != 0) {
                    newFirstNode[i].next = newFirstNode[i + 1];
                    newFirstNode[i].prev = newFirstNode[i - 1];
                } else if (i == newFirstNode.length - 1) {
                    newFirstNode[i].next = firstNode.next;
                    newFirstNode[i].prev = newFirstNode[i - 1];
                } else if (i == 0) {
                    newFirstNode[i].next = newFirstNode[i + 1];
                    firstNode = newFirstNode[0];
                }
            }
            size++;
        } else {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS + index + OUT_FOR_LENGTH + this.size);
        }
    }

    /**
     * Remove one node from all nodes by the index of it
     *
     * @param index index of the node which need to delete
     * @return element of the node which was deletes
     */
    public Objects remove(int index) {
        if (!isIndexOutOfBounds(index)) {
            if (index == 0) {
                return removeFirst();
            } else if (index == size - 1) {
                return removeLast();
            }

            Node[] newFirstNode = new Node[index + 1];
            for (int i = 0; i <= index; i++) {
                newFirstNode[i] = firstNode;
                firstNode = firstNode.next;
            }

            firstNode.prev = newFirstNode[newFirstNode.length - 2];

            newFirstNode = makeOneNode(newFirstNode);

            size--;
            return (Objects) newFirstNode[newFirstNode.length - 1].element;
        } else {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS + index + OUT_FOR_LENGTH + this.size);
        }
    }

    /**
     * Makes one of several nodes connected to all the others
     *
     * @param newFirstNode all Nodes which need connected
     * @return newFirstNode with first node which connected with all other
     */
    private Node[] makeOneNode(Node[] newFirstNode) {
        for (int i = newFirstNode.length - 2; i >= 0; i--) {
            if (i != newFirstNode.length - 2 && i != 0) {
                newFirstNode[i].next = newFirstNode[i + 1];
                newFirstNode[i].prev = newFirstNode[i - 1];
            } else if (i == newFirstNode.length - 2 && newFirstNode.length > 2) {
                newFirstNode[i].next = firstNode;
                newFirstNode[i].prev = newFirstNode[i - 1];
            } else if (i == 0 && newFirstNode.length > 2) {
                newFirstNode[i].next = newFirstNode[i + 1];
                firstNode = newFirstNode[0];
            } else {
                newFirstNode[i].next = firstNode;
                firstNode = newFirstNode[0];
            }
        }
        return newFirstNode;
    }

    /**
     * Remove last node and return element which was at this node
     *
     * @return element of the last LinkNode
     */
    public Objects removeLast() {
        Object obj = this.lastNode.element;
        this.lastNode = this.lastNode.prev;
        this.size--;
        return (Objects) obj;
    }

    /**
     * Get first element of the list and return it
     *
     * @return element from the first LinkNode of MyLinkedList
     */
    public Objects getFirst() {
        if (firstNode != null) {
            return (Objects) firstNode.element;
        }
        return null;
    }

    /**
     * Get last element of the list and return it
     *
     * @return element from the last LinkNode of MyLinkedList
     */
    public Objects getLast() {
        if (size > 0) {
            return (Objects) lastNode.element;
        }
        return null;
    }

    /**
     * Return the information about list`s length
     *
     * @return If size == 0(list is empty) return true else return false
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Clear the queue make size - 0, and create a new LinkNode which == null
     */
    public void clear() {
        this.firstNode = null;
        this.lastNode = null;
        this.size = 0;
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
     * Translates the list into a string and returns it
     *
     * @return the string with all elements of the list
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (this.size != 0) {
            Node newNode = firstNode;
            for (int i = 0; i < this.size; i++) {
                sb.append(newNode.element);
                if (i != this.size - 1) {
                    sb.append(", ");
                }
                newNode = newNode.next;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Looks whether the entered object in the list if
     * so returns the true if not that a false
     *
     * @param object entered object
     * @return True if list contains o and false if not contains
     */
    public boolean contains(Objects object) {
        if (this.size > 0) {
            Node newNode = this.firstNode;
            for (int i = 0; i < this.size; i++) {
                if (newNode.element == object || newNode.element.equals(object)) {
                    return true;
                }
                newNode = newNode.next;
            }
        }
        return false;
    }

    /**
     * Look for every element if anyone == entered element return index of this element else return -1
     *
     * @param object element which need to find at the array
     * @return index of the element if such an element exists and -1 if not
     */
    public int indexOf(Objects object) {
        if (this.size > 0) {
            Node newNode = this.firstNode;
            for (int i = 0; i < this.size; i++) {
                if (newNode.element == object || newNode.element.equals(object)) {
                    return i;
                }
                newNode = newNode.next;
            }
        }
        return -1;
    }

    /**
     * Get element from the list by the entering index and return this element
     *
     * @param index index of the element
     * @return object which is on this index
     */
    public Objects get(int index) {
        if (!isIndexOutOfBounds(index)) {
            if (index == size - 1) {
                return (Objects) lastNode.element;
            } else if (index == 0) {
                return (Objects) firstNode.element;
            }
            Node newNode = firstNode;

            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }

            return (Objects) newNode.element;
        }
        throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS + index + OUT_FOR_LENGTH + this.size);
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
     * The class for representing the nodes used in the Linked List
     *
     * @param <Objects>
     */
    private static class Node<Objects> {
        /**
         * Element of the current node
         */
        Objects element;

        /**
         * Next node of the current node
         */
        Node<Objects> next;

        /**
         * Previous node of the current node
         */
        Node<Objects> prev;

        /**
         * Constructor for node in which entering element of the current node,
         * next node and previous node
         *
         * @param prev    previous node of the current node
         * @param element element of the current node
         * @param next    next node of the current node
         */
        Node(Node<Objects> prev, Objects element, Node<Objects> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
