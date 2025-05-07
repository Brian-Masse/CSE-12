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

        boolean containedElement = this.hashMap.get(element) == null;

        this.hashMap.put(element, DEFAULT_OBJECT);

        return containedElement;
    }

    // MARK: remove
    public boolean remove(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        boolean containedElement = this.hashMap.get(element) == null;

        this.hashMap.remove(element);

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