/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA8 Write-up
   
  This file is for CSE 12 PA8 in Spring 2025,
*/

import java.util.ArrayList;

// MARK: MyBST
/**
 * This class represents a generic BST data type
 * 
 * It contains standard functions including insert, remove, search
 */
public class MyBST<K extends Comparable<K>, V> {

    // instance variables
    MyBSTNode<K, V> root = null;
    int size = 0;

    // MARK: Size
    /**
     * Gets the number of elements in the BST
     * 
     * @return number of elements in the BST
     */
    public int size() {
        return size;
    }

    // MARK: Insert
    /**
     * Inserts a new element into the BST. If the key already exists, the vlaue
     * is replaced
     * 
     * @param key   the key of the new element
     * @param value the value of the new element
     * @return the value of the old element, if it was updated
     */
    public V insert(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }

        MyBSTNode<K, V> currentNode = this.root;
        MyBSTNode<K, V> newNode = new MyBSTNode<K, V>(key, value, currentNode);

        this.size++;

        if (currentNode == null) {
            this.root = newNode;
        }

        while (currentNode != null) {
            K currentNodeKey = (K) currentNode.getKey();

            // if the keys are equal, update the old value, and return it
            if (key.equals(currentNodeKey)) {
                V currentNodeValue = (V) currentNode.getValue();
                currentNode.setValue(value);
                this.size--;
                return currentNodeValue;
            }

            newNode.setParent(currentNode);

            // if the new node is greater than the current, check to see if its
            // a leaf. If it is, insert it, otherwise, continue
            if (key.compareTo(currentNodeKey) > 0) {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(newNode);
                    return null;
                }
                currentNode = currentNode.right;
            }

            // if the new node is less than the current, check to see if its
            // a leaf. If it is, insert it, otherwise, continue
            if (key.compareTo(currentNodeKey) < 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(newNode);
                    return null;
                }

                currentNode = currentNode.left;
            }
        }

        return null;
    }

    // MARK: Search
    /**
     * Searches for an element in the BST. If there is no match it returns null
     * 
     * @param key the key of the element to search for
     * @return the value at the key
     */
    public V search(K key) {
        MyBSTNode<K, V> result = searchForNode(key);
        return result == null ? null : (V) result.getValue();
    }

    // MARK: searchForNode
    /**
     * Searches for an element in the BST. If there is a match it returns a
     * reference to the node
     * 
     * @param key the key of the element to search for
     * @return the node at the key
     */
    private MyBSTNode<K, V> searchForNode(K key) {
        MyBSTNode<K, V> currentNode = this.root;

        if (key == null) {
            return null;
        }

        while (currentNode != null) {
            K currentKey = (K) currentNode.getKey();

            if (key.equals(currentKey)) {
                return currentNode;
            }

            // if the key your searching for is bigger, go to the right subtree
            if (key.compareTo(currentKey) > 0) {
                currentNode = currentNode.getRight();
            }

            // if the key your searching for is smaller, go to the left subtree
            if (key.compareTo(currentKey) < 0) {
                currentNode = currentNode.getLeft();
            }
        }

        return null;
    }

    // MARK: Remove
    /**
     * Removes an element from the BST. If no elements for the given key are
     * found it returns null
     * 
     * @param key the key of the element to remove
     * @return the value of the node being removed
     */
    public V remove(K key) {
        // first get a reference to the node to remove
        MyBSTNode<K, V> removingNode = searchForNode(key);
        if (removingNode == null) {
            return null;
        }

        V value = (V) removingNode.getValue();

        // if you are a leaf node, update your parent
        if (removingNode.getRight() == null && removingNode.getLeft() == null) {
            this.size--;
            MyBSTNode<K, V> parent = removingNode.getParent();

            // if the parent is null, update the root instance var
            if (parent == null) {
                this.root = null;
                return value;
            }

            // otherwise update the correct side of the parrent
            MyBSTNode<K, V> right = parent.getRight();
            if (right != null && right.equals(removingNode)) {
                parent.setRight(null);
            } else {
                parent.setLeft(null);
            }
            return value;
        }

        // if you have one child
        if (removingNode.getRight() == null || removingNode.getLeft() == null) {
            this.size--;
            MyBSTNode<K, V> child = removingNode.getRight() == null
                    ? removingNode.getLeft()
                    : removingNode.getRight();

            MyBSTNode<K, V> parent = removingNode.getParent();

            if (parent == null) {
                this.root = child;
                return value;
            }

            // if you are on the left side of your parent, update its left child
            if (((K) removingNode.getKey()).compareTo(parent.getKey()) < 0) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }

            child.setParent(parent);
            return value;
        }

        // if you have two children
        MyBSTNode<K, V> successor = removingNode.successor();
        this.remove(successor.getKey());

        removingNode.setKey(successor.getKey());
        removingNode.setValue(successor.getValue());

        return value;
    }

    // MARK: InOrder
    /**
     * Scans through the BST, and collects each node in ascending order
     * 
     * @return the list of nodes in the BST, sorted
     */
    public ArrayList<MyBSTNode<K, V>> inorder() {
        ArrayList<MyBSTNode<K, V>> list = new ArrayList<MyBSTNode<K, V>>();

        // find the smallest element in the tree
        MyBSTNode<K, V> currentNode = this.root;
        if (currentNode == null) {
            return list;
        }

        while (currentNode.getLeft() != null) {
            currentNode = currentNode.getLeft();
        }

        while (currentNode != null) {
            list.add(currentNode);
            currentNode = currentNode.successor();
        }

        return list;
    }

    // MARK: copy
    /**
     * Copies all the elements in a BST. The new nodes do not point to the same
     * data in memory
     * 
     * @return the copied BST
     */
    public MyBST<K, V> copy() {
        MyBST<K, V> copy = new MyBST<>();

        copyNode(copy, this.root);

        return copy;
    }

    // MARK: CopyNode
    /**
     * Copies an individual node and its children into a container BST
     * 
     * @param BST  the BST to copy into
     * @param node the node to copy
     */
    private void copyNode(MyBST<K, V> BST, MyBSTNode<K, V> node) {
        if (node == null) {
            return;
        }

        BST.insert(node.getKey(), node.getValue());
        copyNode(BST, node.getLeft());
        copyNode(BST, node.getRight());
    }

    // MARK: BSTNode
    /**
     * This class represents a generic BST Node data type
     * 
     * It contains standard functions
     */
    static class MyBSTNode<K, V> {
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K, V> parent;
        private MyBSTNode<K, V> left = null;
        private MyBSTNode<K, V> right = null;

        /**
         * Creates a MyBSTNode storing specified data
         *
         * @param key    the key the MyBSTNode will
         * @param value  the data the MyBSTNode will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Return the key stored in the the MyBSTNode
         *
         * @return the key stored in the MyBSTNode
         */
        public K getKey() {
            return key;
        }

        /**
         * Set the key stored in the MyBSTNode
         *
         * @param newKey the key to be stored
         */
        public void setKey(K newKey) {
            this.key = newKey;
        }

        /**
         * Return data stored in the MyBSTNode
         *
         * @return the data stored in the MyBSTNode
         */
        public V getValue() {
            return value;
        }

        /**
         * Set the data stored in the MyBSTNode
         *
         * @param newValue the data to be stored
         */
        public void setValue(V newValue) {
            this.value = newValue;
        }

        /**
         * Return the parent
         *
         * @return the parent
         */
        public MyBSTNode<K, V> getParent() {
            return parent;
        }

        /**
         * Set the parent
         *
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K, V> newParent) {
            this.parent = newParent;
        }

        /**
         * Return the left child
         *
         * @return left child
         */
        public MyBSTNode<K, V> getLeft() {
            return left;
        }

        /**
         * Set the left child
         *
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K, V> newLeft) {
            this.left = newLeft;
        }

        /**
         * Return the right child
         *
         * @return right child
         */
        public MyBSTNode<K, V> getRight() {
            return right;
        }

        /**
         * Set the right child
         *
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K, V> newRight) {
            this.right = newRight;
        }

        // MARK: Successor
        /**
         * Finds the next largest element in the BST. If none exist, it returns
         * null
         * 
         * @return the next largest element
         */
        public MyBSTNode<K, V> successor() {
            MyBSTNode<K, V> rightChild = this.getRight();

            // if you dont have a right child, the only possible successor will
            // be your parent
            if (rightChild == null) {
                return findSuccessorAbove(this);
            }

            return findSuccessorBelow(rightChild);
        }

        // MARK: findSuccessorAbove
        /**
         * recursivley look above the current node until you hit a node that is
         * larger than the current
         * 
         * @param currentNode the node to start the search at
         * @return the next largest element
         */
        private MyBSTNode<K, V> findSuccessorAbove(
                MyBSTNode<K, V> currentNode) {
            MyBSTNode<K, V> parent = currentNode.getParent();
            if (parent == null) {
                return null;
            }

            MyBSTNode<K, V> leftNode = parent.getLeft();
            if (leftNode != null && leftNode.equals(currentNode)) {
                return parent;
            }

            return findSuccessorAbove(parent);
        }

        // MARK: findSuccessorBelow
        /**
         * recursivley look down from the current node until you hit the
         * smallest node
         * 
         * @param currentNode the node to start the search at
         * @return the next largest element
         */

        private MyBSTNode<K, V> findSuccessorBelow(
                MyBSTNode<K, V> currentNode) {
            MyBSTNode<K, V> leftChild = currentNode.getLeft();
            // if there is no left child, then you are the smallest node.
            if (leftChild == null) {
                return currentNode;
            }

            return findSuccessorBelow(leftChild);
        }

        /**
         * This method compares if two node objects are equal.
         *
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K, V> comp = (MyBSTNode<K, V>) obj;

            return ((this.getKey() == null ? comp.getKey() == null
                    : this.getKey().equals(comp.getKey()))
                    && (this.getValue() == null ? comp.getValue() == null
                            : this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         *
         * @return "Key:Value" that represents the node object
         */
        public String toString() {
            return String.format(TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }

}
