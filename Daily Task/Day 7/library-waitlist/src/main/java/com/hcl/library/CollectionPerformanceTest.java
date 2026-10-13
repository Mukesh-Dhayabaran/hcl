
package com.hcl.library;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CollectionPerformanceTest {

    private static final int SIZE = 100_000;
    private static final int ACCESS_COUNT = 10_000;

    private static long measureAppend(List<Integer> list) {
        long start = System.nanoTime();

        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }

        return System.nanoTime() - start;
    }

    private static long measureMiddleInsert(List<Integer> list) {
        // Insert repeatedly at the middle to compare shifting and traversal.
        long start = System.nanoTime();

        for (int i = 0; i < 1_000; i++) {
            list.add(list.size() / 2, i);
        }

        return System.nanoTime() - start;
    }

    private static long measureMiddleAccess(List<Integer> list) {
        long start = System.nanoTime();

        for (int i = 0; i < ACCESS_COUNT; i++) {
            list.get(SIZE / 2);
        }

        return System.nanoTime() - start;
    }

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        long arrayAppend = measureAppend(arrayList);
        long linkedAppend = measureAppend(linkedList);

        long arrayInsert = measureMiddleInsert(new ArrayList<>(arrayList));
        long linkedInsert = measureMiddleInsert(new LinkedList<>(linkedList));

        long arrayAccess = measureMiddleAccess(arrayList);
        long linkedAccess = measureMiddleAccess(linkedList);

        System.out.println("Operation (nanoseconds) | ArrayList | LinkedList");
        System.out.printf("Append %d items | %d | %d%n",
                SIZE, arrayAppend, linkedAppend);
        System.out.printf("1,000 middle inserts | %d | %d%n",
                arrayInsert, linkedInsert);
        System.out.printf("%,d middle accesses | %d | %d%n",
                ACCESS_COUNT, arrayAccess, linkedAccess);
    }
}
