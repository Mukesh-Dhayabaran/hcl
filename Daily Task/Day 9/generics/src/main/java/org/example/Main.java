package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Pair<Integer, String> student = new Pair<>(101, "Mukesh");

        System.out.println(student.getKey());
        System.out.println(student.getValue());

        Box<Integer> integerBox = new Box<>(100);
        Box<Double> doubleBox = new Box<>(25.5);

        System.out.println(integerBox.getDoubleValue());
        System.out.println(doubleBox.getDoubleValue());

        System.out.println(GenericUtils.max(10, 25));
        System.out.println(GenericUtils.max("Apple", "Mango"));
        System.out.println(GenericUtils.max(15.5, 12.5));


        List<Integer> integerNumbers = List.of(10, 20, 30);
        List<Double> decimalNumbers = List.of(1.5, 2.5);

        System.out.println(WildcardDemo.sumNumbers(integerNumbers));
        System.out.println(WildcardDemo.sumNumbers(decimalNumbers));

        List<Number> destination = new ArrayList<>();
        WildcardDemo.addIntegers(destination);
        System.out.println(destination);

        try {
            ProductCsvReader.readProducts();
        } catch (java.io.IOException e) {
            System.out.println("Could not read products: " + e.getMessage());
        }

        ResourceDemo.demonstrate();
    }
}
