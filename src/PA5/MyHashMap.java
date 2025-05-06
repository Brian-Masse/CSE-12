// MARK: MyHashMap
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 5;
    private static final double LOAD_FACTOR = 0.8;
    private static final int EXPAND_CAPACITY_RATIO = 2;
    private static final int HASH_CODE_MASK = 0x7fffffff;

    Node[] hashTable;
    int size;

    // MARK: Initializers
    public MyHashMap() {
        this.size = 0;
        this.hashTable = new Node[DEFAULT_CAPACITY];
    }

    public MyHashMap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException();
        }

        this.size = initialCapacity;
        this.hashTable = new Node[initialCapacity];
    }

    // MARK: Get
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException();
        }

        // retrieve the data stored at the key
        int hash = getHash(key, this.hashTable.length);
        Node currentNode = this.hashTable[hash];
        if (currentNode == null) {
            return null;
        }

        // if there is a linked list, traverse it to the end
        while (currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
        }

        return (V) currentNode.getValue();
    }

    // MARK: Put
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }

        // check that the hashmap is not at capacity
        if (this.getCapacity() * LOAD_FACTOR >= this.size) {
            this.expandCapacity();
        }

        // get current data
        Node<K, V> newNode = new Node<K, V>(key, value);
        int hashCode = getHash(key, this.getCapacity());
        Node<K, V> currentNode = this.hashTable[hashCode];

        // if the value at key is empty, store the new node
        if (currentNode == null) {
            this.hashTable[hashCode] = newNode;
            return null;
        }

        // if there is a collision, add the new node to the end of the list
        while (currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
        }

        currentNode.setNext(newNode);
        return currentNode.getValue();
    }

    // MARK: remove
    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException();
        }

        int hashCode = getHash(key, this.getCapacity());
        Node currentNode = this.hashTable[hashCode];

        // if the value at the key is empty
        if (currentNode == null) {
            return null;
        }

        // if there is only one value, remove it, and return its value
        if (currentNode.getNext() == null) {
            this.hashTable[hashCode] = null;
            return (V) currentNode.getValue();
        }

        // if there are multiple values, traverse to the end of the list, and
        // remove it
        while (currentNode.getNext().getNext() != null) {
            currentNode = currentNode.getNext();
        }

        V storedValue = (V) currentNode.getNext().getValue();
        currentNode.setNext(null);
        return storedValue;

    }

    // MARK: size
    public int size() {
        return this.size;
    }

    // MARK: getCapacity
    public int getCapacity() {
        return this.hashTable.length;
    }

    // MARK: clear
    public void clear() {
        for (int i = 0; i < this.getCapacity(); i++) {
            this.hashTable[i] = null;
        }
    }

    // MARK: isEmpty
    public boolean isEmpty() {
        return (this.size == 0);
    }

    // MARK: expandCapacity
    public void expandCapacity() {
        int oldCapacity = this.getCapacity();
        int newCapacity = oldCapacity * EXPAND_CAPACITY_RATIO;
        Node[] newHashTable = new Node[newCapacity];

        for (int i = 0; i < oldCapacity; i++) {
            Node currentNode = this.hashTable[i];

            // if the value at the table is null, keep going
            if (currentNode == null) {
                continue;
            }

            // if the value at the table is full, go through the list and
            // re-hash
            while (currentNode.getValue() != null) {
                K key = (K) currentNode.getKey();
                int newHashCode = this.getHash(key, newCapacity);

                newHashTable[newHashCode] = currentNode;
            }
        }
    }

    // MARK: getHash
    public int getHash(K key, int capacity) {
        if (key == null) {
            throw new NullPointerException();
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }

        // Hashing function is given here. DO NOT MODIFY THIS
        return (key.hashCode() & HASH_CODE_MASK) % capacity;
    }

    // MARK: Node
    /**
     * A Node class that holds (key, value) pairs and references to the next
     * node in the linked list
     */
    protected class Node<K, V> {
        K key;
        V value;
        Node next;

        /**
         * Constructor to create a single node
         * 
         * @param key   key to store in this node
         * @param value value to store in this node
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        /**
         * Accessor to get the next node in the list
         * 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * Set the next node in the list
         * 
         * @param n the new next node
         */
        public void setNext(Node n) {
            next = n;
        }

        /**
         * Accessor to get the node's key
         * 
         * @return this node's key
         */
        public K getKey() {
            return this.key;
        }

        /**
         * Set the node's key
         * 
         * @param key the new key
         */
        public void setKey(K key) {
            this.key = key;
        }

        /**
         * Accessor to get the node's value
         * 
         * @return this node's value
         */
        public V getValue() {
            return this.value;
        }

        /**
         * Set the node's value
         * 
         * @param value the new value
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Check if this node is equal to another node
         * 
         * @param other the other node to check equality with
         * @return whether or not this node is equal to the other node
         */
        public boolean equals(Node<K, V> other) {
            return this.key.equals(other.key) && this.value.equals(other.value);
        }
    }
}
