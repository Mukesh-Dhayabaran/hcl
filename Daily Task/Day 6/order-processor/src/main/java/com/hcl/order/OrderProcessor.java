package com.hcl.order;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderProcessor {

    private static final Logger logger =
            Logger.getLogger(OrderProcessor.class.getName());

    public void processOrder(int orderId, double amount)
            throws InvalidOrderException {

        logger.info("AUDIT: Starting order " + orderId);

        try {
            if (orderId <= 0) {
                throw new InvalidOrderException(
                        "Order ID must be positive");
            }

            if (amount <= 0) {
                throw new IllegalArgumentException(
                        "Order amount must be positive");
            }

            // Simulate a processing failure for demonstration.
            if (amount > 100_000) {
                throw new ArithmeticException(
                        "Order amount exceeds processing limit");
            }

            logger.info("Order " + orderId
                    + " processed successfully. Amount: " + amount);

        } catch (IllegalArgumentException | ArithmeticException e) {
            // Exception chaining: retain the original exception as the cause.
            throw new OrderProcessingException(
                    "Failed to process order " + orderId, e);

        } finally {
            logger.info("AUDIT: Finished processing order " + orderId);
        }
    }
}