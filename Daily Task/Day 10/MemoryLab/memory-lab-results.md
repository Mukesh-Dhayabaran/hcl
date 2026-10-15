### Experiment 1: Stack Overflow

**Objective:** Observe how thread stack size affects recursion depth.

| Stack size | Observed recursion depth |
|---|---:|
| `-Xss256k` — Run 1 | 3,014 |
| `-Xss256k` — Run 2 | 2,395 |
| `-Xss1m` — Run 1 | 19,580 |

**Observation:** The program repeatedly calls the same method until the JVM throws `StackOverflowError`. The larger stack size allowed more recursive calls in the observed run.

**Conclusion:** Stack size influences recursion depth, but the exact number of calls varies with JVM runtime conditions and should not be treated as a fixed limit.

### Experiment 2: Heap Exhaustion

**Objective:** Observe what happens when a Java program exceeds its available heap memory.

**Command:** `java -Xmx64m -cp target/classes org.example.HeapExhaustionLab`

**Result:**

| Measurement | Observation |
|---|---|
| Maximum heap setting | 64 MiB |
| Allocation block size | 1 MiB |
| Successful array payload allocated | Approximately 31 MiB |
| Final result | `OutOfMemoryError` |

**Observation:** The program stored each allocated byte array in an `ArrayList`, keeping references to the arrays. Because these objects remained reachable, garbage collection could not reclaim them while the list retained them.

**Conclusion:** `-Xmx64m` limits the Java heap, but it does not guarantee that 64 MiB of array payload can be allocated. Object overhead and other heap usage reduce the memory available for the arrays.

### Experiment 3: String Concatenation vs StringBuilder

**Objective:** Compare the performance of repeated string concatenation and `StringBuilder`.

**Iterations:** 20,000

| Measurement | `String +=` | `StringBuilder` |
|---|---:|---:|
| Output length | 88,890 | 88,890 |
| Execution time | 277.134 ms | 1.6175 ms |

**Observation:** Both approaches produced strings of the same length. In this run, `StringBuilder` completed the operation much faster than repeated `String +=` concatenation.

**Conclusion:** Strings are immutable, so repeated concatenation can create many intermediate string objects. `StringBuilder` modifies its internal buffer and is generally more efficient when building strings repeatedly in a loop. Timings vary between runs and are not guaranteed performance benchmarks.

### Experiment 4: Garbage Collection Logs

**Objective:** Observe garbage collection activity during repeated memory allocation.

**Command:** `java -Xms16m -Xmx64m -Xlog:gc -cp target/classes org.example.GarbageCollectionLab`

**Results:**

| Observation | Actual result |
|---|---|
| Garbage collector | G1 |
| Young GC events | Observed repeatedly |
| Example before/after heap usage | `9M -> 8M` |
| Another example | `20M -> 0M` |
| Final status | All 10 allocation rounds completed |

**Observation:** The GC logs showed repeated young collection events, concurrent marking activity, and heap usage changes. For example, one event reduced the reported heap usage from 20 MiB to 0 MiB. This is consistent with temporary objects becoming eligible for collection after their references are no longer retained.

**Conclusion:** Garbage collection reclaims memory occupied by unreachable objects. Its timing and exact behavior are controlled by the JVM; enabling `-Xlog:gc` helps us observe collection activity rather than assume when it will happen.


### Experiment 5: Integer Cache and `==`

**Objective:** Observe how the Integer cache affects reference comparison.

**Results:**

| Comparison | Output |
|---|---|
| `127` using `==` | `true` |
| `128` using `==` | `false` |
| `128` using `equals()` | `true` |

**Observation:** The two `Integer` references holding `127` referred to the same cached object. The references holding `128` were different objects in this run, even though their values were equal.

**Conclusion:** The `==` operator compares object references, whereas `equals()` compares the integer values. Use `equals()` when comparing `Integer` values for equality.
