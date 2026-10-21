package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    @Test
    void addStudentRejectsInvalidId() {
        StudentService service = new StudentService();

        assertThrows(IllegalArgumentException.class,
                () -> new Student(0, "Arun", 20));
    }

    @Test
    void findStudentReturnsDefensiveCopy() {
        StudentService service = new StudentService();
        service.addStudent(new Student(1, "Arun", 20));

        Student found = service.findStudentById(1);
        found.setName("Changed");

        assertEquals("Arun", service.findStudentById(1).getName());
    }

    @Test
    void getAllStudentsReturnsDefensiveCopies() {
        StudentService service = new StudentService();
        service.addStudent(new Student(1, "Arun", 20));

        List<Student> students = service.getAllStudents();
        students.get(0).setName("Changed");

        assertEquals("Arun", service.findStudentById(1).getName());
    }

    @Test
    void updateStudentPreservesId() {
        StudentService service = new StudentService();
        service.addStudent(new Student(1, "Arun", 20));

        boolean updated = service.updateStudent(
                1, new Student(99, "Bala", 21));

        assertTrue(updated);
        assertEquals(1, service.findStudentById(1).getId());
        assertEquals("Bala", service.findStudentById(1).getName());
    }

    @Test
    void updatingMissingStudentReturnsFalse() {
        StudentService service = new StudentService();

        boolean updated = service.updateStudent(
                10, new Student(2, "Bala", 21));

        assertFalse(updated);
    }

    @Test
    void changingOriginalStudentDoesNotChangeStoredStudent() {
        StudentService service = new StudentService();
        Student student = new Student(1, "Arun", 20);

        service.addStudent(student);
        student.setName("Changed");

        assertEquals("Arun", service.findStudentById(1).getName());
    }
}