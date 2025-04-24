import java.util.AbstractList;

// MARK: MyLinkedList
public class MyLinkedList<E> extends AbstractList<E> {

    int size;
    Node head;
    Node tail;

    // MARK: Node
    /**
     * A Node class that holds data and references to previous and next Nodes.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /**
         * Constructor to create singleton Node
         * 
         * @param element Element to add, can be null
         */
        public Node(E element) {
            // Initialize the instance variables
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /**
         * Set the parameter prev as the previous node
         * 
         * @param prev new previous node
         */
        public void setPrev(Node prev) {
            this.prev = prev;
        }

        /**
         * Set the parameter next as the next node
         * 
         * @param next new next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * Set the parameter element as the node's data
         * 
         * @param element new element
         */
        public void setElement(E element) {
            this.data = element;
        }

        /**
         * Accessor to get the next Node in the list
         * 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * Accessor to get the prev Node in the list
         * 
         * @return the previous node
         */
        public Node getPrev() {
            return this.prev;
        }

        /**
         * Accessor to get the Nodes Element
         * 
         * @return this node's data
         */
        public E getElement() {
            return this.data;
        }
    }

    // MARK: checkIndexBounds
    private void checkIndexBounds(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    // MARK: Initializer
    public MyLinkedList() {
        Node dummyHead = new Node(null);
        Node dummyTail = new Node(null);
        this.head = dummyHead;
        this.tail = dummyTail;

        this.head.setNext(this.tail);
        this.tail.setPrev(this.head);

        this.size = 0;
    }

    // MARK: Size
    @Override
    public int size() {
        return this.size;
    }

    // MARK: getNth
    protected Node getNth(int index) {
        checkIndexBounds(index);

        Node currentNode = this.head;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    // MARK: get
    @Override
    public E get(int index) {
        Node node = getNth(index);
        return node.getElement();
    }

    // MARK: add
    @Override
    public void add(int index, E data) {
        if (index != this.size) {
            checkIndexBounds(index);
        }

        if (data == null) {
            throw new NullPointerException();
        }

        Node newNode = new Node(data);

        if (index == this.size) {
            newNode.setPrev(this.tail.getPrev());
            newNode.setNext(this.tail);
            this.tail.getPrev().setNext(newNode);
            this.tail.setPrev(newNode);

        } else {
            // move to the correct position in the list
            Node currentNode = getNth(index);

            // Insert the new node
            newNode.setNext(currentNode);
            newNode.setPrev(currentNode.getPrev());
            currentNode.getPrev().setNext(newNode);
            currentNode.setPrev(newNode);
        }

        this.size += 1;
    }

    @Override
    public boolean add(E data) {
        this.add(this.size, data);
        return true;
    }

    // MARK: set
    @Override
    public E set(int index, E data) {
        this.checkIndexBounds(index);

        if (data == null) {
            throw new NullPointerException();
        }

        Node currentNode = this.getNth(index);
        E previousData = currentNode.getElement();
        currentNode.setElement(data);

        return previousData;
    }

    // MARK: Remove
    @Override
    public E remove(int index) {
        this.checkIndexBounds(index);

        Node currentNode = this.getNth(index);
        E previousData = currentNode.getElement();

        Node previousNode = currentNode.getPrev();
        Node nextNode = currentNode.getNext();

        nextNode.setPrev(previousNode);
        previousNode.setNext(nextNode);

        this.size -= 1;

        return previousData;
    }

    // MARK: Clear
    @Override
    public void clear() {
        int initialSize = this.size;
        for (int i = 0; i < initialSize; i++) {
            this.remove(0);
        }
    }

    // MARK: isEmpty
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    // MARK: Contains
    public boolean contains(E data, int start, int end) {
        // check that the indicies are valid
        this.checkIndexBounds(start);
        if (end != this.size) {
            this.checkIndexBounds(end);
        }

        if (end <= start) {
            return false;
        }

        Node currentNode = this.getNth(start);
        for (int i = start; i < end; i++) {
            if (currentNode.getElement().equals(data)) {
                return true;
            }
            currentNode = currentNode.getNext();
        }

        return false;
    }

    // MARK: indexOfElement
    public int indexOfElement(E data) {
        if (data == null) {
            throw new NullPointerException();
        }

        Node currentNode = this.head;
        for (int i = 0; i < this.size; i++) {
            currentNode = currentNode.getNext();
            if (currentNode.getElement().equals(data)) {
                return i;
            }
        }

        return -1;
    }
}
