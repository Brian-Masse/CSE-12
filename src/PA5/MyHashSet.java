// MARK: MyHashSet
public class MyHashSet<E> {
    public static final Object DEFAULT_OBJECT = new Object();

    MyHashMap<E, Object> hashMap;

    // MARK: Initializers
    public MyHashSet() {
        this.hashMap = new MyHashMap<>();
    }

    public MyHashSet(int initialCapacity) {
        this.hashMap = new MyHashMap<>(initialCapacity);
    }

    // MARK: Add
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        boolean containedElement = this.hashMap.put(element,
                DEFAULT_OBJECT) == null;

        return containedElement;
    }

    // MARK: remove
    public boolean remove(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        boolean containedElement = this.hashMap.remove(element) != null;

        return containedElement;
    }

    // MARK: size
    public int size() {
        return this.hashMap.size();
    }

    // MARK: clear
    public void clear() {
        this.hashMap.clear();
    }

    // MARK: isEmpty
    public boolean isEmpty() {
        return this.hashMap.isEmpty();
    }
}