package org.example;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("student must not be null");
        }
        if (student.getId() <= 0) {
            throw new IllegalArgumentException("student ID must be greater than 0");
        }
        if (hasStudentWithId(student.getId())) {
            throw new IllegalArgumentException("A student with this ID already exists");
        }
        students.add(new Student(
                student.getId(),
                student.getName(),
                student.getAge()
        ));
    }

    public Student findStudentById(int id) {
        validateId(id);
        for (Student student : students) {
            if (student.getId() == id) {
                return new Student(student.getId(), student.getName(), student.getAge());
            }
        }
        return null;
    }

    public boolean updateStudent(int id, Student updatedStudent) {
        validateId(id);
        if (updatedStudent == null) {
            throw new IllegalArgumentException("updatedStudent must not be null");
        }
        for (Student student : students) {
            if (student.getId() == id) {
                student.setName(updatedStudent.getName());
                student.setAge(updatedStudent.getAge());
                return true;
            }
        }
        return false;
    }

    public boolean deleteStudentById(int id) {
        validateId(id);
        for (int index = 0; index < students.size(); index++) {
            if (students.get(index).getId() == id) {
                students.remove(index);
                return true;
            }
        }
        return false;
    }

    public List<Student> getAllStudents() {
        List<Student> studentCopies = new ArrayList<>();
        for (Student student : students) {
            studentCopies.add(new Student(student.getId(), student.getName(), student.getAge()));
        }
        return studentCopies;
    }

    private boolean hasStudentWithId(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("student ID must be greater than 0");
        }
    }
}
