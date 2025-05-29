/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA7 Write-up
   
  This file is for CSE 12 PA7 in Spring 2025,
*/

import java.util.ArrayList;

// MARK: MyAlgorithm
/**
 * This class houses an algorithm to find the kth largest value in an unsorted
 * list of numbers uisng a priority queue and minheap data structure
 * 
 */
public class MyPriorityQueueAlgorithm {

    /**
     * Finds the kth largest value in an unsorted list of numbers
     *
     * @param list the list of unsorted integeres to search
     * @param k    the kth largest number to find
     * @return the kth largest number
     */
    public static Integer getKthLargest(ArrayList<Integer> list, int k) {

        MyPriorityQueue<Integer> queue = new MyPriorityQueue<>(list);

        // remove n - k elements
        int size = queue.getLength();
        for (int i = 0; i < size - k; i++) {
            queue.pop();
        }

        return queue.peek();
    }
}