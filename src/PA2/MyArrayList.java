public class MyArrayList<E> implements MyList<E> {

    // MARK: Constants
    protected static final int DEFAULT_CAPACITY = 5;
    protected static final int INDEX_NOT_FOUND = -1;

    // MARK: Vars
    Object[] data;
    int size;

    // MARK: Initializers:
    public MyArrayList() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }

        this.data = new Object[initialCapacity];
        this.size = 0;
    }

    public MyArrayList(E[] arr) {
        if (arr != null) {
            // TODO: possibly make this a deep copy?
            this.data = arr;
            this.size = arr.length;
        } else {
            this.data = new Object[DEFAULT_CAPACITY];
            this.size = 0;
        }
    }

    // MARK: extendCapacity
    @Override
    public void expandCapacity(int requiredCapacity) {
        if (requiredCapacity < this.size) {
            throw new IllegalArgumentException();
        }

        // determine the newCapacity
        int currentCapacity = this.getCapacity();

        int newCapacity = (currentCapacity == 0) ? DEFAULT_CAPACITY : currentCapacity * 2;
        newCapacity = (newCapacity < requiredCapacity) ? requiredCapacity : newCapacity;

        // copy the objects into the resized array
        Object[] resizedData = new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            resizedData[i] = this.data[i];
        }

        // update instance variables
        this.data = resizedData;
    }

    // MARK: getCapacity
    @Override
    public int getCapacity() {
        return this.data.length;
    }

    // MARK: checkCapacity
    private void checkCapacity(int newRequiredCapacity) {
        if (this.size == this.getCapacity()) {
            this.expandCapacity(newRequiredCapacity);
        }
    }

    // MARK: insert
    @Override
    public void insert(int index, E element) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        checkCapacity(this.size + 1);

        // move elements past index to the right to make space
        for (int i = this.size - 1; i >= index; i--) {
            this.data[i + 1] = this.data[i];
        }

        // insert new element
        this.data[index] = element;
        this.size += 1;
    }

    // MARK: Append
    @Override
    public void append(E element) {
        this.insert(this.size, element);
    }

    // MARK: prepend
    @Override
    public void prepend(E element) {
        this.insert(0, element);
    }

    // MARK: get
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        // TODO: Maybe functionalize checking indexOutOfBounds
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        return (E) this.data[index];
    }

    // MARK: set
    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        E previousValue = (E) this.data[index];
        this.data[index] = element;

        return previousValue;
    }

    // MARK: remove
    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        // capture the old value, and shift other values
        E removedValue = (E) this.data[index];
        for (int i = index + 1; i < this.size; i++) {
            this.data[i - 1] = this.data[i];
        }

        this.data[size - 1] = null;
        this.size -= 1;
        return removedValue;
    }

    // MARK: size
    @Override
    public int size() {
        return this.size;
    }

    // MARK: Rotate
    @Override
    public void rotate(int numPositions) {
        if (numPositions < 0 || numPositions > this.size) {
            throw new IndexOutOfBoundsException();
        }

        Object[] dataCopy = new Object[this.getCapacity()];

        // rotate each element into the new array
        for (int i = 0; i < this.size; i++) {
            int newIndex = (i + numPositions) % this.size;
            dataCopy[newIndex] = this.data[i];
        }

        this.data = dataCopy;
    }

    // MARK: Find
    @Override
    public int find(E element) {

        for (int i = this.size - 1; i >= 0; i--) {
            if (this.data[i].equals(element)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
}
