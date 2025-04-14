/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA2 Write-up
   
  This file is for CSE 12 PA1 in Spring 2025,
*/

import static org.junit.Assert.*;
import org.junit.*;

// MARK: MyArrayListHiddenTester
/**
 * This class tests special cases of the MyArrayListClass Most of the tests
 * focus on edge cases and exception handling
 */
public class MyArrayListHiddenTester {

    private int storedCapacity;
    private int storedSize;
    private Object[] storedData;

    private MyArrayList<Integer> fullList, largeList, repeatedElement;
    private MyArrayList<Object> fullObjectList;

    protected static final int LARGE_LIST_SIZE = 1024;
    protected static final int FINAL_INDEX_REPEATED_FIND = 4;
    protected static final int DEFAULT_CAPACITY = 5;
    protected static final int INDEX_NOT_FOUND = -1;

    // MARK: Before
    /**
     * This sets up the test fixture. JUnit invokes this method before every
     * testXXX method. The @Before tag tells JUnit to run this method before
     * each test
     */
    @Before
    public void setUp() throws Exception {

        Integer[] fullArray = new Integer[] { 1, 2, 3, 4, 5 };
        fullList = new MyArrayList<Integer>(fullArray);

        Object[] fullArray2 = new Object[8];
        fullObjectList = new MyArrayList<Object>(fullArray2);

        Integer[] largeArray = new Integer[LARGE_LIST_SIZE];
        for (int i = 0; i < 1000; i++) {
            largeArray[i] = i;
        }

        largeList = new MyArrayList<Integer>(largeArray);

        Integer[] repeatedElementArray = new Integer[] { 0, 1, 2, 2, 2, 3, 4,
                5 };
        repeatedElementArray[FINAL_INDEX_REPEATED_FIND] = 2;
        repeatedElement = new MyArrayList<Integer>(repeatedElementArray);
    }

    // MARK: storeListInformation
    /**
     * Stores the capacity, size, and data of the given list in instance
     * variables in the class Is used to quickly test these three parameters
     * 
     * @param list the arraylist to store the values of
     */
    private void storeListInformation(MyArrayList list) {
        this.storedCapacity = list.data.length;
        this.storedSize = list.size;
        this.storedData = list.data;
    }

    // MARK: testArrayAgainstStoredInfo
    /**
     * Checks the capacity, size, and data of the list against the stored values
     * 
     * @param list the arraylist to test
     */
    private void testArrayAgainstStoredInfo(MyArrayList list) {
        assertEquals("Checking Capacity", this.storedCapacity,
                list.data.length);
        assertEquals("Checking Size", this.storedSize, list.size);
        assertArrayEquals("Checking data", this.storedData, list.data);
    }

    // MARK: testConstructorInvalidArg
    /**
     * Aims to test the constructor when the input argument is not valid
     */
    @Test
    public void testConstructorInvalidArg() {
        boolean caughtException = false;
        try {
            MyArrayList<Integer> temp = new MyArrayList<Integer>(-5);
        } catch (IllegalArgumentException e) {
            caughtException = true;
        }

        assertEquals("Check invalid input on 2nd constructor", true,
                caughtException);
    }

    // MARK: testConstructorNullArg
    /**
     * Aims to test the constructor when the input argument is null
     */
    @Test
    public void testConstructorNullArg() {
        this.storedCapacity = DEFAULT_CAPACITY;
        this.storedSize = 0;
        this.storedData = new Object[DEFAULT_CAPACITY];

        MyArrayList<Integer> list1 = new MyArrayList<>(null);

        this.testArrayAgainstStoredInfo(list1);
    }

    // MARK: testConstructorArrayWithNull
    /**
     * Aims to test the constructor when the input array has null elements
     */
    @Test
    public void testConstructorArrayWithNull() {
        Object[] arr = new Object[LARGE_LIST_SIZE];

        this.storedCapacity = LARGE_LIST_SIZE;
        this.storedSize = LARGE_LIST_SIZE;
        this.storedData = arr;

        MyArrayList<Object> list2 = new MyArrayList<>(arr);

        this.testArrayAgainstStoredInfo(list2);
    }

    // MARK: testAppendAtCapacity
    /**
     * Aims to test the append method when an element is added to a full list
     * Check reflection on size and capacity
     */
    @Test
    public void testAppendAtCapacity() {
        Integer fullListCapacity = fullList.size;
        this.storedCapacity = fullListCapacity * 2;
        this.storedSize = fullListCapacity + 1;

        // create the expectedArray
        Integer[] resultingList = new Integer[fullListCapacity * 2];
        for (int i = 0; i < fullListCapacity; i++) {
            resultingList[i] = (Integer) fullList.data[i];
        }
        resultingList[5] = 6;
        this.storedData = resultingList;

        fullList.append(6);

        this.testArrayAgainstStoredInfo(fullList);
    }

    // MARK: testAppendNull
    /**
     * Aims to test the append method when null is added to a full list Check
     * reflection on size and capacity
     */
    @Test
    public void testAppendNull() {
        int fullListCapacity = fullObjectList.size;
        this.storedCapacity = fullListCapacity * 2;
        this.storedSize = fullListCapacity + 1;
        this.storedData = new Object[fullListCapacity * 2];

        fullObjectList.append(null);

        this.testArrayAgainstStoredInfo(fullObjectList);
    }

    // MARK: testPrependAtCapacity
    /**
     * Aims to test the prepend method when an element is added to a full list
     * Check reflection on size and capacity
     */
    @Test
    public void testPrependAtCapacity() {
        int fullListCapacity = fullList.size;
        this.storedCapacity = fullListCapacity * 2;
        this.storedSize = fullListCapacity + 1;

        Integer[] expectedArray = new Integer[fullListCapacity * 2];
        for (int i = 0; i < fullListCapacity; i++) {
            expectedArray[i + 1] = (Integer) fullList.data[i];
        }
        expectedArray[0] = 6;

        this.storedData = expectedArray;

        fullList.prepend(6);

        this.testArrayAgainstStoredInfo(fullList);
    }

    // MARK: testPrependNull
    /**
     * Aims to test the prepend method when a null element is added Checks
     * reflection on size and capacity Checks whether null was added
     * successfully
     */
    @Test
    public void testPrependNull() {
        int fullListCapacity = fullObjectList.size;
        this.storedCapacity = fullListCapacity * 2;
        this.storedSize = fullListCapacity + 1;
        this.storedData = new Object[fullListCapacity * 2];

        fullObjectList.prepend(null);

        testArrayAgainstStoredInfo(fullObjectList);
    }

    // MARK: testInsertOutOfBounds
    /**
     * Aims to test the insert method when input index is out of bounds
     */
    @Test
    public void testInsertOutOfBounds() {
        // test inserting above the bounds
        this.storeListInformation(fullList);
        boolean exceptionRaised = false;
        try {
            fullList.insert(fullList.size + 1, 0);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised above bounds", true,
                exceptionRaised);

        // test insertting below bounds
        this.storeListInformation(fullList);
        exceptionRaised = false;
        try {
            fullList.insert(-1, 0);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised blow bounds", true,
                exceptionRaised);
    }

    // MARK: testInsertMultiple
    /**
     * Insert multiple (eg. 1000) elements sequentially beyond capacity - Check
     * reflection on size and capacity Hint: for loop could come in handy
     */
    @Test
    public void testInsertMultiple() {
        Integer[] startingArray = new Integer[] { 0, 999 };
        MyArrayList<Integer> testList = new MyArrayList<Integer>(startingArray);

        for (int i = 998; i > 0; i--) {
            testList.insert(1, i);
        }

        assertEquals("check capacity", 1024, testList.data.length);
        assertEquals("Check size", 1000, testList.size);
        assertArrayEquals("check array", largeList.data, testList.data);
    }

    // MARK: testGetOutOfBound
    /**
     * Aims to test the get method when input index is out of bound
     */
    @Test
    public void testGetOutOfBound() {
        // test getting above the bounds
        this.storeListInformation(fullList);
        boolean exceptionRaised = false;
        try {
            fullList.get(fullList.size + 1);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised above bounds", true,
                exceptionRaised);

        // test getting below bounds
        this.storeListInformation(fullList);

        exceptionRaised = false;
        try {
            fullList.get(-1);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        assertEquals("check exception raised blow bounds", true,
                exceptionRaised);
        this.testArrayAgainstStoredInfo(fullList);
    }

    // MARK: testSetOutOfBound
    /**
     * Aims to test the set method when input index is out of bound
     */
    @Test
    public void testSetOutOfBound() {
        /// test setting above the bounds
        this.storeListInformation(fullList);
        boolean exceptionRaised = false;
        try {
            fullList.set(fullList.size + 1, 0);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised above bounds", true,
                exceptionRaised);

        // test setting below bounds
        this.storeListInformation(fullList);

        exceptionRaised = false;
        try {
            fullList.set(-1, 0);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised blow bounds", true,
                exceptionRaised);
    }

    // MARK: testRemoveMultiple
    /**
     * Aims to test the remove method when removing multiple items from a list
     */
    @Test
    public void testRemoveMultiple() {
        Integer[] expectedArray = new Integer[LARGE_LIST_SIZE];

        for (int i = 0; i < LARGE_LIST_SIZE; i++) {
            largeList.remove(0);
        }

        assertEquals("check capacity", 1024, largeList.getCapacity());
        assertEquals("check size", 0, largeList.size);
        assertArrayEquals("check data", expectedArray, largeList.data);

    }

    // MARK: testRemoveOutOfBound
    /**
     * Aims to test the remove method when input index is out of bound
     */
    @Test
    public void testRemoveOutOfBound() {
        /// test remove above the bounds
        this.storeListInformation(fullList);
        boolean exceptionRaised = false;
        try {
            fullList.remove(fullList.size + 1);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised above bounds", true,
                exceptionRaised);

        // test remove below bounds
        this.storeListInformation(fullList);

        exceptionRaised = false;
        try {
            fullList.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised blow bounds", true,
                exceptionRaised);
    }

    // MARK: testExpandCapacitySmaller
    /**
     * Aims to test the expandCapacity method when requiredCapacity is strictly
     * less than the current capacity
     */
    @Test
    public void testExpandCapacitySmaller() {
        /// test remove above the bounds
        this.storeListInformation(fullList);
        boolean exceptionRaised = false;
        try {
            fullList.expandCapacity(0);
            ;
        } catch (IllegalArgumentException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised above bounds", true,
                exceptionRaised);
    }

    /**
     * Aims to test the expandCapacity method when requiredCapacity is greater
     * than current capacity * 2 and default capacity
     */
    @Test
    public void testExpandCapacityLarge() {
        this.storedCapacity = LARGE_LIST_SIZE;
        this.storedSize = fullObjectList.size;
        this.storedData = new Object[LARGE_LIST_SIZE];

        fullObjectList.expandCapacity(LARGE_LIST_SIZE);

        this.testArrayAgainstStoredInfo(fullObjectList);
    }

    // MARK: testRotateOutOfBound
    /**
     * Aims to test the rotate method when input numPositions is out of bounds
     */
    @Test
    public void testRotateOutOfBound() {
        /// test rotate above the bounds
        this.storeListInformation(fullList);
        boolean exceptionRaised = false;
        try {
            fullList.rotate(fullList.size + 1);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised above bounds", true,
                exceptionRaised);

        // test remove below bounds
        this.storeListInformation(fullList);
        exceptionRaised = false;
        try {
            fullList.rotate(-1);
        } catch (IndexOutOfBoundsException e) {
            exceptionRaised = true;
        }

        this.testArrayAgainstStoredInfo(fullList);
        assertEquals("check exception raised blow bounds", true,
                exceptionRaised);
    }

    // MARK: testFindMultiple
    /**
     * Aims to test the find method when there are multiple of the input element
     */
    @Test
    public void testFindMultiple() {
        this.storeListInformation(repeatedElement);

        int index = repeatedElement.find(2);

        assertEquals("checking found index", FINAL_INDEX_REPEATED_FIND, index);
        this.testArrayAgainstStoredInfo(repeatedElement);
    }

    // MARK: testFindDoesNotExist
    /**
     * Aims to test the find method when input element does not exist in the
     * list
     */
    @Test
    public void testFindDoesNotExist() {
        this.storeListInformation(repeatedElement);

        int index = repeatedElement.find(LARGE_LIST_SIZE);

        assertEquals("checking found index", INDEX_NOT_FOUND, index);
        this.testArrayAgainstStoredInfo(repeatedElement);
    }

}
