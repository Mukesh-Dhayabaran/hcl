package com.hcl.employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class EmployeeDirectory {

    // Comparable: employees are sorted by ID by default.
    record Employee(int id, String name, String department, double salary)
            implements Comparable<Employee> {

        @Override
        public int compareTo(Employee other) {
            return Integer.compare(this.id, other.id);
        }
    }

    // Demonstrates the equals/hashCode contract.
    static class EmployeeKey {
        private final int id;

        EmployeeKey(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EmployeeKey other)) {
                return false;
            }
            return id == other.id;
        }

        // Correct implementation: equal IDs produce equal hash codes.
        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    static class EmployeeKeyWithoutHashCode {
        private final int id;

        EmployeeKeyWithoutHashCode(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EmployeeKeyWithoutHashCode other)) {
                return false;
            }
            return id == other.id;
        }
    }

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee(104, "Arun", "IT", 65000),
                new Employee(101, "Priya", "HR", 82000),
                new Employee(106, "Kumar", "IT", 75000),
                new Employee(102, "Divya", "Finance", 90000),
                new Employee(105, "Ravi", "HR", 70000),
                new Employee(103, "Meena", "IT", 95000),
                new Employee(107, "Sanjay", "Finance", 60000)
        );

        // 1. HashMap: fast employee lookup by ID.
        Map<Integer, Employee> employeeMap = new HashMap<>();

        for (Employee employee : employees) {
            employeeMap.put(employee.id(), employee);
        }

        System.out.println("1. Employee lookup:");
        System.out.println(employeeMap.get(103));

        // 2. Map.merge(): count employees in each department.
        Map<String, Integer> departmentCounts = new HashMap<>();

        for (Employee employee : employees) {
            departmentCounts.merge(employee.department(), 1, Integer::sum);
        }

        System.out.println("\n2. Department frequency:");
        System.out.println(departmentCounts);

        // 3. TreeMap: departments appear in sorted key order.
        Map<String, List<Employee>> departmentReport = new TreeMap<>();

        for (Employee employee : employees) {
            departmentReport
                    .computeIfAbsent(employee.department(),
                            department -> new ArrayList<>())
                    .add(employee);
        }

        System.out.println("\n3. Department report:");
        departmentReport.forEach((department, members) ->
                System.out.println(department + ": " + members));

        // 4. Comparable: natural ordering by employee ID.
        List<Employee> sortedEmployees = new ArrayList<>(employees);
        sortedEmployees.sort(null);

        System.out.println("\n4. Employees sorted by ID:");
        sortedEmployees.forEach(System.out::println);

        // 5. Comparator chaining: highest salary first, then name.
        Comparator<Employee> salaryComparator =
                Comparator.comparingDouble(Employee::salary)
                        .reversed()
                        .thenComparing(Employee::name);

        System.out.println("\n5. Top five salaries:");
        employees.stream()
                .sorted(salaryComparator)
                .limit(5)
                .forEach(System.out::println);

        // 6. equals/hashCode: equal objects behave correctly in a HashSet.
        Set<EmployeeKeyWithoutHashCode> brokenKeys = new HashSet<>();
        brokenKeys.add(new EmployeeKeyWithoutHashCode(101));
        brokenKeys.add(new EmployeeKeyWithoutHashCode(101));

        System.out.println("\n6. Missing hashCode demonstration:");
        System.out.println("Equal keys stored without hashCode: "
                + brokenKeys.size());

// Correct implementation.
        Set<EmployeeKey> fixedKeys = new HashSet<>();
        fixedKeys.add(new EmployeeKey(101));
        fixedKeys.add(new EmployeeKey(101));

        System.out.println("Equal keys stored with hashCode: "
                + fixedKeys.size());
    }
}
