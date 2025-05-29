import java.util.ArrayList;

// MARK: MyBST
public class MyBST<K extends Comparable<K>, V> {
    MyBSTNode<K, V> root = null;
    int size = 0;

    // MARK: Size
    public int size() {
        return size;
    }

    // MARK: Insert
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
    public V search(K key) {
        MyBSTNode<K, V> result = searchForNode(key);
        return result == null ? null : (V) result.getValue();
    }

    private MyBSTNode<K, V> searchForNode(K key) {
        MyBSTNode<K, V> currentNode = this.root;

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
    public V remove(K key) {
        // first get a reference to the node to remove
        MyBSTNode<K, V> removingNode = searchForNode(key);
        if (removingNode == null) {
            return null;
        }

        V value = (V) removingNode.getValue();
        this.size--;

        // if you are a leaf node, update your parent
        if (removingNode.getRight() == null && removingNode.getLeft() == null) {
            MyBSTNode<K, V> parent = removingNode.getParent();

            // if the parent is null, update the root instance var
            if (parent == null) {
                this.root = null;
                return value;
            }

            // otherwise update the correct side of the parrent
            if (parent.getRight().equals(removingNode)) {
                parent.setRight(null);
            } else {
                parent.setLeft(null);
            }
            return value;
        }

        // if you have one child
        if (removingNode.getRight() == null || removingNode.getLeft() == null) {
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
    public MyBST<K, V> copy() {
        MyBST<K, V> copy = new MyBST<>();

        copyNode(copy, this.root);

        return copy;
    }

    private void copyNode(MyBST<K, V> BST, MyBSTNode<K, V> node) {
        if (node == null) {
            return;
        }

        BST.insert(node.getKey(), node.getValue());
        copyNode(BST, node.getLeft());
        copyNode(BST, node.getRight());
    }

    // MARK: BSTNode
    static class MyBSTNode<K, V> {
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K, V> parent;
        private MyBSTNode<K, V> left = null;
        private MyBSTNode<K, V> right = null;

        /**
         * Creates a MyBSTNode<K,V> storing specified data
         *
         * @param key    the key the MyBSTNode<K,V> will
         * @param value  the data the MyBSTNode<K,V> will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Return the key stored in the the MyBSTNode<K,V>
         *
         * @return the key stored in the MyBSTNode<K,V>
         */
        public K getKey() {
            return key;
        }

        /**
         * Set the key stored in the MyBSTNode<K,V>
         *
         * @param newKey the key to be stored
         */
        public void setKey(K newKey) {
            this.key = newKey;
        }

        /**
         * Return data stored in the MyBSTNode<K,V>
         *
         * @return the data stored in the MyBSTNode<K,V>
         */
        public V getValue() {
            return value;
        }

        /**
         * Set the data stored in the MyBSTNode<K,V>
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
        public MyBSTNode<K, V> successor() {
            MyBSTNode<K, V> rightChild = this.getRight();

            // if you dont have a right child, the only possible successor will
            // be your parent
            if (rightChild == null) {
                return findSuccessorAbove(this);
            }

            return findSuccessorBelow(rightChild);
        }

        // recursivley look above the current node until you hit a node that is
        // larger than the current
        private MyBSTNode<K, V> findSuccessorAbove(
                MyBSTNode<K, V> currentNode) {
            MyBSTNode<K, V> parent = currentNode.getParent();
            if (parent == null) {
                return null;
            }

            if (parent.getLeft().equals(currentNode)) {
                return parent;
            }

            return findSuccessorAbove(parent);
        }

        // recursivley look down from the current node until you hit the
        // smallest node
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
