import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

// MARK: CustomTester
public class BSTCustomTester {

    // MARK: PrintList
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

    private ArrayList<Integer> makeList(Integer[] list) {
        return new ArrayList<>(Arrays.asList(list));
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

    ArrayList<MyBST.MyBSTNode> storedList;
    int storedSize;

    // MARK: Check BST correctness
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

            assertEquals("Checking Object Correctness", convertedObject,
                    expectedObject);
        }
    }

    // MARK: storeBST
    private void storeBST(MyBST BST) {
        this.storedList = convertBSTToArray(BST);
        this.storedSize = BST.size;
    }

    // MARK: Setup
    @Before
    public void setup() {
        // setupEmptyBST
        this.emptyBSTList = new ArrayList<>();
        this.emptyBST = new MyBST<>();

        // setup fullBST
        Integer[] fullBSTList = { 8, 3, 10, 1, 6, 14, 4, 7, 13 };
        this.fullBST = convertArrayToBST(makeList(fullBSTList));
        this.fullBSTList = convertBSTToArray(fullBST);

        // setupLineBST
        Integer[] lineBSTList = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        this.lineBST = convertArrayToBST(makeList(lineBSTList));
        this.lineBSTList = convertBSTToArray(fullBST);

        // setup rootBST
        Integer[] rootBSTList = { 10 };
        this.rootBST = convertArrayToBST(makeList(rootBSTList));
        this.rootBSTList = convertBSTToArray(fullBST);
    }

    // tests finding the next value from the final node in the BST
    @Test
    public void testSuccessorFromBottom() {
        // fullBST
        storeBST(fullBST);

    }

}
