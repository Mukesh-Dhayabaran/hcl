package org.example;

public class ResourceDemo {

    static class DemoResource implements AutoCloseable {
        private final String name;

        DemoResource(String name) {
            this.name = name;
            System.out.println(name + " opened");
        }

        @Override
        public void close() {
            System.out.println(name + " closed");
        }
    }

    public static void demonstrate() {
        try (DemoResource first = new DemoResource("First");
             DemoResource second = new DemoResource("Second")) {

            System.out.println("Inside try block");

        }
    }
}
