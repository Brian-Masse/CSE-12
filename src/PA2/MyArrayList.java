public class MyArrayList<E> implements MyList {

    // MARK: Constants
    protected static final int DEFAULT_CAPACITY = 5;

    // MARK: Vars
    Object[] data;
    int size;

    // MARK: Initializers:
    public MyArrayList() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public MyArrayList(int initialCapacity) {
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
    public void expandCapacity(int requiredCapacity) throws IllegalArgumentException {
        if (requiredCapacity < this.size) {
            throw new IllegalArgumentException();
        }

        // determine the newCapacity
        int newCapacity = (this.size == 0) ? DEFAULT_CAPACITY : this.size * 2;
        newCapacity = (newCapacity < requiredCapacity) ? requiredCapacity : newCapacity;

        // copy the objects into the resized array
        Object[] resizedData = new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            resizedData[i] = this.data[i];
        }

        // update instance variables
        this.data = resizedData;
    }

}
