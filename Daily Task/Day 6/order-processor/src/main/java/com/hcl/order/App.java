package com.hcl.order;

public class App {

    public static void main(String[] args) {

        OrderProcessor processor = new OrderProcessor();

        // Test 1: Successful order
        try {
            processor.processOrder(101, 2500);
        } catch (InvalidOrderException e) {
            System.err.println("Invalid order: " + e.getMessage());
        }

        // Test 2: Checked exception
        try {
            processor.processOrder(0, 2500);
        } catch (InvalidOrderException e) {
            System.err.println("Checked exception: " + e.getMessage());
        }

        // Test 3: Unchecked exception and exception chaining
        try {
            processor.processOrder(102, -500);
        } catch (InvalidOrderException e) {
            System.err.println("Invalid order: " + e.getMessage());
        } catch (OrderProcessingException e) {
            System.err.println("Processing failed: " + e.getMessage());
            System.err.println("Root cause: " + e.getCause());
        }
    }
}