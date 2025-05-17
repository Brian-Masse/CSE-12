/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA6 Write-up
   
  This file is for CSE 12 PA6 in Spring 2025,
*/

// MARK: MyDeque
/**
 * This class represents a generic Deque data type similar to the one found in
 * Java
 * 
 * It contains standard functions including addFirst, addLast, removeFirst, and
 * removeLast.
 */
public class MyDeque<E> implements DequeInterface<E> {

    // static variables
    private static final int DEFAULT_CAPACITY = 10;
    private static final int EXPAND_CAPACITY_RATIO = 2;
    private static final int STANDARD_ITERATOR = 1;

    // instance variables
    Object[] data;
    int size;
    int front;
    int rear;

    // MARK: MyDequeue
    /**
     * Initializes an empty deque with default capacity
     * 
     * @param initialCapacity the initialCapacity to initialize the deque with
     */
    public MyDeque(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }

        int capacity = initialCapacity;
        this.data = new Object[capacity];

        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    // MARK: size
    /**
     * Gets the size var
     * 
     * @return number of valid elements in the deque
     */
    public int size() {
        return this.size;
    }

    // MARK: ExpandCapacity
    /**
     * Expands the capacity of the deque by a fixed ratio. Once the capacity is
     * resized, all elements get reindexed to properly wrap around the array
     */
    public void expandCapacity() {
        // calaculate the new capacity
        int currentCapacity = this.data.length;
        int proposedCapacity = currentCapacity * EXPAND_CAPACITY_RATIO;
        int newCapacity = this.data.length == 0 ? DEFAULT_CAPACITY
                : proposedCapacity;

        // copy elements into new array
        Object[] newArray = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            int index = (i + front) % currentCapacity;
            newArray[i] = this.data[index];
        }

        // update instance variables
        this.front = 0;
        this.rear = size == 0 ? 0 : size - STANDARD_ITERATOR;
        this.data = newArray;
    }

    // MARK: addFirst
    /**
     * Adds an element to the beginning of the dequeue. If front is at the
     * beginning of the array, it wraps around to the end. If the list is empty,
     * front does not change
     * 
     * @param element the element to be added
     */
    public void addFirst(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        // expand capacity if neccessary
        if (this.size >= this.data.length) {
            this.expandCapacity();
        }

        // add the element
        int capacity = this.data.length;
        int iterator = this.data[this.front] == null ? 0 : STANDARD_ITERATOR;
        int index = (this.front + capacity - iterator) % capacity;
        this.data[index] = element;

        // update instance vars
        this.size++;
        this.front = index;
    }

    // MARK: addLast
    /**
     * Adds an element to the end of the dequeue. If rear is at the end of the
     * array, it wraps around to the beginning. If the list is empty, rear does
     * not change
     * 
     * @param element the element to be added
     */
    public void addLast(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        // expandCapacity if neccessary
        if (this.size >= this.data.length) {
            this.expandCapacity();
        }

        // add the element
        int capacity = this.data.length;
        int iterator = this.data[this.rear] == null ? 0 : STANDARD_ITERATOR;
        int index = (this.rear + capacity + iterator) % capacity;
        this.data[index] = element;

        // update instance vars
        this.size++;
        this.rear = index;
    }

    // MARK: removeFirst
    /**
     * Removes an element from the front of a deque
     * 
     * @return the element stored at the front of the deque
     */
    public E removeFirst() {
        if (this.size == 0) {
            return null;
        }

        Object data = this.data[this.front];
        this.data[this.front] = null;

        // if there is no data at the front, do nothing
        if (data == null) {
            return null;
        }

        // update instance vars
        int capacity = this.data.length;
        int nextFrontIndex = (this.front + STANDARD_ITERATOR) % capacity;
        this.size--;
        this.front = nextFrontIndex;

        return (E) data;
    }

    // MARK: removeLast
    /**
     * Removes an element from the end of a deque
     * 
     * @return the element stored at the end of the deque
     */
    public E removeLast() {
        if (this.size == 0) {
            return null;
        }

        Object data = this.data[this.rear];
        this.data[this.rear] = null;

        // if there is no data at rear, do nothing
        if (data == null) {
            return null;
        }

        // update instance vars
        int capacity = this.data.length;
        int nextRearIndex = (this.rear - STANDARD_ITERATOR) % capacity;
        this.size--;
        this.rear = nextRearIndex;

        return (E) data;
    }

    // MARK: peekFirst
    /**
     * Previews the element at the start of the deque without removing it
     * 
     * @return the element at the start of the deque
     */
    public E peekFirst() {
        return (E) this.data[this.front];
    }

    // MARK: peekLast
    /**
     * Previews the element at the end of the deque without removing it
     * 
     * @return the element at the end of the deque
     */
    public E peekLast() {
        return (E) this.data[this.rear];
    }

}