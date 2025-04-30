/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA4 Write-up
   
  This file is for CSE 12 PA4 in Spring 2025,
*/

import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import org.junit.*;

/**
 * This class contains public test cases for MyListIterator. listLen1 is a
 * linkedlist of length 1 and listLen2 is a linkedlist of length 2.
 */
public class MyListIteratorCustomTester {

    // Defines a generic operation ona linkedList to make function calls easier
    @FunctionalInterface
    interface LinkedListOperation {
        void execute(MyLinkedList<Object>.MyListIterator iterator);
    }

    // MARK: Vars
    // LinkedList Vars
    private Object[] storedList;
    private int storedSize;

    // Iterator Vars
    private int storedIdx;
    private Object leftElement;
    private Object rightElement;

    private boolean storedForward;
    private boolean storedCanRemoveOrSet;

    private MyLinkedList<Integer> integerList;
    private MyLinkedList<Integer>.MyListIterator integerListIterator;

    private MyLinkedList<Object> emptyList;
    private MyLinkedList<Object>.MyListIterator emptyListIterator;

    // MARK: Static Vars
    private static final int integerListSize = 10;

    // MARK: translateList
    /**
     * Creates a standard Java array from a LinkedList instance. Uses the
     * forward traversal method to create the list
     * 
     * @param list the list to translate
     * @return the generated list
     */
    private Object[] translateList(MyLinkedList<Object> list) {
        Object[] translateObjects = new Object[list.size + 2];

        MyLinkedList<Object>.Node currentNode = list.head;
        int i = 0;
        while (currentNode != null) {
            translateObjects[i] = currentNode.data;
            currentNode = currentNode.next;
            i++;
        }

        return translateObjects;
    }

    // MARK: storeLinkedList
    /**
     * Copies a linkedList and stores its size and values into this class to
     * test
     * 
     * @param list the list to store
     */
    private void storeLinkedList(MyLinkedList list) {
        this.storedList = translateList(list);
        this.storedSize = list.size;
    }

    // MARK: storeIterator
    /**
     * Stores the instance variables / other data of an iterator into this class
     * to test
     * 
     * @param iterator the iterator to store
     */
    private void storeIterator(MyLinkedList.MyListIterator iterator) {
        this.leftElement = iterator.left.getElement();
        this.rightElement = iterator.right.getElement();
        this.storedIdx = iterator.idx;
        this.storedForward = iterator.forward;
        this.storedCanRemoveOrSet = iterator.canRemoveOrSet;
    }

    // MARK: vheckIteratorCorrectness
    /**
     * Checks if a given Iterator mathces what is stored in this class, by
     * checking the instance variables, left and right elements, and other data
     * 
     * @param iterator the iterator to check
     */
    private void checkIteratorCorrectness(
            MyLinkedList.MyListIterator iterator) {
        assertEquals("Checking Correct Left Node Element", leftElement,
                iterator.left.getElement());
        assertEquals("Checking correct Right Node", rightElement,
                iterator.right.getElement());

        assertEquals("Checking index", storedIdx, iterator.idx);
        assertEquals("Checking forward", storedForward, iterator.forward);
        assertEquals("Checking can Remove or Set", storedCanRemoveOrSet,
                iterator.canRemoveOrSet);
    }

    // MARK: checkListCorrectness
    /**
     * Checks if a given LinkedList mathces what is stored in this class. Checks
     * that the sentinels are correct, the size matches, and both forward and
     * backward traversed translations of the list are correct
     * 
     * @param list the list to check
     */
    private void checkListCorrectness(MyLinkedList list) {
        assertEquals("Checking size correctness", storedSize, list.size);
        assertEquals("Checking dummyHead", null, list.head.data);
        assertEquals("Checking dummyTail", null, list.tail.data);

        Object[] translatedList = translateList(list);
        assertArrayEquals("Checking LinkedList Equality", this.storedList,
                translatedList);
        // Object[] reverseTranslatedList = reverseTranslateList(list);
        // assertArrayEquals("Checking Reverse LinkedList Equality",
        // this.storedList, reverseTranslatedList);
    }

    // MARK: checkExceptionCorrectness
    /**
     * Checks whether a glaf representing wehther an exception was raised is
     * correct
     * 
     * @param expected the expected value of the flag
     * @param actual   the actual value of the flag
     */
    private void checkExceptionCorrectness(boolean expected, boolean actual) {
        assertEquals("Checking Exception", expected, actual);
    }

    // MARK: testExceptionRaised
    /**
     * Tests running a function on iterator that raises an exception. It both
     * tests that an exception was raised, and that the original iterator / list
     * was unmodified by the call
     * 
     * @param iterator      the iterator to test
     * @param list          the associated LinkedList to test
     * @param exceptionType the type of Exception that should be raised by the
     *                      operation
     * @param operation     the operation to perform in the try catch block
     */
    public void testExceptionRaised(MyLinkedList.MyListIterator iterator,
            MyLinkedList list, Class<?> exceptionType,
            LinkedListOperation operation) {
        boolean caughtException = false;
        this.storeIterator(iterator);
        this.storeLinkedList(list);

        // check passing in an invalid param
        try {
            operation.execute(iterator);
        } catch (Exception exception) {
            if (exception.getClass() == exceptionType) {
                caughtException = true;
            }
        }

        this.checkExceptionCorrectness(true, caughtException);
        this.checkIteratorCorrectness(iterator);
        this.checkListCorrectness(list);
    }

    // MARK: Setup
    /**
     * This sets up the test fixture. JUnit invokes this method before every
     * testXXX method. The @Before tag tells JUnit to run this method before
     * each test.
     */
    @Before
    public void setUp() throws Exception {
        // setup integerList
        this.integerList = new MyLinkedList<Integer>();
        this.intergerArray = new Integer[MyListIteratorCustomTester.integerListSize];
        for (int i = 0; i < MyListIteratorCustomTester.integerListSize; i++) {
            this.integerList.add(i);
            this.intergerArray[i] = i;
        }

        this.integerListIterator = this.integerList.new MyListIterator();

        // setup emptyList
        this.emptyList = new MyLinkedList<Object>();
        this.emptyListIterator = this.emptyList.new MyListIterator();
    }

    // MARK: testNextEnd
    /**
     * Aims to test the next() method when iterator is at end of the list
     */
    @Test
    public void testNextEnd() {
        int size = MyListIteratorCustomTester.integerListSize;

        // test an interator at the end of a full list
        for (int i = 0; i < size; i++) {
            integerListIterator.next();
        }

        this.testExceptionRaised(this.integerListIterator, this.integerList,
                NoSuchElementException.class, (iterator) -> iterator.next());

        // test an iterator at the end of an empty list
        this.testExceptionRaised(emptyListIterator, emptyList,
                NoSuchElementException.class, (iterator) -> iterator.next());
    }

    // MARK: testPreviousStart
    /**
     * Aims to test the previous() method when iterator is at the start of the
     * list
     */
    @Test
    public void testPreviousStart() {
        // test an iterator at the start of a full list
        this.testExceptionRaised(this.integerListIterator, this.integerList,
                NoSuchElementException.class,
                (iterator) -> iterator.previous());

        // test an iterator at the start of an emptyList
        this.testExceptionRaised(this.emptyListIterator, this.emptyList,
                NoSuchElementException.class,
                (iterator) -> iterator.previous());

    }

    // MARK: testAddInvalid
    /**
     * Aims to test the add(E e) method when an invalid element is added
     */
    @Test
    public void testAddInvalid() {
        // test add invalid element to start of list
        this.testExceptionRaised(this.integerListIterator, this.integerList,
                NullPointerException.class, (iterator) -> iterator.add(null));

        // test add invalid element to the middle of the list
        int size = MyListIteratorCustomTester.integerListSize;
        for (int i = 0; i < size / 2; i++) {
            this.integerListIterator.next();
        }

        this.testExceptionRaised(this.integerListIterator, this.integerList,
                NullPointerException.class, (iterator) -> iterator.add(null));

        // test add invalid element to the end of the list
        for (int i = size / 2; i < size; i++) {
            this.integerListIterator.next();
        }

        this.testExceptionRaised(this.integerListIterator, this.integerList,
                NullPointerException.class, (iterator) -> iterator.add(null));
    }

    // MARK: testCant
    /**
     * Tests running operations that depend on the canRemoveOrSet variable on an
     * iterator when it is set to false.
     * 
     * @param operation the operation being tested
     */
    private void testCant(LinkedListOperation operation) {
        // check that nothing is changed when simply set to false
        this.integerListIterator.canRemoveOrSet = false;

        this.testExceptionRaised(this.integerListIterator, this.integerList,
                IllegalStateException.class, operation);

        // check the exception is raised after failed calls to next / previous
        try {
            this.integerListIterator.previous();
        } catch (Exception e) {
        }

        this.testExceptionRaised(this.integerListIterator, this.integerList,
                IllegalStateException.class, operation);

        // check that the next call also does not mistakenly set the flag
        int size = MyListIteratorCustomTester.integerListSize;
        for (int i = 0; i < size; i++) {
            this.integerListIterator.next();
        }
        this.integerListIterator.canRemoveOrSet = false;
        try {
            this.integerListIterator.next();
        } catch (Exception e) {
        }

        this.testExceptionRaised(this.integerListIterator, this.integerList,
                IllegalStateException.class, operation);

    }

    // MARK: testCantSet
    /**
     * Aims to test the set(E e) method when canRemoveOrSet is false
     */
    @Test
    public void testCantSet() {
        this.testCant((iterator) -> iterator.set(5));
    }

    // MARK: testCantRemove
    /**
     * Aims to test the remove() method when canRemoveOrSet is false
     */
    @Test
    public void testCantRemove() {
        this.testCant((iterator) -> iterator.remove());
    }

    // MARK: testHasNextEnd
    /**
     * Aims to tests the hasNext() method at the end of a list
     */
    @Test
    public void testHasNextEnd() {
        // test hasNext at the end of a fullList
        int size = MyListIteratorCustomTester.integerListSize;
        for (int i = 0; i < size; i++) {
            this.integerListIterator.next();
        }

        this.storeLinkedList(integerList);
        this.storeIterator(integerListIterator);

        boolean result = this.integerListIterator.hasNext();

        assertEquals("Checking hasNext Correctness", false, result);
        this.checkListCorrectness(integerList);
        this.checkIteratorCorrectness(integerListIterator);

        // check hasNext at the end of an emptyList
        this.storeLinkedList(emptyList);
        this.storeIterator(emptyListIterator);

        result = this.emptyListIterator.hasNext();

        assertEquals("Checking hasNext Correctness", false, result);
        this.checkListCorrectness(emptyList);
        this.checkIteratorCorrectness(emptyListIterator);
    }

    // MARK: testHasPreviousStart
    /**
     * Aims to test the hasPrevious() method at the start of a list
     */
    @Test
    public void testHasPreviousStart() {
        // test hasNext at the end of a fullList
        this.storeLinkedList(integerList);
        this.storeIterator(integerListIterator);

        boolean result = this.integerListIterator.hasPrevious();

        assertEquals("Checking prev Correctness", false, result);
        this.checkListCorrectness(integerList);
        this.checkIteratorCorrectness(integerListIterator);

        // check hasNext at the end of an emptyList
        this.storeLinkedList(emptyList);
        this.storeIterator(emptyListIterator);

        result = this.emptyListIterator.hasPrevious();

        assertEquals("Checking prev Correctness", false, result);
        this.checkListCorrectness(emptyList);
        this.checkIteratorCorrectness(emptyListIterator);
    }

    /**
     * Aims to test the previousIndex() method at the start of a list
     */
    @Test
    public void testPreviousIndexStart() {
        // test previousIndex at the start of a full list
        this.storeLinkedList(this.integerList);
        this.storeIterator(this.integerListIterator);

        int result = this.integerListIterator.previousIndex();

        assertEquals("Checking prevIndex", -1, result);
        this.checkListCorrectness(integerList);
        this.checkIteratorCorrectness(integerListIterator);

        // test previousIndex at the start of an empty list
        this.storeLinkedList(this.emptyList);
        this.storeIterator(this.emptyListIterator);

        result = this.emptyListIterator.previousIndex();

        assertEquals("Checking prevIndex", -1, result);
        this.checkListCorrectness(emptyList);
        this.checkIteratorCorrectness(emptyListIterator);
    }

    /**
     * Aims to test the nextIndex() method at the end of a list
     */
    @Test
    public void testNextIndexEnd() {
        // test previousIndex at the start of a full list
        int size = MyListIteratorCustomTester.integerListSize;
        for (int i = 0; i < size; i++) {
            this.integerListIterator.next();
        }

        this.storeLinkedList(this.integerList);
        this.storeIterator(this.integerListIterator);

        int result = this.integerListIterator.nextIndex();

        assertEquals("Checking prevIndex", size, result);
        this.checkListCorrectness(integerList);
        this.checkIteratorCorrectness(integerListIterator);

        // test previousIndex at the start of an empty list
        this.storeLinkedList(this.emptyList);
        this.storeIterator(this.emptyListIterator);

        result = this.emptyListIterator.nextIndex();

        assertEquals("Checking prevIndex", 0, result);
        this.checkListCorrectness(emptyList);
        this.checkIteratorCorrectness(emptyListIterator);
    }
}
