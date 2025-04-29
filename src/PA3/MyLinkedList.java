/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA3 Write-up
   
  This file is for CSE 12 PA3 in Spring 2025,
*/

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

// MARK: MyLinkedList
/**
 * This class represents a generic LinkedList data type similar to the one found
 * in Java
 * 
 * It contains standard functions including add, insert, remove, and contains.
 * It uses Sentinel nodes so head and tail are never null the Node class is
 * contained within the MyLinkedList class
 */
public class MyLinkedList<E> extends AbstractList<E> {

    // MARK: vars
    int size;
    Node head;
    Node tail;

    // MARK: Node
    /**
     * A Node class that holds data and references to previous and next Nodes.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /**
         * Constructor to create singleton Node
         * 
         * @param element Element to add, can be null
         */
        public Node(E element) {
            // Initialize the instance variables
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /**
         * Set the parameter prev as the previous node
         * 
         * @param prev new previous node
         */
        public void setPrev(Node prev) {
            this.prev = prev;
        }

        /**
         * Set the parameter next as the next node
         * 
         * @param next new next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * Set the parameter element as the node's data
         * 
         * @param element new element
         */
        public void setElement(E element) {
            this.data = element;
        }

        /**
         * Accessor to get the next Node in the list
         * 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * Accessor to get the prev Node in the list
         * 
         * @return the previous node
         */
        public Node getPrev() {
            return this.prev;
        }

        /**
         * Accessor to get the Nodes Element
         * 
         * @return this node's data
         */
        public E getElement() {
            return this.data;
        }
    }

    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }

    // MARK: - Iterator
    protected class MyListIterator implements ListIterator<E> {
        MyLinkedList<E>.Node left;
        MyLinkedList<E>.Node right;

        int idx;
        boolean forward;
        boolean canRemoveOrSet;

        // MARK: MyListIterator
        public MyListIterator() {
            this.left = head;
            this.right = head.next;

            this.idx = 0;
            this.forward = true;
            this.canRemoveOrSet = false;
        }

        // MARK: hasNext / hasPrev
        public boolean hasNext() {
            return (this.right.getElement() != null);
        }

        public boolean hasPrevious() {
            return this.left.getElement() != null;
        }

        // MARK: next
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            this.idx += 1;
            this.left = right;
            this.right = right.getNext();

            this.canRemoveOrSet = true;
            this.forward = true;

            return this.left.getElement();
        }

        // MARK: Prev
        public E previous() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }

            this.idx -= 1;
            this.right = this.left;
            this.left = left.getPrev();

            this.canRemoveOrSet = true;
            this.forward = false;

            return this.right.getElement();
        }

        // MARK: index
        public int nextIndex() {
            return this.idx;
        }

        public int previousIndex() {
            return this.hasPrevious() ? this.idx - 1 : this.idx;
        }

        // MARK: add
        public void add(E element) {
            if (element == null) {
                throw new NullPointerException();
            }

            Node newNode = new Node(element);
            this.left = newNode;
            this.idx += 1;

            this.canRemoveOrSet = false;
        }

        // MARK: set
        public void set(E element) {
            if (element == null) {
                throw new NullPointerException();
            }
            if (!this.canRemoveOrSet) {
                throw new IllegalStateException();
            }

            if (this.forward) {
                this.left.setElement(element);
            } else {
                this.right.setElement(element);
            }
        }

        // MARK: remove
        public void remove() {
            if (!this.canRemoveOrSet) {
                throw new IllegalStateException();
            }

            if (this.forward) {
                this.left = this.left.getPrev();
                this.idx -= 1;
            } else {
                this.right = this.right.getNext();
            }

            this.canRemoveOrSet = false;
        }

    }

    // MARK: checkIndexBounds
    /**
     * checks whether the passed index is a valid input
     * 
     * @param index the index to check
     */
    private void checkIndexBounds(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    // MARK: Initializer
    /**
     * Initializes an empty LinkedList
     */
    public MyLinkedList() {
        Node dummyHead = new Node(null);
        Node dummyTail = new Node(null);
        this.head = dummyHead;
        this.tail = dummyTail;

        this.head.setNext(this.tail);
        this.tail.setPrev(this.head);

        this.size = 0;
    }

    // MARK: Size
    /**
     * Gets the size of the LinkedList. Does not count the sentinel nodes
     * 
     * @return the size of the list
     */
    @Override
    public int size() {
        return this.size;
    }

    // MARK: getNth
    /**
     * Gets the node at the provided index. Throws an error if out of bounds
     * 
     * @param index the index to fetch
     * @return the node at index
     */
    protected Node getNth(int index) {
        checkIndexBounds(index);

        Node currentNode = this.head;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    // MARK: get
    /**
     * gets the data at the provided index. Throws an error if out of bounds
     * 
     * @param index the index to fetch
     * @return the data at the index
     */
    @Override
    public E get(int index) {
        Node node = getNth(index);
        return node.getElement();
    }

    // MARK: add
    /**
     * Creates a node an adds it before the given index. Can accept inputs from
     * index 0 to size, inclusive.
     * 
     * Throws an error if the data is null
     * 
     * @param index the index to insert after
     * @param data  the data to insert into the list
     */
    @Override
    public void add(int index, E data) {
        if (index != this.size) {
            checkIndexBounds(index);
        }

        if (data == null) {
            throw new NullPointerException();
        }

        Node newNode = new Node(data);

        if (index == this.size) {
            newNode.setPrev(this.tail.getPrev());
            newNode.setNext(this.tail);
            this.tail.getPrev().setNext(newNode);
            this.tail.setPrev(newNode);

        } else {
            // move to the correct position in the list
            Node currentNode = getNth(index);

            // Insert the new node
            newNode.setNext(currentNode);
            newNode.setPrev(currentNode.getPrev());
            currentNode.getPrev().setNext(newNode);
            currentNode.setPrev(newNode);
        }

        this.size += 1;
    }

    /**
     * Adds data to the end of the list
     * 
     * @param data the data to add
     * @return always returns true
     */
    @Override
    public boolean add(E data) {
        this.add(this.size, data);
        return true;
    }

    // MARK: set
    /**
     * Sets the data of the node at the index to the provided data
     * 
     * Throws an error if the data is null or if the index is out of bounds
     * 
     * @param index the index to set
     * @param data  the data to set it with
     * @return the previous value of the node
     */
    @Override
    public E set(int index, E data) {
        this.checkIndexBounds(index);

        if (data == null) {
            throw new NullPointerException();
        }

        Node currentNode = this.getNth(index);
        E previousData = currentNode.getElement();
        currentNode.setElement(data);

        return previousData;
    }

    // MARK: Remove
    /**
     * Removes the node at the given index and returns its value
     * 
     * throws an error if the index is out of bounds
     * 
     * @param index the index to remove at
     * @return the value of the former node
     */
    @Override
    public E remove(int index) {
        this.checkIndexBounds(index);

        Node currentNode = this.getNth(index);
        E previousData = currentNode.getElement();

        Node previousNode = currentNode.getPrev();
        Node nextNode = currentNode.getNext();

        nextNode.setPrev(previousNode);
        previousNode.setNext(nextNode);

        this.size -= 1;

        return previousData;
    }

    // MARK: Clear
    /**
     * Clears a list of all its non sentinel nodes
     */
    @Override
    public void clear() {
        int initialSize = this.size;
        for (int i = 0; i < initialSize; i++) {
            this.remove(0);
        }
    }

    // MARK: isEmpty
    /**
     * Checks whether a list is empty, not including sentinel nodes
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    // MARK: Contains
    /**
     * Chceks if the list contains the given data in a range. Both the start and
     * end index must be in range with start < end
     * 
     * If there are multiple copies of the given data, return the first
     * 
     * @param data  the data to check for
     * @param start the lower bound of the range
     * @param end   the upper bound of the range
     * @return whether the list contains the data
     */
    public boolean contains(E data, int start, int end) {
        // check that the indicies are valid
        this.checkIndexBounds(start);
        if (end != this.size) {
            this.checkIndexBounds(end);
        }

        if (end <= start) {
            return false;
        }

        Node currentNode = this.getNth(start);
        for (int i = start; i < end; i++) {
            if (currentNode.getElement().equals(data)) {
                return true;
            }
            currentNode = currentNode.getNext();
        }

        return false;
    }

    // MARK: indexOfElement
    /**
     * Gets the index of a given element. If there are multiple, return the
     * first index
     * 
     * @param data the data to check for
     * @return the index of the data
     */
    public int indexOfElement(E data) {
        if (data == null) {
            throw new NullPointerException();
        }

        Node currentNode = this.head;
        for (int i = 0; i < this.size; i++) {
            currentNode = currentNode.getNext();
            if (currentNode.getElement().equals(data)) {
                return i;
            }
        }

        return -1;
    }
}
