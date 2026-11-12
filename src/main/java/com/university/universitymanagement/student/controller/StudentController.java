
package com.university.universitymanagement.student.controller;

import com.university.universitymanagement.student.entity.Student;
import com.university.universitymanagement.student.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.university.universitymanagement.student.service.StudentTranscriptService;
import com.university.universitymanagement.dto.StudentTranscriptDto;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentTranscriptService transcriptService;
    public StudentController(
            StudentService studentService,
            StudentTranscriptService transcriptService) {
        this.studentService = studentService;
        this.transcriptService = transcriptService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(
            @Valid @RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {
        return ResponseEntity.ok(
                studentService.updateStudent(id, student)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/transcript")
    public ResponseEntity<StudentTranscriptDto> getStudentTranscript(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                transcriptService.getStudentTranscript(id)
        );
    }
}
