# Day 10 – Memory Management Lab

## Experiment 1: Stack Overflow

I ran the program with different stack sizes to see how many recursive method calls it could handle before getting a `StackOverflowError`.

| Stack size | Recursion depth |
|---|---:|
| `-Xss256k` – Run 1 | 3,014 |
| `-Xss256k` – Run 2 | 2,395 |
| `-Xss1m` – Run 1 | 19,580 |

With `-Xss256k`, the program stopped after a few thousand calls. When I increased the stack size to `-Xss1m`, it handled more recursive calls before the error occurred. The results were also different between the two runs with the same stack size.

**What I learned:** Each method call uses some stack memory. If recursive calls continue without stopping, the available stack space eventually runs out and the JVM throws a `StackOverflowError`. Increasing the stack size allows more calls, but the exact depth can vary.

## Experiment 2: Heap Exhaustion

I ran the program with a maximum heap setting of 64 MiB. It kept creating 1 MiB byte arrays and storing them in an `ArrayList`.

**Command:** `java -Xmx64m -cp target/classes org.example.HeapExhaustionLab`

| Item | Result |
|---|---|
| Maximum heap setting | 64 MiB |
| Size of each byte array | 1 MiB |
| Approximate array data allocated | 31 MiB |
| Final result | `OutOfMemoryError` |

The program stopped allocating after approximately 31 MiB of array data. The arrays were still referenced by the list, so the garbage collector could not remove them while those references remained.

**What I learned:** The heap stores objects created by a Java program. Setting `-Xmx64m` limits the heap size, but the arrays cannot use the entire heap because other objects and memory overhead also take up space.

## Experiment 3: String Concatenation and StringBuilder

I used both `String +=` and `StringBuilder` to build a string over 20,000 iterations and compared their execution times.

| Measurement | `String +=` | `StringBuilder` |
|---|---:|---:|
| Output length | 88,890 | 88,890 |
| Execution time | 277.134 ms | 1.6175 ms |

Both methods produced strings of the same length. However, `StringBuilder` finished much faster in my test.

**What I learned:** Java strings are immutable, which means their contents cannot be changed after creation. Repeated concatenation can create many intermediate strings. `StringBuilder` is more suitable for repeatedly building a string inside a loop because it can append to its internal buffer. The exact timing may change between runs.

## Experiment 4: Garbage Collection Logs

I ran the allocation program with GC logging enabled to see what the JVM did when the program created temporary byte arrays.

**Command:** `java -Xms16m -Xmx64m -Xlog:gc -cp target/classes org.example.GarbageCollectionLab`

| Item | Result |
|---|---|
| Garbage collector | G1 |
| GC activity | Multiple young GC events |
| Example heap usage change | `9M -> 8M` |
| Another heap usage change | `20M -> 0M` |
| Allocation rounds completed | 10 |

The output showed several GC events while the program was running. Heap usage changed after some of these events, and the program completed all ten allocation rounds.

**What I learned:** Garbage collection helps the JVM reclaim memory occupied by objects that are no longer reachable. The JVM decides when collection is needed. The `-Xlog:gc` option lets me see GC activity and heap usage changes instead of guessing when collection happens.

## Experiment 5: Integer Cache and ==

I created two `Integer` variables with the value `127` and another two with the value `128`. Then I compared them using `==` and `equals()`.

| Comparison | Result |
|---|---|
| Two `Integer` references holding `127`, using `==` | `true` |
| Two `Integer` references holding `128`, using `==` | `false` |
| Two `Integer` values holding `128`, using `equals()` | `true` |

The comparison using `==` returned `true` for `127`, but `false` for `128`. Using `equals()` for `128` returned `true`.

**What I learned:** The `Integer` cache commonly covers values from `-128` to `127`. The `==` operator checks whether two references point to the same object, while `equals()` checks whether their values are equal. Therefore, I should use `equals()` when comparing `Integer` values rather than relying on `==`.

## Overall Learning

These experiments helped me understand the difference between stack and heap memory, what happens when memory limits are reached, why `StringBuilder` is useful in loops, how to observe garbage collection, and why reference comparison can give unexpected results with wrapper objects.