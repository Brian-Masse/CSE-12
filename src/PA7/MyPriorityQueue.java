import java.util.Collection;

public class MyPriorityQueue<E extends Comparable<E>> {
    protected MyMinHeap<E> heap;

    // MARK: Initializers
    public MyPriorityQueue() {
        this.heap = new MyMinHeap<>();
    }

    public MyPriorityQueue(Collection<? extends E> collection) {
        this.heap = new MyMinHeap<>(collection);
    }

    // MARK: push
    public void push(E element) {
        this.heap.insert(element);
    }

    // MARK: Peek
    public E peek() {
        return this.heap.getMin();
    }

    // MARK: pop
    public E pop() {
        return this.heap.remove();
    }

    // MARK: getLength
    public int getLength() {
        return this.heap.size();
    }

    // MARK: clear
    public void clear() {
        this.heap.clear();
    }

}