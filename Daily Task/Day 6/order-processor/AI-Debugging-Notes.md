# Day 6 — AI Debugging Notes

## Stack Trace 1: Unhandled Checked Exception

**Example error:**
```text
unreported exception InvalidOrderException;
must be caught or declared to be thrown
```

**Cause:** `InvalidOrderException` extends `Exception`, making it a checked exception. Java requires the calling method to handle it using `try-catch` or declare it with `throws`.

**Resolution:** Added a `catch (InvalidOrderException e)` block around the relevant `processOrder()` call in `App.java`.

**Verification:** `mvn clean test` completed successfully.

## Stack Trace 2: Exception Chaining

**Example output:**
```text
Processing failed: Failed to process order 102
Root cause: java.lang.IllegalArgumentException:
Order amount must be positive
```

**Cause:** An invalid amount triggers `IllegalArgumentException`. `OrderProcessor` wraps it in `OrderProcessingException`, preserving the original exception as its cause.

**Resolution:**
```java
throw new OrderProcessingException(
    "Failed to process order " + orderId, e);
```

**Verification:** Running `App` displays both the processing error and its original cause.

## AI Review

The explanations were checked against the Java compiler error and the application's actual output. The checked-exception fix compiles, and the chained exception retains its root cause.