/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA2 Write-up
   
  This file is for CSE 12 PA1 in Spring 2025,
*/

/**
 * This class represents a generic ArrayList data type similar to the one found
 * in Java
 * 
 * It implements the MyList Interface, which includes several standard functions
 * to create a Java Collection, inclulding: append, prepend, and insert remove
 * find, rotate
 */
public class MyArrayList<E> implements MyList<E> {

    // MARK: Constants
    protected static final int DEFAULT_CAPACITY = 5;
    protected static final int INDEX_NOT_FOUND = -1;
    protected static final int EMPTY_SIZE = 0;
    protected static final int CAPACITY_SCALE_FACTOR = 2;

    // MARK: Vars
    Object[] data;
    int size;

    // MARK: Initializers:
    /**
     * Construct a new instance of MyArrayList
     * 
     * Instantiates data with DEFAULT_CAPACITY
     */
    public MyArrayList() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = EMPTY_SIZE;
    }

    /**
     * Construct a new instance of MyArrayList
     * 
     * @param initialCapacity the initialCapacity for the data
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < EMPTY_SIZE) {
            throw new IllegalArgumentException();
        }

        this.data = new Object[initialCapacity];
        this.size = EMPTY_SIZE;
    }

    /**
     * Construct a new instance of MyArrayList If the passed array is null,
     * default to the no-arg constructor
     * 
     * @param arr the initial data for the list.
     */
    public MyArrayList(E[] arr) {
        if (arr != null) {
            // TODO: possibly make this a deep copy?
            this.data = arr;
            this.size = arr.length;
        } else {
            this.data = new Object[DEFAULT_CAPACITY];
            this.size = EMPTY_SIZE;
        }
    }

    // MARK: extendCapacity
    /**
     * If the current capacity is non-zero, double the current capacity. If the
     * current capacity is 0, reset the capacity to the default capacity. If the
     * capacity is still not enough, then set the capacity to requiredCapacity.
     * 
     * @param requiredCapacity the minimum capacity for resized data
     */
    @Override
    public void expandCapacity(int requiredCapacity) {
        if (requiredCapacity < this.size) {
            throw new IllegalArgumentException();
        }

        // determine the newCapacity
        int currentCapacity = this.getCapacity();

        int newCapacity = (currentCapacity == 0) ? DEFAULT_CAPACITY
                : currentCapacity * CAPACITY_SCALE_FACTOR;
        newCapacity = (newCapacity < requiredCapacity) ? requiredCapacity
                : newCapacity;

        // copy the objects into the resized array
        Object[] resizedData = new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            resizedData[i] = this.data[i];
        }

        // update instance variables
        this.data = resizedData;
    }

    // MARK: getCapacity
    /**
     * Gets the current Capcity of the ArrayList
     * 
     * @return the current Capacity
     */
    @Override
    public int getCapacity() {
        return this.data.length;
    }

    // MARK: checkCapacity
    /**
     * checks whether the current list is full, and if so, calls the
     * expandCapacity function
     * 
     * @param newRequiredCapacity the requiredCapacity var to be passed to
     *                            expandCapacity
     */
    private void checkCapacity(int newRequiredCapacity) {
        if (this.size == this.getCapacity()) {
            this.expandCapacity(newRequiredCapacity);
        }
    }

    // MARK: insert
    /**
     * Inserts a new element at the specified index
     * 
     * @param index   the index to insert at
     * @param element the element to insert
     */
    @Override
    public void insert(int index, E element) {
        if (index < EMPTY_SIZE || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        checkCapacity(this.size + 1);

        // move elements past index to the right to make space
        for (int i = this.size - 1; i >= index; i--) {
            this.data[i + 1] = this.data[i];
        }

        // insert new element
        this.data[index] = element;
        this.size += 1;
    }

    // MARK: Append
    /**
     * Appends an element to the end of the list
     * 
     * @param element the element to append
     */
    @Override
    public void append(E element) {
        this.insert(this.size, element);
    }

    // MARK: prepend
    /**
     * prepends an element to the end of the list
     * 
     * @param element the element to preappend
     */
    @Override
    public void prepend(E element) {
        this.insert(0, element);
    }

    // MARK: get
    /**
     * gets the element at the specified index
     * 
     * @param index the index to fetch the element at
     * @return the element at the index
     */
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        if (index < EMPTY_SIZE || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        return (E) this.data[index];
    }

    // MARK: set
    /**
     * sets the element at the given index to the provided value
     * 
     * @param index   the index to set
     * @param element the new element
     * @return the previous value at the index
     */
    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        if (index < EMPTY_SIZE || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        E previousValue = (E) this.data[index];
        this.data[index] = element;

        return previousValue;
    }

    // MARK: remove
    /**
     * removes an element from the list
     * 
     * @param index the index to remove at
     * @return the removed element
     */
    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        if (index < EMPTY_SIZE || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        // capture the old value, and shift other values
        E removedValue = (E) this.data[index];
        for (int i = index + 1; i < this.size; i++) {
            this.data[i - 1] = this.data[i];
        }

        this.data[size - 1] = null;
        this.size -= 1;
        return removedValue;
    }

    // MARK: size
    /**
     * gets the current size of the arrayList This value represents how many
     * valid elements there are, not the capacity of the list
     * 
     * @return the current size
     */
    @Override
    public int size() {
        return this.size;
    }

    // MARK: Rotate
    /**
     * Rotates each element in the ArrayList right by numPositions amount.
     * numPositions must be within the bounds of the array
     * 
     * @param numPositions the number of postions to rotate by
     */
    @Override
    public void rotate(int numPositions) {
        if (numPositions < EMPTY_SIZE || numPositions > this.size) {
            throw new IndexOutOfBoundsException();
        }

        Object[] dataCopy = new Object[this.getCapacity()];

        // rotate each element into the new array
        for (int i = EMPTY_SIZE; i < this.size; i++) {
            int newIndex = (i + numPositions) % this.size;
            dataCopy[newIndex] = this.data[i];
        }

        this.data = dataCopy;
    }

    // MARK: Find
    /**
     * Finds and returns the index of the specified element if there are
     * multiple copies of the element, it returns the last one returns -1 if
     * there element is not found
     * 
     * @param element the element to find
     * @return the index of the element
     */
    @Override
    public int find(E element) {

        for (int i = this.size - 1; i >= 0; i--) {
            if (this.data[i].equals(element)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
}
