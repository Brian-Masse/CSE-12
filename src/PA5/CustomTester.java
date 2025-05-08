/*
 * Name: Casey Hild
 * Email: child@ucsd.edu
 * PID: A16953257
 * Sources Used: JDK 17 Doc
 *
 * IMPORTANT: Do not change the method headers. Your tests will be run against
 * good and bad implementations of Student/Course/Sanctuary. You will only
 * receive points if your test passes when run on a good implementation and
 * fails for the corresponding bad implementation.
 */

import static org.junit.Assert.*;
import org.junit.*;

/**
 * The custom tester for PA5, which covers some basic test cases.
 */
public class CustomTester {

    @FunctionalInterface
    interface HashMapOperation {
        void execute(MyHashMap map);
    }

    private MyHashMap.Node[][] storedHashMap;
    private int storedHashSize;

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

                    linkedList[currentIndex] = node;

                    node = node.getNext();
                    currentIndex += 1;
                }

                translatedMap[i] = linkedList;
            }
        }

        return translatedMap;
    }

    private void storeHashMap(MyHashMap map) {
        this.storedHashMap = translateHashMap(map);
        this.storedHashSize = map.size;
    }

    private void checkHashMap(MyHashMap map) {
        MyHashMap.Node[][] translatedMap = this.translateHashMap(map);

        assertEquals("Checking size", storedHashSize, map.size);
        assertArrayEquals("Checking hashMapCorrectness", storedHashMap,
                translatedMap);

        int length = this.storedHashMap.length;
        for (int i = 0; i < length; i++) {
            String msg = "Checking LinkedList Correctness (" + i + ")";
            assertArrayEquals(msg, this.storedHashMap[i], translatedMap[i]);
        }
    }

    public void testExceptionRaised(MyHashMap map, Class<?> exceptionType,
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

    /**
     * This sets up the test fixture. JUnit invokes this method before every
     * testXXX method. The @Before tag tells JUnit to run this method before
     * each test.
     */
    // MARK: Before
    @Before
    public void setup() {
        // Optional: add setup here
    }

    // ----------------MyHashMap class----------------

    // MARK: testConstructor
    /**
     * Test MyHashMap constructor with an invalid initial capacity
     */
    @Test
    public void testMyHashMapConstructorInvalidCapacity() {

        MyHashMap map = new MyHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("F", 3);

        MyHashMap.printMap(map);

        this.printTranslatedHashMap(translateHashMap(map));

    }

    // MARK: testGetNullKey
    /**
     * Test MyHashMap get with a null key
     */
    @Test
    public void testMyHashMapGetNullKey() {
        // TODO: add your test here
    }

    // MARK: testGetKeyDoesNotExist
    /**
     * Test MyHashMap get with a key that does not exist in the hash table
     */
    @Test
    public void testMyHashMapGetKeyDoesNotExist() {
        // TODO: add your test here
    }

    // MARK: testPutNullKey
    /**
     * Test MyHashMap put with a null key
     */
    @Test
    public void testMyHashMapPutNullKey() {
        // TODO: add your test here
    }

    // MARK: testPutKeyAlreadyExists
    /**
     * Test MyHashMap put with a key that already exists in the hash table
     */
    @Test
    public void testMyHashMapPutKeyAlreadyExists() {
        // TODO: add your test here
    }

    // MARK: testRemoveNullKey
    /**
     * Test MyHashMap remove with a null key
     */
    @Test
    public void testMyHashMapRemoveNullKey() {
        // TODO: add your test here
    }

    // MARK: testRemoveKeyDoesNotExist
    /**
     * Test MyHashMap remove with a key that does not exist in the hash table
     */
    @Test
    public void testMyHashMapRemoveKeyDoesNotExist() {
        // TODO: add your test here
    }

    // MARK: testGetHashNullKey
    /**
     * Test MyHashMap getHash with a null key
     */
    @Test
    public void testMyHashMapGetHashNullKey() {
        // TODO: add your test here
    }

    // ----------------MyHashSet class----------------

    // MARK: testAddAlreadyExists
    /**
     * Test MyHashMap put with a key that already exists in the hash table
     */
    @Test
    public void testMyHashSetAddAlreadyExists() {
        // TODO: add your test here
    }

    // MARK: testRemoveDoesNotExist
    /**
     * Test MyHashSet remove with a key that does not exist in the hash table
     */
    @Test
    public void testMyHashSetRemoveDoesNotExist() {
        // TODO: add your test here
    }
}