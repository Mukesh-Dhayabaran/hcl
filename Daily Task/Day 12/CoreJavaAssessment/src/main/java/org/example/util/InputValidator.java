package org.example.util;

public class InputValidator {

    private InputValidator() {
    }

    public static int readPositiveInteger(String input) {
        try {
            int value = Integer.parseInt(input.trim());

            if (value < 0) {
                throw new IllegalArgumentException(
                        "Value cannot be negative");
            }

            return value;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Please enter a valid integer", e);
        }
    }

    public static int readStrictlyPositiveInteger(String input) {
        int value = readPositiveInteger(input);

        if (value == 0) {
            throw new IllegalArgumentException(
                    "Value must be greater than zero");
        }

        return value;
    }

    public static double readPositiveAmount(String input) {
        try {
            double value = Double.parseDouble(input.trim());

            if (!Double.isFinite(value) || value <= 0) {
                throw new IllegalArgumentException(
                        "Amount must be positive and finite");
            }

            return value;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Please enter a valid amount", e);
        }
    }
}
