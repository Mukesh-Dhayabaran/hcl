# Collection Performance Comparison

## Test Results

| Operation | ArrayList (ns) | LinkedList (ns) |
|---|---:|---:|
| Append 100,000 items | 7,259,200 | 5,643,800 |
| 1,000 middle inserts | 5,254,900 | 104,222,500 |
| 10,000 middle accesses | 433,700 | 1,090,237,500 |

## Observations

- **Append:** LinkedList was faster in this particular run.
- **Middle insertion:** ArrayList was substantially faster for the tested workload.
- **Indexed access:** ArrayList was substantially faster because it supports direct index access, whereas LinkedList must traverse nodes.

## Conclusion

ArrayList is generally suitable when indexed access is frequent. LinkedList can be useful for certain insertion and removal patterns when the required node is already available, but inserting at an index requires traversal. Actual performance depends on the workload, JVM, hardware, and execution conditions.

These measurements represent a single educational benchmark run and are not universal performance guarantees.
