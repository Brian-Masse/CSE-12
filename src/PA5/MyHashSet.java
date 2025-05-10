/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA5 Write-up
   
  This file is for CSE 12 PA5 in Spring 2025,
*/

// MARK: MyHashSet
/**
 * This class represents a generic HashSeT data type similar to the one found in
 * Java
 * 
 * It acts as a basic wrapper for the HashMap implemented elsewhere in this PA
 */
public class MyHashSet<E> {
    public static final Object DEFAULT_OBJECT = new Object();

    MyHashMap<E, Object> hashMap;

    // MARK: Initializers
    /**
     * Initializes an empty HashSet with defaultCapacity
     */
    public MyHashSet() {
        this.hashMap = new MyHashMap<>();
    }

    /**
     * Initializes an empty HashSet with a specified initalCapacity
     * 
     * @param initialCapacity the initialCapacity of the HashSet
     */
    public MyHashSet(int initialCapacity) {
        this.hashMap = new MyHashMap<>(initialCapacity);
    }

    // MARK: Add
    /**
     * Adds the default objects with the specified key to the HashMap
     * 
     * @param element the key associated with the new object
     * @return a flag indiciating if the key was not already present in the map
     */
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        boolean containedElement = this.hashMap.put(element,
                DEFAULT_OBJECT) == null;

        return containedElement;
    }

    // MARK: remove
    /**
     * Removes an element with the specified key from the HashMap
     * 
     * @param element the key associated with the object to remove
     * @return a flag indicating whether the key was present in the map
     */
    public boolean remove(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        boolean containedElement = this.hashMap.remove(element) != null;

        return containedElement;
    }

    // MARK: size
    /**
     * Returns the size of the underlying hashMap
     * 
     * @return the size of the underlying HashMap
     */
    public int size() {
        return this.hashMap.size();
    }

    // MARK: clear
    /**
     * Clears all the key value pairs in the HashMap
     */
    public void clear() {
        this.hashMap.clear();
    }

    // MARK: isEmpty
    /**
     * Determines whether there are any key value pairs in the HashMap
     * 
     * @return whether the HashMap is empty
     */
    public boolean isEmpty() {
        return this.hashMap.isEmpty();
    }
}