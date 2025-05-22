/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA7 Write-up
   
  This file is for CSE 12 PA7 in Spring 2025,
*/

import java.util.Collection;

// MARK: MyPriorityQueue
/**
 * This class represents a generic priority quueue data type.
 * 
 * It uses a min heap to implement the data strucutre, meaning lower numbers are
 * the highest priority
 */
public class MyPriorityQueue<E extends Comparable<E>> {
    protected MyMinHeap<E> heap;

    // MARK: Initializers
    /**
     * Initializes a new empty priorityQueue
     */
    public MyPriorityQueue() {
        this.heap = new MyMinHeap<>();
    }

    /**
     * Initializes a new priorityQueue wtih an initial collection
     * 
     * @param collection the initial collection to insert and heapify
     */
    public MyPriorityQueue(Collection<? extends E> collection) {
        this.heap = new MyMinHeap<>(collection);
    }

    // MARK: push
    /**
     * Adds a new element into the queue
     * 
     * @param element the element to add to the queue
     */
    public void push(E element) {
        this.heap.insert(element);
    }

    // MARK: Peek
    /**
     * Looks at the element with the highest priority in the queue
     * 
     * @return the element with the highest priority
     */
    public E peek() {
        return this.heap.getMin();
    }

    // MARK: pop
    /**
     * Removes the element with the highest priority in th queue
     * 
     * @return the element with the highest priority
     */
    public E pop() {
        return this.heap.remove();
    }

    // MARK: getLength
    /**
     * Gets the length of the current queue
     * 
     * @return the lenght of the queue
     */
    public int getLength() {
        return this.heap.size();
    }

    // MARK: clear
    /**
     * Removes all elements from the queueu
     */
    public void clear() {
        this.heap.clear();
    }

}