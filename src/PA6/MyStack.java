/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA6 Write-up
   
  This file is for CSE 12 PA6 in Spring 2025,
*/

/**
 * This class implements the Stack ADT using a MyDeque instance variable called
 * theStack.
 * 
 * The implementation uses the front of the queue as the top of the stack
 */
public class MyStack<E> implements StackInterface<E> {
    MyDeque<E> theStack;

    /**
     * Constructor to create new MyStack that holds a MyDeque.
     *
     * @param initialCapacity The max amount of elements this data structure can
     *                        hold.
     */
    public MyStack(int initialCapacity) {
        this.theStack = new MyDeque(initialCapacity);
    }

    /**
     * Checks if the stack is empty.
     *
     * @return True if there are no elements in the stack, false otherwise.
     */
    @Override
    public boolean empty() {
        return this.theStack.size() == 0;
    }

    /**
     * Adds the specified element to the top of this StackInterface.
     *
     * @param element the element to add to the stack
     */
    @Override
    public void push(E element) {
        this.theStack.addFirst(element);
    }

    /**
     * Removes the element at the top of this StackInterface. Returns the
     * element removed, or null if there was no such element.
     *
     * @return the element removed, or null if the size was zero.
     */
    @Override
    public E pop() {
        return this.theStack.removeFirst();
    }

    /**
     * Returns the element at the top of this stack, or null if there was no
     * such element.
     *
     * @return the element at the top, or null if the size was zero
     */
    @Override
    public E peek() {
        return this.theStack.peekFirst();
    }

    /**
     * Returns the number of elements in this stack.
     *
     * @return the number of elements in this stack.
     */
    @Override
    public int size() {
        return theStack.size();
    }

}
