package org.example;

import java.util.ArrayList;
import java.util.List;

public class GarbageCollectionLab {

    public static void main(String[] args) {
        for (int round = 1; round <= 10; round++) {
            List<byte[]> temporaryObjects = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                temporaryObjects.add(new byte[1024 * 1024]);
            }

            System.out.println("Completed allocation round: " + round);
        }

        System.out.println("Garbage collection lab completed.");
    }
}