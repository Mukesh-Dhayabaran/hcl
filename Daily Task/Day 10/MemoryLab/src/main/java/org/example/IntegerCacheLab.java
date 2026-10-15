package org.example;

public class IntegerCacheLab {

    public static void main(String[] args) {
        Integer firstSmall = 127;
        Integer secondSmall = 127;

        Integer firstLarge = 128;
        Integer secondLarge = 128;

        System.out.println("127 compared using ==: " + (firstSmall == secondSmall));
        System.out.println("128 compared using ==: " + (firstLarge == secondLarge));

        System.out.println("128 compared using equals(): " + firstLarge.equals(secondLarge));
    }
}