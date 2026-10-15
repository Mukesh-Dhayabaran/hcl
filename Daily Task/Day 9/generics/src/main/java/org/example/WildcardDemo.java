package org.example;

import java.util.ArrayList;
import java.util.List;

public class WildcardDemo {

    public static double sumNumbers(List<? extends Number> numbers) {
        double total = 0;

        for (Number number : numbers) {
            total += number.doubleValue();
        }

        return total;
    }

    public static void addIntegers(List<? super Integer> numbers) {
        numbers.add(10);
        numbers.add(20);
    }
}
