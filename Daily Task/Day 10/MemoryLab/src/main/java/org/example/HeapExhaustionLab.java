package org.example;

import java.util.ArrayList;
import java.util.List;

public class HeapExhaustionLab {

    public static void main(String[] args) {
        List<byte[]> memoryBlocks = new ArrayList<>();
        long totalAllocated = 0;
        int blockSize = 1024 * 1024;

        try {
            while (true) {
                memoryBlocks.add(new byte[blockSize]);
                totalAllocated += blockSize;

                System.out.println(
                        "Allocated approximately "
                                + totalAllocated / (1024 * 1024)
                                + " MiB"
                );
            }
        } catch (OutOfMemoryError error) {
            System.out.println("OutOfMemoryError occurred.");
            System.out.println(
                    "Approximate requested allocation: "
                            + totalAllocated / (1024 * 1024)
                            + " MiB"
            );
        }
    }
}
