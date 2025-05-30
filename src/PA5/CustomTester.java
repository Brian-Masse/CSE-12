
/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA4 Write-up
   
  This file is for CSE 12 PA4 in Spring 2025,
*/
import static org.junit.Assert.*;
import org.junit.*;

/**
 * The custom tester for PA5, which covers some basic test cases.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomTester {

    // Defines a generic operation on a HashMap to make function calls easier
    @FunctionalInterface
    interface HashMapOperation {
        void execute(MyHashMap map);
    }

    // stored vaules
    private MyHashMap.Node[][] storedHashMap;
    private int storedHashCapacity;
    private int storedHashSize;

    // sample HashMaps / sets
    private MyHashMap<String, Integer> fullMap;
    private MyHashMap<String, Integer> emptyMap;
    private MyHashMap<String, Integer> fullResizedMap;

    private MyHashSet<String> fullSet;
    private MyHashSet<String> emptySet;

    // MARK: static vars
    private static final int fullResizedSize = 10;
    private static final int fullMapSize = 4;
    private static final int fullSetSize = 3;

    // MARK: printTranslatedHashMap
    /**
     * Takes a 2D representation of a HashMap and prints it in a human readable
     * format
     * 
     * @param map the map to print
     */
    private void printTranslatedHashMap(MyHashMap.Node[][] map) {
        int length = map.length;
        System.out.println("----");

        for (int i = 0; i < length; i++) {
            int j = 0;
            String str = "[" + i + "]";

            if (map[i] == null) {
                System.out.println("[" + i + "] null");
                continue;
            }

            while (map[i][j] != null) {
                MyHashMap.Node node = map[i][j];
                str += "[" + j + "] (Key: " + node.getKey() + ", Value: "
                        + node.getValue() + "), ";

                j++;

            }
            System.out.println(str);
        }
        System.out.println("----");

    }

    // MARK: translateHashMap
    /**
     * Translates the HashMap datastructure into a 2D array
     * 
     * @param hashMap the map to translate
     * @return the translated map
     */
    private MyHashMap.Node[][] translateHashMap(MyHashMap hashMap) {
        int size = hashMap.size;
        int capacity = hashMap.hashTable.length;
        MyHashMap.Node[][] translatedMap = new MyHashMap.Node[capacity][size];

        for (int i = 0; i < hashMap.hashTable.length; i++) {
            MyHashMap.Node node = hashMap.hashTable[i];

            if (node == null) {
                translatedMap[i] = null;
            } else {
                MyHashMap.Node[] linkedList = new MyHashMap.Node[size];
                int currentIndex = 0;

                while (node != null) {

                    MyHashMap.Node newNode = hashMap.new Node(node.getKey(),
                            node.getValue());
                    linkedList[currentIndex] = newNode;

                    node = node.getNext();
                    currentIndex += 1;
                }

                translatedMap[i] = linkedList;
            }
        }

        return translatedMap;
    }

    // MARK: storeHashMap
    /**
     * Stores the values associated with a HashMap data type to be used when
     * checking the correctness of a map later
     * 
     * @param map the map to store
     */
    private void storeHashMap(MyHashMap map) {
        this.storedHashMap = translateHashMap(map);
        this.storedHashSize = map.size;
        this.storedHashCapacity = map.hashTable.length;
    }

    // MARK: checkHashMap
    /**
     * Checks whether a given HashMap matches the values stored in this class.
     * Specifically checks size, capacity, and the values of the map are all
     * correct
     * 
     * @param map the map to check
     */
    private void checkHashMap(MyHashMap map) {
        MyHashMap.Node[][] translatedMap = this.translateHashMap(map);

        assertEquals("Checking size", storedHashSize, map.size);
        assertEquals("Checking Capacity", storedHashCapacity,
                map.hashTable.length);

        // recursivley check every element in the stored map is correct
        int length = this.storedHashMap.length;
        for (int i = 0; i < length; i++) {
            if (this.storedHashMap[i] == null) {
                assertArrayEquals("Checking Null Element in Table", null,
                        translatedMap[i]);
                continue;
            }

            String msg = "Checking LinkedList Correctness (" + i + ")";

            int j = 0;
            while (this.storedHashMap[i][j] != null) {
                boolean nodesEqual = storedHashMap[i][j]
                        .equals(translatedMap[i][j]);
                assertTrue(msg, nodesEqual);

                j++;
            }
        }
    }

    // MARK: testExceptionRaised
    /**
     * Tests that a generic function that should raise an exception, does raise
     * the correct type of exception
     * 
     * @param map           the map to perform the function on
     * @param exceptionType the expected type of exception the function raises
     * @param operation     the function to perform on the map
     */
    private void testExceptionRaised(MyHashMap map, Class<?> exceptionType,
            HashMapOperation operation) {
        boolean caughtException = false;
        this.storeHashMap(map);

        // check passing in an invalid param
        try {
            operation.execute(map);
        } catch (Exception exception) {
            if (exception.getClass() == exceptionType) {
                caughtException = true;
            }
        }

        assertTrue("Checking exception was Caught", caughtException);
        this.checkHashMap(map);
    }

    // MARK: Before
    /**
     * This sets up the test fixture. JUnit invokes this method before every
     * testXXX method. The @Before tag tells JUnit to run this method before
     * each test.
     */
    @Before
    public void setup() {
        // setup full map
        fullMap = new MyHashMap<>();
        MyHashMap<String, Integer>.Node<String, Integer> element1 = fullMap.new Node<String, Integer>(
                "A", 1);
        MyHashMap<String, Integer>.Node<String, Integer> element2 = fullMap.new Node<String, Integer>(
                "B", 2);
        MyHashMap<String, Integer>.Node<String, Integer> element3 = fullMap.new Node<String, Integer>(
                "C", 3);
        MyHashMap<String, Integer>.Node<String, Integer> element4 = fullMap.new Node<String, Integer>(
                "F", 6);

        fullMap.hashTable[0] = element1;
        fullMap.hashTable[1] = element2;
        fullMap.hashTable[2] = element3;
        element1.setNext(element4);

        fullMap.size = fullMapSize;

        // setup fullResizedMap
        fullResizedMap = new MyHashMap<>(10);
        fullResizedMap.hashTable[0] = element4;
        fullResizedMap.hashTable[5] = fullMap.new Node<String, Integer>("A", 1);
        fullResizedMap.hashTable[6] = element2;
        fullResizedMap.hashTable[7] = element3;

        fullResizedMap.size = fullMapSize;

        // setup emptyMap
        emptyMap = new MyHashMap<>();

        // setup full set
        fullSet = new MyHashSet<>();

        MyHashMap<String, Object>.Node<String, Object> setElement1 = fullSet.hashMap.new Node<String, Object>(
                "A", MyHashSet.DEFAULT_OBJECT);
        MyHashMap<String, Object>.Node<String, Object> setElement2 = fullSet.hashMap.new Node<String, Object>(
                "B", MyHashSet.DEFAULT_OBJECT);
        MyHashMap<String, Object>.Node<String, Object> setElement3 = fullSet.hashMap.new Node<String, Object>(
                "F", MyHashSet.DEFAULT_OBJECT);

        fullSet.hashMap.hashTable[0] = setElement1;
        fullSet.hashMap.hashTable[1] = setElement2;
        setElement1.setNext(setElement3);

        fullSet.hashMap.size = fullSetSize;

        // setup emptySet
        emptySet = new MyHashSet<>();

    }

    // ----------------MyHashMap class----------------

    // MARK: testConstructor
    /**
     * Test MyHashMap constructor with an invalid initial capacity
     */
    @Test
    public void testMyHashMapConstructorInvalidCapacity() {
        boolean caughtException = false;
        try {
            new MyHashMap<>(-1);
        } catch (IllegalArgumentException exception) {
            caughtException = true;
        }

        assertTrue("Checking Exception Rasied", caughtException);
        caughtException = false;

        try {
            new MyHashMap<>(0);
        } catch (IllegalArgumentException exception) {
            caughtException = true;
        }

        assertTrue("Checking Exception Raised", caughtException);
    }

    // MARK: testGetNullKey
    /**
     * Test MyHashMap get with a null key
     */
    @Test
    public void testMyHashMapGetNullKey() {
        // use Null key on an emptyList
        testExceptionRaised(emptyMap, NullPointerException.class,
                (map) -> map.get(null));

        // use Null key on a fullList
        testExceptionRaised(emptyMap, NullPointerException.class,
                (map) -> map.get(null));
    }

    // MARK: testGetKeyDoesNotExist
    /**
     * Test MyHashMap get with a key that does not exist in the hash table
     */
    @Test
    public void testMyHashMapGetKeyDoesNotExist() {
        // test retrieving an element from an emptyMap
        this.storeHashMap(emptyMap);
        Integer result = emptyMap.get("test");

        assertEquals("Checking null return", null, result);
        checkHashMap(emptyMap);

        // test retrieving an element from a fullMap
        this.storeHashMap(fullMap);
        result = fullMap.get("D");

        assertEquals("Checking null return", null, result);
        checkHashMap(fullMap);
    }

    // MARK: testPutNullKey
    /**
     * Test MyHashMap put with a null key
     */
    @Test
    public void testMyHashMapPutNullKey() {
        // use Null key on an empty map
        testExceptionRaised(emptyMap, NullPointerException.class,
                (map) -> map.put(null, null));
        testExceptionRaised(emptyMap, NullPointerException.class,
                (map) -> map.put(null, 1));

        // use Null key on a full map
        testExceptionRaised(fullMap, NullPointerException.class,
                (map) -> map.put(null, null));
        testExceptionRaised(fullMap, NullPointerException.class,
                (map) -> map.put(null, 1));
    }

    // MARK: testPutKeyAlreadyExists
    /**
     * Test MyHashMap put with a key that already exists in the hash table
     */
    @Test
    public void testMyHashMapPutKeyAlreadyExists() {
        // check trivial put
        this.storeHashMap(fullResizedMap);
        this.storedHashCapacity = fullResizedSize;
        int result = fullMap.put("A", 1);

        printTranslatedHashMap(storedHashMap);

        printTranslatedHashMap(translateHashMap(fullMap));

        assertEquals("Checking correct return", 1, result);
        this.checkHashMap(fullMap);

        // check puts for key at the end of a linked list
        MyHashMap<String, Integer>.Node<String, Integer> newNode = fullMap.new Node<String, Integer>(
                "F", fullResizedSize);
        this.storeHashMap(fullMap);
        this.storedHashMap[0][0] = newNode;
        result = fullMap.put("F", fullResizedSize);

        assertEquals("Checking correct return", 6, result);
        this.checkHashMap(fullMap);

        // check puts for key at the beginning of a linked list
        newNode = fullMap.new Node<String, Integer>("A", fullResizedSize);
        this.storedHashMap[5][0] = newNode;
        result = fullMap.put("A", fullResizedSize);

        assertEquals("Checking correct return", 1, result);
        this.checkHashMap(fullMap);

        // check puts for key not in linked list
        newNode = fullMap.new Node<String, Integer>("C", fullResizedSize);
        this.storedHashMap[7][0] = newNode;
        result = fullMap.put("C", fullResizedSize);

        assertEquals("Checking correct return", 3, result);
        this.checkHashMap(fullMap);
    }

    // MARK: testRemoveNullKey
    /**
     * Test MyHashMap remove with a null key
     */
    @Test
    public void testMyHashMapRemoveNullKey() {
        // remove null key from empty map
        testExceptionRaised(emptyMap, NullPointerException.class,
                (map) -> map.remove(null));

        // remove null key from full map
        testExceptionRaised(fullMap, NullPointerException.class,
                (map) -> map.remove(null));
    }

    // MARK: testRemoveKeyDoesNotExist
    /**
     * Test MyHashMap remove with a key that does not exist in the hash table
     */
    @Test
    public void testMyHashMapRemoveKeyDoesNotExist() {
        // test removing an element from an emptyMap
        this.storeHashMap(emptyMap);
        Integer result = emptyMap.remove("test");

        assertEquals("Checking null return", null, result);
        checkHashMap(emptyMap);

        // test removing an element from a fullMap
        this.storeHashMap(fullMap);
        result = fullMap.remove("D");

        assertEquals("Checking null return", null, result);
        checkHashMap(fullMap);
    }

    // MARK: testGetHashNullKey
    /**
     * Test MyHashMap getHash with a null key
     */
    @Test
    public void testMyHashMapGetHashNullKey() {
        // test getting hash for an empty map
        this.testExceptionRaised(emptyMap, NullPointerException.class,
                (map) -> map.getHash(null, fullResizedSize));

        // test getting hash for a full map
        this.testExceptionRaised(fullMap, NullPointerException.class,
                (map) -> map.getHash(null, fullResizedSize));

        // test getting hash with invalid capacity
        this.testExceptionRaised(fullMap, NullPointerException.class,
                (map) -> map.getHash(null, -fullResizedSize));
    }

    // ----------------MyHashSet class----------------

    // MARK: testAddAlreadyExists
    /**
     * Test MyHashMap put with a key that already exists in the hash table
     */
    @Test
    public void testMyHashSetAddAlreadyExists() {
        // check puts for key at the beginning of a linked list
        this.storeHashMap(fullSet.hashMap);
        boolean result = fullSet.add("A");

        assertTrue("Checking correct return", !result);
        this.checkHashMap(fullSet.hashMap);

        // check puts for key at the end of a linked list
        this.storeHashMap(fullSet.hashMap);
        result = fullSet.add("F");

        assertTrue("Checking correct return", !result);
        this.checkHashMap(fullSet.hashMap);

        // check puts for key not in linked list
        this.storeHashMap(fullSet.hashMap);
        result = fullSet.add("B");

        assertTrue("Checking correct return", !result);
        this.checkHashMap(fullSet.hashMap);
    }

    // MARK: testRemoveDoesNotExist
    /**
     * Test MyHashSet remove with a key that does not exist in the hash table
     */
    @Test
    public void testMyHashSetRemoveDoesNotExist() {
        // test removing an element from an empty set
        this.storeHashMap(emptySet.hashMap);
        boolean result = emptySet.remove("test");

        assertTrue("Checking correct return", !result);
        checkHashMap(emptySet.hashMap);

        // test removing an element from a full set
        this.storeHashMap(fullSet.hashMap);
        result = fullSet.remove("C");

        assertTrue("Checking correct return", !result);
        checkHashMap(fullSet.hashMap);
    }
}