
package com.university.universitymanagement.grade.service;

import com.university.universitymanagement.course.service.CourseService;
import com.university.universitymanagement.grade.entity.Grade;
import com.university.universitymanagement.grade.repository.GradeRepository;
import com.university.universitymanagement.student.service.StudentService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public GradeService(
            GradeRepository gradeRepository,
            StudentService studentService,
            CourseService courseService) {
        this.gradeRepository = gradeRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Transactional(transactionManager = "gradeTransactionManager")
    public Grade createGrade(Grade grade) {

        // Check whether the student exists in student_db
        studentService.getStudentById(grade.getStudentId());

        // Check whether the course exists in course_db
        courseService.getCourseById(grade.getCourseId());

        // Set grading date if it was not provided
        if (grade.getGradedAt() == null) {
            grade.setGradedAt(LocalDateTime.now());
        }

        // Calculate letter grade based on score
        grade.setLetterGrade(calculateLetterGrade(grade.getScore()));

        return gradeRepository.save(grade);
    }

    @Transactional(
            transactionManager = "gradeTransactionManager",
            readOnly = true
    )
    public List<Grade> getGradesByStudentId(Long studentId) {

        // Verify that the student exists
        studentService.getStudentById(studentId);

        return gradeRepository.findByStudentId(studentId);
    }

    private String calculateLetterGrade(Integer score) {

        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}
