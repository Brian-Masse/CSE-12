/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA6 Write-up
   
  This file is for CSE 12 PA6 in Spring 2025,
*/

/**
 * This class contains an algorithm utilizing a stack or queue.
 */
public class MyDequeAlgorithm {
    private static final String SET1 = "()";
    private static final String SET2 = "[]";
    private static final String SET3 = "{}";

    private static final int DEFAULT_CAPACITY = 5;

    /**
     * Returns whether or not the given string contains a valid arrangement of
     * brackets
     * 
     * @param input the input string containing brackets
     * @throws NullPointerException if the given string is null
     * @return the whether or not the given string contains a valid arrangement
     *         of brackets
     */
    public static boolean isValidBrackets(String input) {
        if (input == null) {
            throw new NullPointerException();
        }

        String[] validSets = { SET1, SET2, SET3 };
        int setCount = validSets.length;

        MyStack<Character> stack = new MyStack<Character>(DEFAULT_CAPACITY);
        int length = input.length();

        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);

            // if c is an openning bracket, push to the stack
            // if c is a closing bracket, check that the last element in the
            // stack was the matching bracket. If not, then the set is invalid.
            for (int j = 0; j < setCount; j++) {
                if (validSets[j].charAt(0) == c) {
                    stack.push(c);
                }

                if (validSets[j].charAt(1) == c) {
                    char mostRecentBracket = stack.pop();
                    if (mostRecentBracket != validSets[j].charAt(0)) {
                        return false;
                    }
                }
            }
        }

        return stack.empty();
    }
}