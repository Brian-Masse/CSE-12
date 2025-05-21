import java.util.ArrayList;

public class MyAlgorithm2 {

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