/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA6 Write-up
   
  This file is for CSE 12 PA6 in Spring 2025,
*/

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

// MARK: CustomTester
/**
 * The custom tester for PA6, which covers some basic test cases.
 */
public class CustomTester {

    // Defines a generic operation on a HashMap to make function calls easier
    @FunctionalInterface
    interface DequeOperation {
        void execute(MyDeque deque);
    }

    // MARK: Vars
    private static final int DEFAULT_CAPACITY = 10;

    int storedCapacity;
    int storedSize;
    int storedFront;
    int storedRear;
    Object[] storedData;

    // deques
    MyDeque<Integer> deadDeque;
    MyDeque<Integer> emptyDeque;
    MyDeque<Integer> partialDeque;
    MyDeque<Integer> fullDeque;

    // stacks
    MyStack<Integer> emptyStack;
    MyStack<Integer> fullStack;

    // queues
    MyQueue<Integer> emptyQueue;
    MyQueue<Integer> fullQueue;

    // MARK: PrintArray
    /**
     * Creates and prints a string representation of an Array
     * 
     * @param arr the array to print
     */
    private void printArray(Object[] arr) {
        System.out.print("[");

        int capacity = arr.length;
        for (int i = 0; i < capacity; i++) {
            String str = arr[i] == null ? "Null" : arr[i].toString();
            System.out.print("" + str + ", ");
        }

        System.out.print("] \n");
    }

    // MARK: translateDeque
    /**
     * Conversts a Deque data structure into an array. This is a simple
     * conversion: it first copies all the elements into a new array, and then
     * rotates that new array by a given amount until it should match the
     * dequeu. This tests both that the values and the wrap are correct
     * 
     * @param deque the deque to translate
     * @return the translatedArray
     */
    private Object[] translateDeque(MyDeque deque) {
        int capacity = deque.data.length;
        Object[] translatedObjects = new Object[capacity];

        // copy the objects from the deque
        for (int i = 0; i < capacity; i++) {
            int index = (i + deque.front) % capacity;
            translatedObjects[i] = deque.data[index];
        }

        // rotate the objects to match the structure of the deque
        return rotateArray(translatedObjects, deque.front);
    }

    // MARK: RotateArray
    /**
     * Rotates an array and returns a copy
     * 
     * @param arr      the arr to rotate
     * @param rotation the amount to rotate by
     * @return the new, rotated arr
     */
    private Object[] rotateArray(Object[] arr, int rotation) {
        int capacity = arr.length;

        Object[] rotatedObjects = new Object[capacity];
        for (int i = 0; i < capacity; i++) {
            int index = (i + rotation) % capacity;
            rotatedObjects[index] = arr[i];
        }

        return rotatedObjects;
    }

    // MARK: CheckDeque
    /**
     * Tests all instance variables of a deque structure against the properties
     * stored in this class
     * 
     * @param deque the deque to check
     */
    private void checkDeque(MyDeque deque) {
        Object[] translatedObjects = translateDeque(deque);

        assertArrayEquals("Checking Data Correctness", this.storedData,
                translatedObjects);
        assertEquals("Checkng Front Correctness", this.storedFront,
                deque.front);
        assertEquals("Checkng Rear Correctness", this.storedRear, deque.rear);
        assertEquals("Checking Size Correctness", this.storedSize, deque.size);
        assertEquals("Checking Capacity Correctness", this.storedCapacity,
                deque.data.length);
    }

    // MARK: StoreDeque
    private void storeDeque(MyDeque deque) {
        Object[] translateObjects = translateDeque(deque);

        this.storedCapacity = deque.data.length;
        this.storedSize = deque.size;
        this.storedFront = deque.front;
        this.storedRear = deque.rear;
        this.storedData = translateObjects;
    }

    // MARK: StoreArray
    private void storeArray(Object[] arr, int front, int rear) {
        int capacity = arr.length;
        int size = 0;

        for (int i = 0; i < capacity; i++) {
            size += arr[i] == null ? 0 : 1;
        }

        this.storedCapacity = capacity;
        this.storedSize = size;
        this.storedData = arr;
        this.storedFront = front;
        this.storedRear = rear;
    }

    // MARK: testExceptionRaised
    /**
     * Tests that a generic function that should raise an exception, does raise
     * the correct type of exception
     * 
     * @param map           the map to perform the function on
     * @param exceptionType the expected type of exception the function raises
     * @param operation     the function to perform on the map
     */
    private void testExceptionRaised(MyDeque deque, Class<?> exceptionType,
            DequeOperation operation) {
        boolean caughtException = false;
        this.storeDeque(deque);

        // check passing in an invalid param
        try {
            operation.execute(deque);
        } catch (Exception exception) {
            if (exception.getClass() == exceptionType) {
                caughtException = true;
            }
        }

        assertTrue("Checking exception was Caught", caughtException);
        this.checkDeque(deque);
    }

    // MARK: Before
    @Before
    public void setup() {
        // setup deadDeque
        this.deadDeque = new MyDeque<>(0);

        // setup emptyDeque
        this.emptyDeque = new MyDeque<>(DEFAULT_CAPACITY);

        // setup partialDeque
        this.partialDeque = new MyDeque<>(DEFAULT_CAPACITY);
        Integer[] data = new Integer[DEFAULT_CAPACITY];
        data[7] = 0;
        data[8] = 1;
        data[9] = 2;

        this.partialDeque.data = data;
        this.partialDeque.size = 3;
        this.partialDeque.front = 7;
        this.partialDeque.rear = 9;

        // setup fullDeque
        data = new Integer[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            data[i] = i;
        }

        this.fullDeque = new MyDeque<>(DEFAULT_CAPACITY);
        this.fullDeque.data = data;
        this.fullDeque.size = DEFAULT_CAPACITY;
        this.fullDeque.front = 0;
        this.fullDeque.rear = 9;

        // setup emptyStack
        this.emptyStack = new MyStack<>(DEFAULT_CAPACITY);
        this.emptyStack.theStack = emptyDeque;

        // setup fullStack
        this.fullStack = new MyStack<>(DEFAULT_CAPACITY);
        this.fullStack.theStack = fullDeque;

        // setup empty Queue
        this.emptyQueue = new MyQueue<>(DEFAULT_CAPACITY);
        this.emptyQueue.theQueue = emptyDeque;

        // setup full Queue
        this.fullQueue = new MyQueue<>(DEFAULT_CAPACITY);
        this.fullQueue.theQueue = fullDeque;

    }

    // MARK: DequeueConstructuor
    @Test
    public void testNullConstructor() {
        boolean caughtException = false;

        try {
            new MyDeque<>(-1);
        } catch (IllegalArgumentException e) {
            caughtException = true;
        }

        assertTrue("Checking Exception Correctness", caughtException);
    }

    // MARK: Expand Capacity
    @Test
    public void testExpandCapacity() {
        // test a deque with capacity 0
        Integer[] expectedOutput = new Integer[DEFAULT_CAPACITY];
        this.storeArray(expectedOutput, 0, 0);

        this.deadDeque.expandCapacity();
        this.checkDeque(deadDeque);

        // test a deque with no elements
        expectedOutput = new Integer[DEFAULT_CAPACITY * 2];
        this.storeArray(expectedOutput, 0, 0);

        this.emptyDeque.expandCapacity();
        this.checkDeque(emptyDeque);

        // test a deque with elements
        expectedOutput = new Integer[DEFAULT_CAPACITY * 2];
        expectedOutput[0] = 0;
        expectedOutput[1] = 1;
        expectedOutput[2] = 2;
        this.storeArray(expectedOutput, 0, 2);

        this.partialDeque.expandCapacity();
        this.checkDeque(partialDeque);

        // test a full deque
        expectedOutput = new Integer[DEFAULT_CAPACITY * 2];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            expectedOutput[i] = i;
        }

        this.storeArray(expectedOutput, 0, 9);

        this.fullDeque.expandCapacity();
        this.checkDeque(fullDeque);
    }

    // MARK: addFirst
    @Test
    public void testAddFirst() {
        // test adding a null element to an empty deque
        testExceptionRaised(emptyDeque, NullPointerException.class,
                (deque) -> deque.addFirst(null));

        // test adding a null element to a full deque
        testExceptionRaised(fullDeque, NullPointerException.class,
                (deque) -> deque.addFirst(null));

        // test adding an element to a dead deque
        Integer[] expectedOutput = new Integer[DEFAULT_CAPACITY];
        expectedOutput[0] = 0;
        this.storeArray(expectedOutput, 0, 0);

        this.deadDeque.addFirst(0);
        this.checkDeque(deadDeque);

        // test adding multiple elements to an empty deque
        expectedOutput = new Integer[DEFAULT_CAPACITY];
        expectedOutput[0] = 0;
        expectedOutput[DEFAULT_CAPACITY - 1] = 1;
        expectedOutput[DEFAULT_CAPACITY - 2] = 2;
        this.storeArray(expectedOutput, DEFAULT_CAPACITY - 2, 0);

        for (int i = 0; i < 3; i++) {
            this.emptyDeque.addFirst(i);
        }

        this.checkDeque(emptyDeque);

        // testing adding an element to a full deque
        expectedOutput = new Integer[DEFAULT_CAPACITY * 2];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            expectedOutput[i] = i;
        }
        expectedOutput[DEFAULT_CAPACITY * 2 - 1] = DEFAULT_CAPACITY;
        this.storeArray(expectedOutput, DEFAULT_CAPACITY * 2 - 1,
                DEFAULT_CAPACITY - 1);

        fullDeque.addFirst(DEFAULT_CAPACITY);
        this.checkDeque(fullDeque);
    }

    // MARK: addLast
    @Test
    public void testAddLast() {
        // test adding a null element to an empty deque
        testExceptionRaised(emptyDeque, NullPointerException.class,
                (deque) -> deque.addLast(null));

        // test adding a null element to a full deque
        testExceptionRaised(fullDeque, NullPointerException.class,
                (deque) -> deque.addLast(null));

        // test adding an element to a dead deque
        Integer[] expectedOutput = new Integer[DEFAULT_CAPACITY];
        expectedOutput[0] = 0;
        this.storeArray(expectedOutput, 0, 0);

        this.deadDeque.addLast(0);
        this.checkDeque(deadDeque);

        // test adding multiple elements to a partial deque
        expectedOutput = new Integer[DEFAULT_CAPACITY];
        expectedOutput[7] = 0;
        expectedOutput[8] = 1;
        expectedOutput[9] = 2;

        expectedOutput[0] = 0;
        expectedOutput[1] = 1;
        expectedOutput[2] = 2;
        this.storeArray(expectedOutput, 7, 2);

        for (int i = 0; i < 3; i++) {
            this.partialDeque.addLast(i);
        }

        this.checkDeque(partialDeque);

        // testing adding an element to a full deque
        expectedOutput = new Integer[DEFAULT_CAPACITY * 2];
        for (int i = 0; i < DEFAULT_CAPACITY + 1; i++) {
            expectedOutput[i] = i;
        }
        this.storeArray(expectedOutput, 0, DEFAULT_CAPACITY);

        fullDeque.addLast(DEFAULT_CAPACITY);
        this.checkDeque(fullDeque);
    }

    // MARK: removeFirst
    @Test
    public void testRemoveFirst() {
        // remove from a dead queue
        this.storeDeque(deadDeque);
        Object result = this.deadDeque.removeFirst();

        assertEquals("Checking correct return", null, result);
        this.checkDeque(deadDeque);

        // remove from an empty queue
        this.storeDeque(emptyDeque);
        result = emptyDeque.removeFirst();

        assertEquals("Checking correct return", null, result);
        this.checkDeque(emptyDeque);

        // remove most elements from a full deque
        Integer[] expectedOutput = new Integer[DEFAULT_CAPACITY];
        expectedOutput[DEFAULT_CAPACITY - 1] = DEFAULT_CAPACITY - 1;
        this.storeArray(expectedOutput, DEFAULT_CAPACITY - 1,
                DEFAULT_CAPACITY - 1);

        for (int i = 0; i < DEFAULT_CAPACITY - 1; i++) {
            Object res = this.fullDeque.removeFirst();
            assertEquals("Checking correct return", i, res);
        }

        this.checkDeque(fullDeque);
    }

    // MARK: removeLast
    @Test
    public void testRemoveLast() {
        // remove from a dead queue
        this.storeDeque(deadDeque);
        Object result = this.deadDeque.removeLast();

        assertEquals("Checking correct return", null, result);
        this.checkDeque(deadDeque);

        // remove from an empty queue
        this.storeDeque(emptyDeque);
        result = emptyDeque.removeLast();

        assertEquals("Checking correct return", null, result);
        this.checkDeque(emptyDeque);

        // remove most elements from a full deque
        Integer[] expectedOutput = new Integer[DEFAULT_CAPACITY];
        expectedOutput[0] = 0;
        this.storeArray(expectedOutput, 0, 0);

        for (int i = 0; i < DEFAULT_CAPACITY - 1; i++) {
            Object res = this.fullDeque.removeLast();
            assertEquals("Checking correct return", DEFAULT_CAPACITY - i - 1,
                    res);
        }

        this.checkDeque(fullDeque);
    }

    // MARK: myStack
    @Test
    public void testMyStack() {
        // test invalid constructor
        boolean caughtException = false;
        try {
            new MyStack<>(-1);
        } catch (IllegalArgumentException e) {
            caughtException = true;
        }

        assertTrue("Checking Exception", caughtException);

        // test push on Empty Stack
        Object[] expectedOutput = new Object[DEFAULT_CAPACITY];
        expectedOutput[0] = 0;
        this.storeArray(expectedOutput, 0, 0);

        emptyStack.push(0);
        this.checkDeque(emptyStack.theStack);

        // test push on a Full Stack
        expectedOutput = new Object[DEFAULT_CAPACITY * 2];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            expectedOutput[i] = i;
        }
        expectedOutput[DEFAULT_CAPACITY * 2 - 1] = DEFAULT_CAPACITY;
        this.storeArray(expectedOutput, DEFAULT_CAPACITY * 2 - 1,
                DEFAULT_CAPACITY - 1);

        fullStack.push(DEFAULT_CAPACITY);
        this.checkDeque(fullStack.theStack);
    }

    @Test
    public void testPopMyStack() {
        // test remove on an empty stack
        this.storeDeque(emptyStack.theStack);
        Object result = this.emptyStack.pop();

        assertEquals("Checking return", null, result);
        this.checkDeque(this.emptyStack.theStack);

        // test remove on a full stack
        for (int i = 0; i < DEFAULT_CAPACITY - 1; i++) {
            Object expectedResult = fullStack.peek();
            result = fullStack.pop();
            assertEquals("Check return value", expectedResult, result);
        }

        assertEquals("Checking size", 1, fullStack.theStack.size);
        assertEquals("Checking front == rear", fullStack.theStack.front,
                fullStack.theStack.rear);
    }

    // MARK: myQueue
    @Test
    public void testMyQueue() {
        // test invalid constructor
        boolean caughtException = false;
        try {
            new MyQueue<>(-1);
        } catch (IllegalArgumentException e) {
            caughtException = true;
        }

        assertTrue("Checking Exception", caughtException);

        // test push on Empty Queue
        Object[] expectedOutput = new Object[DEFAULT_CAPACITY];
        expectedOutput[0] = 0;
        this.storeArray(expectedOutput, 0, 0);

        emptyQueue.enqueue(0);
        this.checkDeque(emptyQueue.theQueue);

        // test push on a Full Queue
        expectedOutput = new Object[DEFAULT_CAPACITY * 2];
        for (int i = 0; i < DEFAULT_CAPACITY + 1; i++) {
            expectedOutput[i] = i;
        }
        this.storeArray(expectedOutput, 0, DEFAULT_CAPACITY);

        fullQueue.enqueue(DEFAULT_CAPACITY);
        this.checkDeque(fullQueue.theQueue);
    }

    @Test
    public void testDequeueMyQueue() {
        // test remove on an empty Queue
        this.storeDeque(emptyQueue.theQueue);
        Object result = this.emptyQueue.dequeue();

        assertEquals("Checking return", null, result);
        this.checkDeque(this.emptyQueue.theQueue);

        // test remove on a full Queue
        Object[] expectedOutput = new Object[DEFAULT_CAPACITY];
        expectedOutput[DEFAULT_CAPACITY - 1] = DEFAULT_CAPACITY - 1;
        this.storeArray(expectedOutput, DEFAULT_CAPACITY - 1,
                DEFAULT_CAPACITY - 1);

        for (int i = 0; i < DEFAULT_CAPACITY - 1; i++) {
            result = fullQueue.dequeue();

            assertEquals("Check return value", i, result);
        }

        this.checkDeque(fullQueue.theQueue);
    }

    // MARK: MyAlgorithm
    @Test
    public void testAlgorithm() {
        // test emtpy string
        String input = "";
        boolean result = MyAlgorithm.isValidBrackets(input);

        assertTrue("Testing Empty String", result);

        // test string without brackets
        input = "Hello World! This has no Brackets";
        result = MyAlgorithm.isValidBrackets(input);

        assertTrue("Test string Without Brackets", result);

        // Check Even, but out of order Brackets
        input = "((([[{{{{]])))}}}}";
        result = MyAlgorithm.isValidBrackets(input);

        assertTrue("Test even, but out of order Brackets", !result);

        // Check incorrect number of brackets
        input = "(({{[[]}}))";
        result = MyAlgorithm.isValidBrackets(input);

        assertTrue("Test incorrect number of brackets", !result);

        // Check correct brackets with additional text
        input = "((hi thre {this [should still work despite(( being full {[{";
        String input2 = "[ of text!!]}]})and) theres] some} text) here) too!";
        result = MyAlgorithm.isValidBrackets(input + input2);
        assertTrue("Test correct brackets with text", result);

    }

}