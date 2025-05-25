/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA7 Write-up
   
  This file is for CSE 12 PA7 in Spring 2025,
*/

import java.util.ArrayList;
import java.util.Collection;

// MARK: My MinHeap
/**
 * This class represents a generic MinHeap data type
 * 
 * It contains standard functions including insert, remove, and clear
 */
public class MyMinHeap<E extends Comparable<E>> implements MinHeapInterface<E> {

    protected static final int PARENT_CHILD_RATIO = 2;

    protected ArrayList<E> data;

    // MARK: Initializers
    /**
     * Initializes an emtpty heap
     */
    public MyMinHeap() {
        this.data = new ArrayList<>();
    }

    /**
     * Initializes a new heap wtih an initial collection
     * 
     * @param collection the initial collection to insert and heapify
     */
    public MyMinHeap(Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }

        this.data = new ArrayList<>(collection);
        int size = this.data.size();

        for (int i = size - 1; i >= 0; i--) {
            E element = this.data.get(i);

            if (element == null) {
                throw new NullPointerException();
            }

            this.percolateDown(i);
        }
    }

    // MARK: swap
    /**
     * Swaps to elements within a heap
     * 
     * @param from The first position to swap
     * @param to   The second position to swap
     */
    protected void swap(int from, int to) {
        E tempData = this.data.get(to);
        this.data.set(to, this.data.get(from));
        this.data.set(from, tempData);
    }

    // MARK: getParentIndex
    /**
     * Gets the index of a specified indexes parent. It does nto rely on the
     * underlying list,
     * 
     * @param index The index of the child
     * @return the index of the parent
     */
    protected static int getParentIdx(int index) {
        double result = (index - 1) / PARENT_CHILD_RATIO;
        return (int) result;
    }

    // MARK: getLeftChildIdx
    /**
     * Gets the index of a parent indexes left child. It does not rely on the
     * underlying list
     * 
     * @param index the index of the parent
     * @return the index of the child
     */
    protected static int getLeftChildIdx(int index) {
        return index * PARENT_CHILD_RATIO + 1;
    }

    // MARK: getRightChildIdx
    /**
     * Gets the index of a parent indexes right child. It does not rely on the
     * underlying list
     * 
     * @param index the index of the parent
     * @return the index of the child
     */
    protected static int getRightChildIdx(int index) {
        return index * PARENT_CHILD_RATIO + PARENT_CHILD_RATIO;
    }

    // MARK: getMinChildIdx
    /**
     * Gets the index of the a parent index's smaller child
     * 
     * @param index the index of the parent
     * @return the index of the the smaller child
     */
    protected int getMinChildIdx(int index) {
        int leftIndex = getLeftChildIdx(index);
        int rightIndex = getRightChildIdx(index);

        // first check if this is a leaf node (if it is return -1)
        // if it only has one child, then return the left node
        if (rightIndex >= this.data.size()) {
            if (leftIndex >= this.data.size()) {
                return -1;
            }
            return leftIndex;
        }

        E left = this.data.get(leftIndex);
        E right = this.data.get(rightIndex);

        return right.compareTo(left) < 0 ? rightIndex : leftIndex;
    }

    // MARK: percolateUp
    /**
     * Repeatedly moves a node up until there are no violations of the min heap
     * structure. If a child == parent, no swap occours
     * 
     * @param index the index of the node to move
     */
    protected void percolateUp(int index) {
        int parentIndex = getParentIdx(index);
        int currentIndex = index;

        while (data.get(parentIndex).compareTo(data.get(index)) > 0) {
            this.swap(parentIndex, currentIndex);
            currentIndex = parentIndex;
        }
    }

    // MARK: percolateDown
    /**
     * Repeatedly moves a node down until there are no violations of the min
     * heap structure. If a child == parent, no swap occours
     * 
     * @param index the index of the node to move
     */
    protected void percolateDown(int index) {
        int childIndex = getMinChildIdx(index);
        int currentIndex = index;
        int size = this.data.size();

        while (childIndex != -1
                && data.get(childIndex).compareTo(data.get(currentIndex)) < 0
                && childIndex < size) {

            this.swap(currentIndex, childIndex);
            currentIndex = childIndex;
            childIndex = getMinChildIdx(currentIndex);
        }
    }

    // MARK: deleteIndex
    /**
     * Delete the node at the specified index. Moves the right most child node
     * into its place and then resolves structure violations
     * 
     * @param index the index of the node to delete
     * @return the element that was removed
     */
    protected E deleteIndex(int index) {
        E element = this.data.get(index);

        // swap the deleted element with the last element
        int size = this.data.size() - 1;
        this.swap(index, size);
        this.data.remove(size);

        // percolate up / down
        int parentIndex = getParentIdx(index);
        if (data.get(parentIndex).compareTo(data.get(index)) > 0) {
            this.percolateUp(index);
        }

        int minChildIndex = getMinChildIdx(index);
        if (data.get(minChildIndex).compareTo(data.get(index)) < 0) {
            this.percolateDown(index);
        }

        return element;
    }

    // MARK: insert
    /**
     * Inserts a new element into the tree. Adds it as the right most leaf node,
     * then percolates up
     * 
     * @param element the element to insert
     */
    public void insert(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        // add the element
        this.data.add(element);
        int lastIndex = this.data.size() - 1;

        // ensure its placed correctly
        this.percolateUp(lastIndex);
    }

    // MARK: getMin
    /**
     * Gets the smallest element stored in the heap
     * 
     * @return the minimum element in the array
     */
    public E getMin() {
        if (this.data.isEmpty()) {
            return null;
        }

        return this.data.get(0);
    }

    // MARK: E remove
    /**
     * Removes the root node
     * 
     * @return the element in the root
     */
    public E remove() {
        if (this.data.isEmpty()) {
            return null;
        }

        return this.deleteIndex(0);
    }

    // MARK: size
    /**
     * Gets the size of the heap
     * 
     * @return the size of the heap
     */
    public int size() {
        return this.data.size();
    }

    // MARK: clear
    /**
     * Empties out the heap
     */
    public void clear() {
        this.data.clear();
    }
}