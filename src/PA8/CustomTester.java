/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA8 Write-up
   
  This file is for CSE 12 PA8 in Spring 2025,
*/

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

// MARK: CustomTester
/**
 * This class contains test cases for the BST data struct
 * 
 * It contains standard functions including insert, remove, search
 */
public class CustomTester {

    /**
     * Defines a generic operation on a HashMap to make function calls easier
     */
    @FunctionalInterface
    interface BSTOperation {
        /**
         * Generic operation taking in a deque object and running some code
         * 
         * @param BST the BST to run the function on
         */
        void execute(MyBST BST);
    }

    // MARK: PrintList
    /**
     * Prints a generic array list
     * 
     * @param list the array list to print
     * @param <E>  the generic type for the array
     */
    private <E> void printList(ArrayList<E> list) {
        System.out.print("[");
        int size = list.size();

        for (int i = 0; i < size; i++) {
            E obj = list.get(i);
            String objString = obj == null ? "null" : obj.toString();

            System.out.print("(" + objString + "), ");
        }

        System.out.print("]\n");
    }

    // MARK: BST to Array
    /**
     * Converts a BST into an array. It scans thorugh level by level adding each
     * node to the list. If a node only has one child a null is inserted to
     * maintain the heirarchy
     * 
     * @param BST the BST to convert
     * @return the array representation of the BST
     */
    private ArrayList<MyBST.MyBSTNode> convertBSTToArray(MyBST BST) {
        ArrayList<MyBST.MyBSTNode> convertedArray = new ArrayList<>();

        ArrayList<MyBST.MyBSTNode> queue = new ArrayList<>();

        queue.add(BST.root);

        while (!queue.isEmpty()) {
            // add the currentNode to the array
            MyBST.MyBSTNode currentNode = queue.remove(0);
            convertedArray.add(currentNode);

            if (currentNode == null) {
                continue;
            }

            // add its children to the queue
            MyBST.MyBSTNode left = currentNode.getLeft();
            MyBST.MyBSTNode right = currentNode.getRight();

            if (left != null || right != null) {
                queue.add(left);
                queue.add(right);
            }
        }

        return convertedArray;
    }

    // MARK: Array to BST
    /**
     * Converts an array into a BST. Takes the same schema of array as BST to
     * array, and repeatedly inserts the elements to build a new BST. The nodes
     * in the array and the BST are linked
     * 
     * @param array the array to convert
     * @param <E>   the generic type for the array
     * @return the BST representation of the array
     */
    private <E extends Comparable<E>> MyBST<E, E> convertArrayToBST(
            ArrayList<E> array) {
        int size = array.size();
        MyBST<E, E> BST = new MyBST<>();
        ArrayList<MyBST.MyBSTNode<E, E>> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            E obj = array.get(i);
            BST.insert(obj, obj);
        }

        return BST;

    }

    // MARK: makeList
    /**
     * Converts a java array to an arrayList
     * 
     * @param list the list to convert
     * @return the arrayList representatin of array
     */
    private ArrayList<Integer> makeList(Integer[] list) {
        return new ArrayList<>(Arrays.asList(list));
    }

    // MARK: Check BST correctness
    /**
     * Checks whether the nodes + instance variables of a BST data structure are
     * correct
     * 
     * @param BST the BST to check
     */
    private void checkBSTCorrectness(MyBST BST) {
        // check the BST has correct size
        assertEquals("Checking the correctness of the size", storedSize,
                BST.size);

        ArrayList<MyBST.MyBSTNode> convertedList = convertBSTToArray(BST);
        int size = convertedList.size();

        // check the converted arrays have matching sizes
        assertEquals("Checking converted Array Size correctness",
                this.storedList.size(), size);

        // Check each individual object in the converted array
        for (int i = 0; i < size; i++) {
            MyBST.MyBSTNode convertedObject = convertedList.get(i);
            MyBST.MyBSTNode expectedObject = storedList.get(i);

            assertEquals("Checking Object Correctness", expectedObject,
                    convertedObject);
        }
    }

    // MARK: testExceptionRaised
    /**
     * Tests that a generic function that should raise an exception, does raise
     * the correct type of exception
     * 
     * @param BST           the BST to perform the function on
     * @param exceptionType the expected type of exception the function raises
     * @param operation     the function to perform on the BST
     */
    private void testExceptionRaised(MyBST BST, Class<?> exceptionType,
            BSTOperation operation) {
        boolean caughtException = false;
        this.storeBST(BST);

        // check passing in an invalid param
        try {
            operation.execute(BST);
        } catch (Exception exception) {
            if (exception.getClass() == exceptionType) {
                caughtException = true;
            }
        }

        assertTrue("Checking exception was Caught", caughtException);
        this.checkBSTCorrectness(BST);
    }

    // MARK: storeBST
    /**
     * Converts a BST into an array and stores it in this class with its size.
     * 
     * @param BST The BST to store
     */
    private void storeBST(MyBST BST) {
        this.storedList = convertBSTToArray(BST);
        this.storedSize = BST.size;
    }

    // MARK: storeList
    /**
     * Copies the elements of an array into a list of Nodes and stores it + the
     * size in this class
     * 
     * @param list The list to store
     */
    private void storeList(Integer[] list) {
        ArrayList<MyBST.MyBSTNode> nodes = new ArrayList<>();
        int size = list.length;
        int nonNullSize = 0;
        for (int i = 0; i < size; i++) {
            Integer obj = list[i];
            if (obj == null) {
                nodes.add(null);
                continue;
            }

            MyBST.MyBSTNode newNode = new MyBST.MyBSTNode(obj, obj, null);
            nodes.add(newNode);
            nonNullSize++;
        }

        this.storedList = nodes;
        this.storedSize = nonNullSize;
    }

    // MARK: Instance Variables
    ArrayList<MyBST.MyBSTNode> emptyBSTList;
    MyBST<Integer, Integer> emptyBST;

    ArrayList<MyBST.MyBSTNode> fullBSTList;
    MyBST<Integer, Integer> fullBST;

    ArrayList<MyBST.MyBSTNode> lineBSTList;
    MyBST<Integer, Integer> lineBST;

    ArrayList<MyBST.MyBSTNode> rootBSTList;
    MyBST<Integer, Integer> rootBST;

    // stored values
    ArrayList<MyBST.MyBSTNode> storedList;
    int storedSize;

    // MARK: Setup
    /**
     * Sets up the various BST used for test
     */
    @Before
    public void setup() {
        // setupEmptyBST
        this.emptyBSTList = new ArrayList<>();
        this.emptyBST = new MyBST<>();

        // setup fullBST
        Integer[] fullBSTListConstructor = { 8, 3, 10, 1, 6, 14, 4, 7, 13 };
        this.fullBST = convertArrayToBST(makeList(fullBSTListConstructor));
        this.fullBSTList = convertBSTToArray(fullBST);

        // setupLineBST
        Integer[] lineBSTListConstructor = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        this.lineBST = convertArrayToBST(makeList(lineBSTListConstructor));
        this.lineBSTList = convertBSTToArray(lineBST);

        // setup rootBST
        Integer[] rootBSTListConstructor = { 10 };
        this.rootBST = convertArrayToBST(makeList(rootBSTListConstructor));
        this.rootBSTList = convertBSTToArray(rootBST);
    }

    // MARK: testSuccessorFromBottom
    /**
     * Tests finding a successor of a given element of a BST
     * 
     * @param BST               the BST to check
     * @param BSTList           an array with references to the BSTs nodes
     * @param index             the index of the node to check
     * @param expectedSuccessor the expectedOutcome of the successor function
     */
    private void testSuccessor(MyBST BST, ArrayList<MyBST.MyBSTNode> BSTList,
            int index, Integer expectedSuccessor) {
        storeBST(BST);
        MyBST.MyBSTNode element = BSTList.get(index);
        MyBST.MyBSTNode successor = element.successor();

        if (expectedSuccessor == null) {
            assertNull("Checking Successor Correctness", successor);
        } else {
            assertEquals("Checking Successor Correctness", expectedSuccessor,
                    successor.getKey());
        }

        checkBSTCorrectness(BST);
    }

    // MARK: testSuccessor
    /**
     * tests the different BSTs for their successors at various values
     */
    @Test
    public void testSuccessor() {
        // fullBST
        int index = fullBSTList.size() - 2;
        testSuccessor(fullBST, fullBSTList, index, 14);

        testSuccessor(fullBST, fullBSTList, 0, 10);

        testSuccessor(fullBST, fullBSTList, 1, 4);

        testSuccessor(fullBST, fullBSTList, 6, null);

        // lineBST
        index = lineBSTList.size() - 1;
        testSuccessor(lineBST, lineBSTList, index, null);

        testSuccessor(lineBST, lineBSTList, 0, 1);

        // rootBST
        testSuccessor(rootBST, rootBSTList, 0, null);
    }

    // MARK: testInsertHelper
    /**
     * A convenience function for testing the insert function
     * 
     * @param expectedOutput the array represntation of the outcome BST
     * @param expectedReturn the expectedReturn of the function call
     * @param insertKey      the key to inesrt
     * @param insertValue    the value to insert
     * @param BST            the BST to act on
     */
    private void testInsertHelper(Integer[] expectedOutput,
            Integer expectedReturn, Integer insertKey, Integer insertValue,
            MyBST BST) {
        this.storeList(expectedOutput);

        // if the insertValue != insertKey, find the corresponding node in the
        // storedList and update it
        if (insertValue != insertKey) {
            int size = storedList.size();
            for (int i = 0; i < size; i++) {
                MyBST.MyBSTNode node = storedList.get(i);
                if (node == null) {
                    continue;
                }

                if (node.getKey().equals(insertKey)) {
                    node.setValue(insertValue);
                    break;
                }
            }
        }

        Integer output = (Integer) BST.insert(insertKey, insertValue);

        assertEquals("Check Correct Return", expectedReturn, output);
        this.checkBSTCorrectness(BST);
    }

    // MARK: testSmallestInsert
    /**
     * tests inserting a new element thats smaller than the rest
     */
    @Test
    public void testSmallestInsert() {
        // emptyBST
        Integer[] expectedOutput = { -1 };
        int value = -1;
        testInsertHelper(expectedOutput, null, value, value, emptyBST);

        // fullBST
        Integer[] expectedOutput2 = { 8, 3, 10, 1, 6, null, 14, -1, null, 4, 7,
                13, null };
        testInsertHelper(expectedOutput2, null, value, value, fullBST);

        // lineBST
        Integer[] expectedOutput3 = { 0, -1, 1, null, 2, null, 3, null, 4, null,
                5, null, 6, null, 7, null, 8, null, 9 };
        testInsertHelper(expectedOutput3, null, value, value, lineBST);

        // rootBST
        Integer[] expectedOutput4 = { 10, -1, null };
        testInsertHelper(expectedOutput4, null, value, value, rootBST);
    }

    // MARK: testLargestInsert
    /**
     * tests inserting a new element thats larger than the rest
     */
    @Test
    public void testLargestInsert() {
        // fullBST
        Integer[] expectedOutput2 = { 8, 3, 10, 1, 6, null, 14, 4, 7, 13, 15 };
        int value = 15;
        testInsertHelper(expectedOutput2, null, value, value, fullBST);

        // lineBST
        Integer[] expectedOutput3 = { 0, null, 1, null, 2, null, 3, null, 4,
                null, 5, null, 6, null, 7, null, 8, null, 9, null, 15 };
        testInsertHelper(expectedOutput3, null, value, value, lineBST);

        // rootBST
        Integer[] expectedOutput4 = { 10, null, 15 };
        testInsertHelper(expectedOutput4, null, value, value, rootBST);
    }

    // MARK: testReplacementInsert
    /**
     * tests inserting a new element thats already in the BST
     */
    @Test
    public void testReplacementInsert() {
        // fullBST
        Integer[] expectedOutput2 = { 8, 3, 10, 1, 6, null, 14, 4, 7, 13,
                null };
        int updatedValue = 100;
        testInsertHelper(expectedOutput2, 8, 8, 100, fullBST);

        // lineBST
        Integer[] expectedOutput3 = { 0, null, 1, null, 2, null, 3, null, 4,
                null, 5, null, 6, null, 7, null, 8, null, 9 };
        testInsertHelper(expectedOutput3, 9, 9, 100, lineBST);

        // rootBST
        Integer[] expectedOutput4 = { 10 };
        testInsertHelper(expectedOutput4, 10, 10, 100, rootBST);

    }

    // MARK: testNullInsertion
    /**
     * tests inserting a new element with a null key
     */
    @Test
    public void testNullInsertion() {
        // fullBST
        testExceptionRaised(fullBST, NullPointerException.class,
                (BST) -> BST.insert(null, 10));

        // emptyBST
        testExceptionRaised(emptyBST, NullPointerException.class,
                (BST) -> BST.insert(null, null));

        // lineBST
        testExceptionRaised(lineBST, NullPointerException.class,
                (BST) -> BST.insert(null, -10));

        // rootBST
        testExceptionRaised(rootBST, NullPointerException.class,
                (BST) -> BST.insert(null, 10));
    }

    // MARK: testSearchHelper
    /**
     * A convenience function to help with testing the search function
     * 
     * @param BST            the BST to test on
     * @param element        the element to search for
     * @param expectedOutput the expected return of the function
     */
    private void testSearchHelper(MyBST BST, Integer element,
            Integer expectedOutput) {
        this.storeBST(BST);

        Integer output = (Integer) BST.search(element);

        assertEquals("Checking correctness of output", expectedOutput, output);

        this.checkBSTCorrectness(BST);

    }

    // MARK: testFindLastElement
    /**
     * Tests finding an element at the bottom of the BST
     */
    @Test
    public void testFindLastElement() {
        // emptyBST
        testSearchHelper(emptyBST, 10, null);

        // fullBST
        testSearchHelper(fullBST, 13, 13);

        // lineBST
        testSearchHelper(lineBST, 9, 9);

        // rootBST
        testSearchHelper(rootBST, 10, 10);
    }

    // MARK: testFindNonExistentElement
    /**
     * Tests finding an element not in the BST
     */
    @Test
    public void testFindNonExistentElement() {
        Integer expectedOutput = null;
        // emptyBST
        testSearchHelper(emptyBST, -5, expectedOutput);

        // fullBST
        testSearchHelper(fullBST, -5, expectedOutput);

        // lineBST
        testSearchHelper(lineBST, -5, expectedOutput);

        // rootBST
        testSearchHelper(rootBST, -5, expectedOutput);
    }

    // MARK: testNullSearch
    /**
     * Tests finding an element with a null key
     */
    @Test
    public void testNullSearch() {
        Integer nullVar = null;
        // emptyBST
        testSearchHelper(emptyBST, nullVar, nullVar);

        // fullBST
        testSearchHelper(fullBST, nullVar, nullVar);

        // lineBST
        testSearchHelper(lineBST, nullVar, nullVar);

        // rootBST
        testSearchHelper(rootBST, nullVar, nullVar);
    }

    // MARK: testRemoveHelper
    /**
     * convenience function for testing the remove function
     * 
     * @param BST             the BST to test on
     * @param element         the element to remove
     * @param expectedReturn  the expected return value of the function
     * @param expectedOutcome the expected form of the BST
     */
    private void testRemoveHelper(MyBST BST, Integer element,
            Integer expectedReturn, Integer[] expectedOutcome) {
        this.storeList(expectedOutcome);

        Integer output = (Integer) BST.remove(element);

        assertEquals("Checking Correct return", expectedReturn, output);
        this.checkBSTCorrectness(BST);
    }

    // MARK: testRemoveLeaf
    /**
     * Test remove a leaf
     */
    @Test
    public void testRemoveLead() {
        // emptyBST
        Integer[] expectedOutput1 = { null };
        this.testRemoveHelper(emptyBST, 10, null, expectedOutput1);

        // fullList
        Integer[] expectedOutput2 = { 8, 3, 10, 1, 6, null, 14, 4, 7 };
        testRemoveHelper(fullBST, 13, 13, expectedOutput2);

        // lineBST
        Integer[] expectedOutput3 = { 0, null, 1, null, 2, null, 3, null, 4,
                null, 5, null, 6, null, 7, null, 8 };
        testRemoveHelper(lineBST, 9, 9, expectedOutput3);

        // rootBST
        Integer[] expectedOutput4 = { null };
        testRemoveHelper(rootBST, 10, 10, expectedOutput4);
    }

    // MARK: testRemoveNodes
    /**
     * Tests removing an element with 1 or 2 nodes
     */
    @Test
    public void testRemoveNodes() {
        // fullList
        Integer[] expectedOutput1 = { 10, 3, 14, 1, 6, 13, null, 4, 7 };
        testRemoveHelper(fullBST, 8, 8, expectedOutput1);

        // lineBST
        Integer[] expectedOutput3 = { 0, null, 2, null, 3, null, 4, null, 5,
                null, 6, null, 7, null, 8, null, 9 };
        testRemoveHelper(lineBST, 1, 1, expectedOutput3);
    }

    // MARK: testRemoveNull
    /**
     * Tests removing an element with a null key
     */
    @Test
    public void testRemoveNull() {
        Integer[] expectedOutput = { 8, 3, 10, 1, 6, null, 14, 4, 7, 13, null };
        testRemoveHelper(fullBST, 100, null, expectedOutput);

        Integer[] expectedOutput2 = { 0, null, 1, null, 2, null, 3, null, 4,
                null, 5, null, 6, null, 7, null, 8, null, 9 };
        testRemoveHelper(fullBST, null, null, expectedOutput);
    }

    // MARK: testInOrderHelper
    /**
     * Convenience function for testing the inOrder function
     * 
     * @param BST            the BST to test on
     * @param expectedOutput the expectedOutput of the inOrder call
     */
    public void testInOrderHelper(MyBST BST, Integer[] expectedOutput) {
        this.storeBST(BST);

        ArrayList<MyBST.MyBSTNode> output = BST.inorder();

        int size = output.size();
        assertEquals("Checking output size", expectedOutput.length,
                output.size());
        for (int i = 0; i < size; i++) {
            Integer element = expectedOutput[i];
            assertEquals("Checking key correctness", element,
                    output.get(i).getKey());
            assertEquals("Checking key value", element,
                    output.get(i).getValue());
        }

        this.checkBSTCorrectness(BST);

    }

    // MARK: TestInOrder
    /**
     * Various test cases for the inOrder method
     */
    @Test
    public void testInOrder() {
        // empty test
        Integer[] expectedOutput = {};
        testInOrderHelper(emptyBST, expectedOutput);

        // fullList
        Integer[] expectedOutput2 = { 1, 3, 4, 6, 7, 8, 10, 13, 14 };
        testInOrderHelper(fullBST, expectedOutput2);

        // lineBST
        Integer[] expectedOutput3 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        testInOrderHelper(lineBST, expectedOutput3);

        // rootBST
        Integer[] expectedOutput4 = { 10 };
        testInOrderHelper(rootBST, expectedOutput4);
    }

    /**
     * Convenience function for testing the copy method
     * 
     * @param BST the BST to test on
     */
    private void testCopyHelper(MyBST BST) {
        this.storeBST(BST);

        MyBST BSTCopy = BST.copy();

        ArrayList<MyBST.MyBSTNode> list = convertBSTToArray(BSTCopy);
        ArrayList<MyBST.MyBSTNode> BSTList = convertBSTToArray(BST);

        int size = list.size();
        for (int i = 0; i < size; i++) {
            MyBST.MyBSTNode node = list.get(i);
            MyBST.MyBSTNode expectedNode = BSTList.get(i);

            if (node == null) {
                continue;
            }

            assertTrue("Checking references are different",
                    expectedNode != node);
            assertEquals("Checking data matches", expectedNode, node);
        }

        this.checkBSTCorrectness(BST);
    }

    // MARK: testCopy
    /**
     * Various test cases for the copy method
     */
    @Test
    public void testCopy() {
        testCopyHelper(emptyBST);
        testCopyHelper(fullBST);
        testCopyHelper(lineBST);
        testCopyHelper(rootBST);
    }

}
