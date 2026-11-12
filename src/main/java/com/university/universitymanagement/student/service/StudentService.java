
package com.university.universitymanagement.student.service;

import com.university.universitymanagement.student.entity.Student;
import com.university.universitymanagement.student.repository.StudentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(
            transactionManager = "studentTransactionManager",
            readOnly = true
    )
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(
            transactionManager = "studentTransactionManager",
            readOnly = true
    )
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student not found with ID: " + id
                ));
    }

    @Transactional(transactionManager = "studentTransactionManager")
    public Student createStudent(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student with this email already exists"
            );
        }

        return studentRepository.save(student);
    }

    @Transactional(transactionManager = "studentTransactionManager")
    public Student updateStudent(Long id, Student updatedStudent) {

        Student student = getStudentById(id);

        studentRepository.findByEmail(updatedStudent.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Email is already used by another student"
                    );
                });

        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setEmail(updatedStudent.getEmail());
        student.setMajor(updatedStudent.getMajor());
        student.setEnrollmentYear(updatedStudent.getEnrollmentYear());

        return studentRepository.save(student);
    }

    @Transactional(transactionManager = "studentTransactionManager")
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}
