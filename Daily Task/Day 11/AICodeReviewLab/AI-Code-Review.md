# Day 11 — AI-Generated Code Review

## Objective

Generate Java classes using GitHub Copilot, review the generated code, identify genuine issues, and document the fixes.

## 1. Prompt comparison

| Aspect | Vague prompt | Specific prompt |
|---|---|---|
| Prompt | Create a Student class in Java. | A detailed prompt specifying fields, constructors, accessors, validation, exceptions, and naming conventions. |
| Result | Generated a basic Student POJO with no validation. | Generated a separate StudentSpecific class with validation in its setters and parameterized constructor. |
| Observation | The prompt left implementation decisions to the AI. | Explicit requirements produced more predictable behavior. |
| Remaining concern | Invalid field values were accepted. | The no-argument constructor left the object in an invalid state. |

## 2. StudentService review

The following findings are based on the original AI-generated `Student.java` and `StudentService.java`, before fixes.

| No. | Issue | Evidence | Planned fix |
|---|---|---|---|
| 1 | Invalid student IDs can be accepted. | The Student constructor and `setId()` assign the ID without validation. | Validate that IDs are positive. |
| 2 | `findStudentById()` exposes the stored object. | It returns the matching object directly. | Return a defensive copy or use a controlled mutation policy. |
| 3 | `getAllStudents()` makes only a shallow copy. | A new list is returned, but its elements reference the stored Student objects. | Return copies of the students as well as the list. |
| 4 | `updateStudent()` can change a student's ID. | It replaces the stored object with `updatedStudent`. | Preserve the existing ID during updates. |
| 5 | Duplicate-ID checking happens before target lookup. | The duplicate check runs before the method confirms that the target ID exists. | Check the target first and return `false` if it is missing. |

## 3. Verification

Automated tests were added using JUnit 5 to verify the corrected behavior.

| Test | Expected result | Actual result |
|---|---|---|
| Reject invalid student ID | `IllegalArgumentException` is thrown. | Passed |
| Find student returns a defensive copy | Changing the returned student does not affect stored data. | Passed |
| Get-all returns defensive copies | Changing a returned student does not affect stored data. | Passed |
| Update preserves student ID | The original ID remains unchanged after updating name and age. | Passed |
| Update a missing student | The method returns `false`. | Passed |
| Adding a student stores a copy | Changing the original object does not affect stored data. | Passed |

### Maven test result

Command: `mvn clean test`

- Tests run: 6
- Failures: 0
- Errors: 0
- Skipped: 0
- Build status: `BUILD SUCCESS`

## 4. Outcome

The AI-generated code was reviewed, and the identified issues were addressed.

- Added validation for positive student IDs and valid student field values.
- Updated `findStudentById()` to return a defensive copy.
- Updated `getAllStudents()` to return a new list containing copies of students.
- Updated `updateStudent()` to preserve the existing ID, return `false` for a missing student, and validate the target ID.
- Updated `addStudent()` to store a copy rather than retaining the caller's mutable object.
- Added six automated tests to verify the corrected behavior.

All six tests passed during the Maven clean test run.

The implementation uses an in-memory `ArrayList`, so student records are not persisted after the application terminates. The no-argument constructor also uses default values for a valid initial object rather than representing a student with user-provided details.
