/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA5 Write-up
   
  This file is for CSE 12 PA5 in Spring 2025,
*/

// MARK: MyHashMap
/**
 * This class represents a generic HashMap data type similar to the one found in
 * Java
 * 
 * It contains standard functions including get, put, remove, and
 * expandCapacity.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 5;
    private static final double LOAD_FACTOR = 0.8;
    private static final int EXPAND_CAPACITY_RATIO = 2;
    private static final int HASH_CODE_MASK = 0x7fffffff;

    private static final int EMPTY_MAP_SIZE = 0;

    Node[] hashTable;
    int size;

    // MARK: Initializers
    /**
     * Initializes an empty Hashmap with defauly capacity
     */
    public MyHashMap() {
        this.size = EMPTY_MAP_SIZE;
        this.hashTable = new Node[DEFAULT_CAPACITY];
    }

    /**
     * Initializes an empty Hashmap with a specified capacity Should be greater
     * than 0
     * 
     * @param initialCapacity the initialCapacity of the hashMap
     */
    public MyHashMap(int initialCapacity) {
        if (initialCapacity <= EMPTY_MAP_SIZE) {
            throw new IllegalArgumentException();
        }

        this.size = EMPTY_MAP_SIZE;
        this.hashTable = new Node[initialCapacity];
    }

    // MARK: Get
    /**
     * Gets the value stored at a given key. Returns null if no key exists in
     * the table
     * 
     * @param key the key of the value to get
     * @return the value at the given key
     */
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
        while (currentNode != null) {
            if (currentNode.getKey().equals(key)) {
                return (V) currentNode.getValue();
            }

            currentNode = currentNode.getNext();
        }

        return null;
    }

    // MARK: Put
    /**
     * Puts a specified value at a specified key. Expands the capacity by a set
     * ratio if the table is approaching capacity. If the key already exists in
     * the table, it updates its value. If there is a collision, the new value
     * is added to the chain of values
     * 
     * @param key   the key to put at
     * @param value the new value to put
     * @return the old value at the specified key
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }

        boolean containsKey = this.get(key) != null;

        // check that the hashmap is not at capacity
        if (this.getCapacity() * LOAD_FACTOR <= this.size) {
            this.expandCapacity();
        }

        if (!containsKey) {
            this.size++;
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
        // if the key already exists in the list, replace its value
        while (currentNode != null) {
            if (currentNode.getKey().equals(key)) {
                V previousValue = (V) currentNode.getValue();
                currentNode.setValue(value);
                return previousValue;
            }

            if (currentNode.getNext() == null) {
                currentNode.setNext(newNode);
                break;
            }

            currentNode = currentNode.getNext();
        }

        return null;
    }

    // MARK: remove
    /**
     * Removes a key value pair.
     * 
     * @param key the key to remove at
     * @return the value stored at the key
     */
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
        if (currentNode.getKey().equals(key)) {
            this.hashTable[hashCode] = currentNode.getNext();
            this.size--;
            return (V) currentNode.getValue();
        }

        // if there are multiple values, find the key and remove it
        while (currentNode.getNext() != null) {
            if (currentNode.getNext().getKey().equals(key)) {

                V storedValue = (V) currentNode.getNext().getValue();
                Node nextValue = currentNode.getNext().getNext();
                currentNode.setNext(nextValue);

                this.size--;

                return storedValue;
            }
            currentNode = currentNode.getNext();
        }

        return null;
    }

    // MARK: size
    /**
     * Gets the number of elements stored in this HashMap
     * 
     * @return the size of the HashMap
     */
    public int size() {
        return this.size;
    }

    // MARK: getCapacity
    /**
     * Gets the capacity of the HashMap
     * 
     * @return the capacity of the HashMap
     */
    public int getCapacity() {
        return this.hashTable.length;
    }

    // MARK: clear
    /**
     * Removes all elements from the HashMap
     */
    public void clear() {
        for (int i = 0; i < this.getCapacity(); i++) {
            this.hashTable[i] = null;
        }
        this.size = EMPTY_MAP_SIZE;
    }

    // MARK: isEmpty
    /**
     * Checks whether the HashMap has an key value pairs
     * 
     * @return Whether the HashMap is empty
     */
    public boolean isEmpty() {
        return (this.size == EMPTY_MAP_SIZE);
    }

    // MARK: expandCapacity
    /**
     * Expands the capacity of the HashMap, then rehashes all current elements
     */
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
            while (currentNode != null) {
                K key = (K) currentNode.getKey();
                int newHashCode = this.getHash(key, newCapacity);

                Node nodeCopy = new Node(currentNode.getKey(),
                        currentNode.getValue());
                newHashTable[newHashCode] = nodeCopy;

                currentNode = currentNode.getNext();
            }
        }

        this.hashTable = newHashTable;
    }

    // MARK: getHash
    /**
     * gets the hash value associated with a given key
     * 
     * @param key      the key to hash
     * @param capacity the currentCapacity of the HashMap
     * @return the hashValue of the specified key
     */
    public int getHash(K key, int capacity) {
        if (key == null) {
            throw new NullPointerException();
        }

        if (capacity <= EMPTY_MAP_SIZE) {
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
    protected class Node<Key, Value> {
        Key key;
        Value value;
        Node next;

        /**
         * Constructor to create a single node
         * 
         * @param key   key to store in this node
         * @param value value to store in this node
         */
        public Node(Key key, Value value) {
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
        public Key getKey() {
            return this.key;
        }

        /**
         * Set the node's key
         * 
         * @param key the new key
         */
        public void setKey(Key key) {
            this.key = key;
        }

        /**
         * Accessor to get the node's value
         * 
         * @return this node's value
         */
        public Value getValue() {
            return this.value;
        }

        /**
         * Set the node's value
         * 
         * @param value the new value
         */
        public void setValue(Value value) {
            this.value = value;
        }

        /**
         * Check if this node is equal to another node
         * 
         * @param other the other node to check equality with
         * @return whether or not this node is equal to the other node
         */
        public boolean equals(Node<Key, Value> other) {
            return this.key.equals(other.key) && this.value.equals(other.value);
        }
    }
}
