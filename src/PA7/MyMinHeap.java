import java.util.ArrayList;
import java.util.Collection;

public class MyMinHeap<E extends Comparable<E>> implements MinHeapInterface<E> {

    protected ArrayList<E> data;

    // MARK: Initializers
    public MyMinHeap() {
        this.data = new ArrayList<>();
    }

    public MyMinHeap(Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }

        this.data = new ArrayList<E>(collection);
        int size = this.data.size();

        for (int i = 0; i < size; i++) {
            if (data.get(i) == null) {
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
        double result = index / 2;
        return (int) Math.floor(result);
    }

    // MARK: getLeftChildIdx
    protected static int getLeftChildIdx(int index) {
        return index * 2;
    }

    // MARK: getRightChildIdx
    protected static int getRightChildIdx(int index) {
        return index * 2 + 1;
    }

    // MARK: getMinChildIdx
    protected int getMinChildIdx(int index) {
        int leftIndex = getLeftChildIdx(index);
        int rightIndex = getLeftChildIdx(index);

        // TODO: this might be a bad bounds check
        // check if this is a leaf child
        if (rightIndex >= this.data.size()) {
            return -1;
        }

        E left = this.data.get(leftIndex);
        E right = this.data.get(rightIndex);

        // TODO: check comparing null and a valid entry
        // if the right child is null, avoid comparing and return the left node
        return right.compareTo(left) > 1 ? rightIndex : leftIndex;
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

        while (data.get(childIndex).compareTo(data.get(currentIndex)) < 0
                && childIndex < size) {

            this.swap(currentIndex, childIndex);
            currentIndex = childIndex;
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
        int lastIndex = this.data.size();

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