import java.util.ArrayList;
import java.util.Collection;

public class MyMinHeap<E extends Comparable<E>> implements MinHeapInterface<E> {

    protected ArrayList<E> data;

    public static void printList(ArrayList list) {
        int size = list.size();

        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print("" + list.get(i).toString() + ", ");
        }
        System.out.println("]");
    }

    // MARK: Initializers
    public MyMinHeap() {
        this.data = new ArrayList<>();
    }

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
    protected void swap(int from, int to) {
        E tempData = this.data.get(to);
        this.data.set(to, this.data.get(from));
        this.data.set(from, tempData);
    }

    // MARK: getParentIndex
    protected static int getParentIdx(int index) {
        double result = (index - 1) / 2;
        return (int) result;
    }

    // MARK: getLeftChildIdx
    protected static int getLeftChildIdx(int index) {
        return index * 2 + 1;
    }

    // MARK: getRightChildIdx
    protected static int getRightChildIdx(int index) {
        return index * 2 + 2;
    }

    // MARK: getMinChildIdx
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
    protected void percolateUp(int index) {
        int parentIndex = getParentIdx(index);
        int currentIndex = index;

        while (data.get(parentIndex).compareTo(data.get(index)) > 0) {
            this.swap(parentIndex, currentIndex);
            currentIndex = parentIndex;
        }
    }

    // MARK: percolateDown
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
    protected E deleteIndex(int index) {
        E element = this.data.get(index);

        // swap the deleted element with the last element
        int size = this.data.size() - 1;
        this.swap(index, size);
        this.data.removeLast();

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
    public E getMin() {
        if (this.data.isEmpty()) {
            return null;
        }

        return this.data.getFirst();
    }

    // MARK: E remove
    public E remove() {
        if (this.data.isEmpty()) {
            return null;
        }

        return this.deleteIndex(0);
    }

    // MARK: size
    public int size() {
        return this.data.size();
    }

    // MARK: clear
    public void clear() {
        this.data.clear();
    }
}