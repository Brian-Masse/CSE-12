public class MyDeque<E> implements DequeInterface<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int EXPAND_CAPACITY_RATIO = 2;

    Object[] data;
    int size;
    int front;
    int rear;

    // MARK: MyDequeue
    public MyDeque(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }

        // TODO: This might not be the correct implementation
        int capacity = initialCapacity == 0 ? DEFAULT_CAPACITY
                : initialCapacity;
        this.data = new Object[capacity];

        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    // MARK: size
    public int size() {
        return this.size;
    }

    // MARK: ExpandCapacity
    public void expandCapacity() {
        // calaculate the new capacity
        int currentCapacity = this.data.length;
        int proposedCapacity = currentCapacity * EXPAND_CAPACITY_RATIO;
        int newCapacity = this.data.length == 0 ? DEFAULT_CAPACITY
                : proposedCapacity;

        // copy elements into new array
        Object[] newArray = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            int index = (i + front) % currentCapacity;
            newArray[i] = this.data[index];
        }

        // update instance variables
        this.front = 0;
        this.rear = size - 1;
        this.data = newArray;
    }

    // MARK: addFirst
    public void addFirst(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        // expand capacity if neccessary
        if (this.size >= this.data.length) {
            this.expandCapacity();
        }

        // add the element
        int capacity = this.data.length;
        int index = (this.front - 1) % capacity;
        this.data[index] = element;

        // update instance vars
        this.size += 1;
        this.front = index;
    }

    // MARK: addLast
    public void addLast(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        // expandCapacity if neccessary
        if (this.size >= this.data.length) {
            this.expandCapacity();
        }

        // add the element
        int capacity = this.data.length;
        int index = (this.rear + 1) % capacity;
        this.data[index] = element;

        // update instance vars
        this.size += 1;
        this.rear = index;
    }

    // MARK: removeFirst
    public E removeFirst() {
        Object data = this.data[this.front];

        // if there is no data at the front, do nothing
        if (data == null) {
            return null;
        }

        // update instance vars
        int capacity = this.data.length;
        int nextFrontIndex = (this.front + 1) % capacity;
        this.size--;
        this.front = nextFrontIndex;

        return (E) data;
    }

    // MARK: removeLast
    public E removeLast() {
        Object data = this.data[this.rear];

        // if there is no data at rear, do nothing
        if (data == null) {
            return null;
        }

        // update instance vars
        int capacity = this.data.length;
        int nextRearIndex = (this.rear - 1) % capacity;
        this.size--;
        this.rear = nextRearIndex;

        return (E) data;
    }

    // MARK: peekFirst
    public E peekFirst() {
        return (E) this.data[this.front];
    }

    // MARK: peekLast
    public E peekLast() {
        return (E) this.data[this.rear];
    }

}