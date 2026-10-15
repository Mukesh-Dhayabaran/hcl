package org.example;

public class StackOverflowLab {

    private static int recursionDepth = 0;

    private static void recurse() {
        recursionDepth++;
        recurse();
    }

    public static void main(String[] args) {
        try {
            recurse();
        } catch (StackOverflowError error) {
            System.out.println("StackOverflowError occurred.");
            System.out.println("Recursion depth reached: " + recursionDepth);
        }
    }
}
