package org.example;

public class StringPerformanceLab {

    private static final int ITERATIONS = 20_000;

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        String result = "";

        for (int i = 0; i < ITERATIONS; i++) {
            result += i;
        }

        long stringConcatTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ITERATIONS; i++) {
            builder.append(i);
        }

        StringBuilder resultBuilder = builder;
        long stringBuilderTime = System.nanoTime() - startTime;

        System.out.println("String += length: " + result.length());
        System.out.println("String += time: " + stringConcatTime / 1_000_000.0 + " ms");

        System.out.println("StringBuilder length: " + resultBuilder.length());
        System.out.println("StringBuilder time: " + stringBuilderTime / 1_000_000.0 + " ms");
    }
}