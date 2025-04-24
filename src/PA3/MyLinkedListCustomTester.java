import static org.junit.Assert.*;

import org.junit.*;

public class MyLinkedListCustomTester {

    @FunctionalInterface
    interface LinkedListOperation {
        void execute(MyLinkedList<Object> list);
    }

    private MyLinkedList<Integer> integerList;
    private MyLinkedList<Integer> copyIntegerList;
    private MyLinkedList<Integer> emptyList;

    private Object[] storedList;
    private int storedSize;

    private static final int IntegerListSize = 10;
    private static final int indexOfElementFail = -1;

    // MARK: Helper functions
    private void printLinkedList(MyLinkedList list) {
        String forwardStr = "";
        String backwardStr = "";

        MyLinkedList<Object>.Node currentNode = list.head;
        while (currentNode != null) {
            forwardStr += (currentNode.data + " -> ");
            backwardStr = (" <- " + currentNode.data) + backwardStr;
            currentNode = currentNode.next;
        }

        System.out.println(forwardStr);
        System.out.println(backwardStr);
    }

    private void printList(Object[] list) {
        String str = "";

        for (int i = 0; i < list.length; i++) {
            str += (list[i] + ", ");
        }

        System.out.println(str);
    }

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

    // MARK: ReverseTranslateList
    private Object[] reverseTranslateList(MyLinkedList<Object> list) {
        Object[] translatedObjects = new Object[list.size + 2];

        MyLinkedList<Object>.Node currentNode = list.tail;
        int i = list.size + 1;
        while (currentNode != null) {
            translatedObjects[i] = currentNode.data;
            currentNode = currentNode.prev;
            i--;
        }

        return translatedObjects;
    }

    // MARK: storeLinkedList
    private void storeLinkedList(MyLinkedList list) {
        this.storedList = translateList(list);
        this.storedSize = list.size;
    }

    private void storeList(Object[] list) {
        this.storedList = list;
        this.storedSize = list.length - 2;
    }

    // MARK: checkListCorrectness
    private void checkListCorrectness(MyLinkedList list) {
        assertEquals("Checking size correctness", storedSize, list.size);
        assertEquals("Checking dummyHead", null, list.head.data);
        assertEquals("Checking dummyTail", null, list.tail.data);

        Object[] translatedList = translateList(list);
        Object[] reverseTranslatedList = reverseTranslateList(list);
        assertArrayEquals("Checking LinkedList Equality", this.storedList,
                translatedList);
        assertArrayEquals("Checking Reverse LinkedList Equality",
                this.storedList, reverseTranslatedList);
    }

    // MARK: checkExceptionCorrectness
    private void checkExceptionCorrectness(boolean expected, boolean actual) {
        assertEquals("Checking Exceptio", expected, actual);
    }

    // MARK: testNullInput
    public void testNullInput(MyLinkedList list,
            LinkedListOperation operation) {
        boolean caughtException = false;
        this.storeLinkedList(list);

        // check passing in an invalid param
        try {
            operation.execute(list);
        } catch (NullPointerException e) {
            caughtException = true;
        }

        this.checkExceptionCorrectness(true, caughtException);
        this.checkListCorrectness(list);
    }

    // MARK: testIndexOutOfBounds
    public void testIndexOutOfBounds(MyLinkedList list,
            LinkedListOperation operation) {
        boolean caughtException = false;
        this.storeLinkedList(list);

        // check passing in an invalid param
        try {
            operation.execute(list);
        } catch (IndexOutOfBoundsException e) {
            caughtException = true;
        }

        this.checkExceptionCorrectness(true, caughtException);
        this.checkListCorrectness(list);
    }

    // MARK: Before
    /**
     * This sets up the test fixture. JUnit invokes this method before every
     * testXXX method. The @Before tag tells JUnit to run this method before
     * each test.
     */
    @Before
    public void setUp() throws Exception {
        // setup integerList
        this.integerList = new MyLinkedList<Integer>();
        for (int i = 0; i < MyLinkedListCustomTester.IntegerListSize; i++) {
            this.integerList.add(i);
        }

        this.copyIntegerList = new MyLinkedList<Integer>();
        for (int i = 0; i < MyLinkedListCustomTester.IntegerListSize; i++) {
            this.copyIntegerList.add(0);
        }

        this.emptyList = new MyLinkedList<Integer>();

    }

    // MARK: testCustomAdd
    /**
     * Aims to test the add(E data) method with a valid argument.
     */
    @Test
    public void testCustomAdd() {
        // check add on a null input
        this.testNullInput(integerList, (list) -> list.add(null));

        // check passing a series of valid params
        Object[] expectedOutput = new Object[102];
        for (int i = 0; i < 100; i++) {
            expectedOutput[i + 1] = i;
        }

        this.storeList(expectedOutput);
        for (int i = 10; i < 100; i++) {
            this.integerList.add(i);
        }

        this.checkListCorrectness(this.integerList);
    }

    // MARK: testCustomAddIdxToStart
    /**
     * Aims to test the add(int index, E data) method. Add a valid argument to
     * the beginning of MyLinkedList.
     */
    @Test
    public void testCustomAddIdxToStart() {
        // Check add on a null input
        this.testNullInput(integerList, (list) -> list.add(0, null));

        // check passing a series of valid params
        Object[] expectedOutput = new Object[MyLinkedListCustomTester.IntegerListSize
                + 10 + 2];
        for (int i = -10; i < 10; i++) {
            expectedOutput[i + 11] = i;
        }

        this.storeList(expectedOutput);
        for (int i = -1; i >= -10; i--) {
            this.integerList.add(0, i);
        }

        this.checkListCorrectness(this.integerList);
    }

    // MARK: testCustomAddIdxToMiddle
    /**
     * Aims to test the add(int index, E data) method. Add a valid argument to
     * the middle of MyLinkedList.
     */
    @Test
    public void testCustomAddIdxToMiddle() {
        // Check add on a null input
        this.testNullInput(copyIntegerList, (list) -> list.add(5, null));

        // check passsing a series of valid params
        int expectedOutputSize = MyLinkedListCustomTester.IntegerListSize * 2;
        Object[] expectedOutput = new Object[expectedOutputSize + 2];
        for (int i = 0; i < expectedOutputSize; i++) {
            expectedOutput[i + 1] = (i % 2 == 0) ? 0 : 1;
        }

        this.storeList(expectedOutput);
        for (int i = 10; i > 0; i--) {
            copyIntegerList.add(i, 1);
        }

        this.checkListCorrectness(copyIntegerList);

        // check adding to the middle of an emptyList
        this.testIndexOutOfBounds(emptyList, (list) -> list.add(1, 5));
        this.testIndexOutOfBounds(emptyList, (list) -> list.add(5, 5));
    }

    // MARK: testCustomRemoveFromEmpty
    /**
     * Aims to test the remove(int index) method. Remove from an empty list.
     */
    @Test
    public void testCustomRemoveFromEmpty() {
        // check removing from outside the bounds of the list
        this.testIndexOutOfBounds(integerList, (list) -> list.remove(-1));
        this.testIndexOutOfBounds(integerList,
                (list) -> list.remove(integerList.size));

        // check removing from an empty list
        this.testIndexOutOfBounds(emptyList, (list) -> list.remove(0));
        this.testIndexOutOfBounds(emptyList, (list) -> list.remove(1));
    }

    // MARK: testCustomRemoverFromMiddle
    /**
     * Aims to test the remove(int index) method. Remove a valid argument from
     * the middle of MyLinkedList.
     */
    @Test
    public void testCustomRemoveFromMiddle() {
        // check removing from outside the bounds of the list
        this.testIndexOutOfBounds(integerList, (list) -> list.remove(-1));
        this.testIndexOutOfBounds(integerList,
                (list) -> list.remove(integerList.size));

        // Test continuously removing from the end;
        Object[] expectedOutput = new Object[2];
        this.storeList(expectedOutput);

        int size = MyLinkedListCustomTester.IntegerListSize;
        for (int i = size - 1; i >= 0; i--) {
            int result = this.integerList.remove(i);
            assertEquals("checking remove return", i, result);
        }

        this.checkListCorrectness(emptyList);

        // Test removing from the middle
        Object[] expectedOutput2 = { null, 0, null };
        for (int i = 0; i < 9; i++) {
            int result = this.copyIntegerList.remove(1);
            assertEquals("checking remove return", 0, result);
        }

        this.storeList(expectedOutput2);
        this.checkListCorrectness(copyIntegerList);

    }

    // MARK: testCustomSetIdxOutOfBounds
    /**
     * Aims to test the set(int index, E data) method. Set an out-of-bounds
     * index with a valid data argument.
     */
    @Test
    public void testCustomSetIdxOutOfBounds() {
        int size = MyLinkedListCustomTester.IntegerListSize;
        this.testIndexOutOfBounds(integerList, (list) -> list.set(-1, 1));
        this.testIndexOutOfBounds(integerList, (list) -> list.set(size, 1));

        // check setting anything with an emptyList
        this.testIndexOutOfBounds(emptyList, (list) -> list.set(0, 1));
        this.testIndexOutOfBounds(emptyList, (list) -> list.set(1, 1));
    }

    // MARK: testCustomContainsExistsOutOfRange
    /**
     * Aims to test the contains(E data, int start, int end) method. Data
     * argument exists in the list but outside the given range.
     */
    @Test
    public void testCustomContainsExistsOutOfRange() {
        // check that the start argument is valid
        int size = MyLinkedListCustomTester.IntegerListSize;
        this.testIndexOutOfBounds(integerList,
                (list) -> list.contains(0, -1, 0));
        this.testIndexOutOfBounds(integerList,
                (list) -> list.contains(0, size, size));

        // check that the end argument is valid
        this.testIndexOutOfBounds(integerList,
                (list) -> list.contains(0, 0, -1));
        this.testIndexOutOfBounds(integerList,
                (list) -> list.contains(0, 0, size + 1));

        // check incorrect bounds
        this.storeLinkedList(integerList);
        boolean result = integerList.contains(0, 5, 4);

        assertEquals("Check contains result", false, result);
        this.checkListCorrectness(integerList);

        // check input in array, outside bounds
        result = integerList.contains(0, 1, size);

        assertEquals("Check contains result", false, result);
        this.checkListCorrectness(integerList);

        // check inptu in array, outside bounds
        result = integerList.contains(1, 0, 1);

        assertEquals("Check contains result", false, result);
        this.checkListCorrectness(integerList);
    }

    // MARK: testCustomIndexOfElementDoesNotExist
    /**
     * Aims to test the indexOfElement(E data) method. Data argument does not
     * exist in the list.
     */
    @Test
    public void testCustomIndexOfElementDoesNotExist() {
        // check null input
        this.testNullInput(integerList, (list) -> list.indexOfElement(null));

        // check data does not exist in the list
        int failResult = MyLinkedListCustomTester.indexOfElementFail;

        this.storeLinkedList(emptyList);
        int result = emptyList.indexOfElement(0);

        assertEquals("Check failed result", failResult, result);
        this.checkListCorrectness(emptyList);

        this.storeLinkedList(integerList);
        result = integerList.indexOfElement(10);

        assertEquals("Check failed result", failResult, result);
        this.checkListCorrectness(integerList);
    }
}
