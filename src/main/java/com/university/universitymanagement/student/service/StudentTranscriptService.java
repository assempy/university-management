
package com.university.universitymanagement.student.service;

import com.university.universitymanagement.course.entity.Course;
import com.university.universitymanagement.course.service.CourseService;
import com.university.universitymanagement.dto.StudentTranscriptDto;
import com.university.universitymanagement.grade.entity.Grade;
import com.university.universitymanagement.grade.service.GradeService;
import com.university.universitymanagement.student.entity.Student;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentTranscriptService {

    private final StudentService studentService;
    private final GradeService gradeService;
    private final CourseService courseService;

    public StudentTranscriptService(
            StudentService studentService,
            GradeService gradeService,
            CourseService courseService) {

        this.studentService = studentService;
        this.gradeService = gradeService;
        this.courseService = courseService;
    }

    public StudentTranscriptDto getStudentTranscript(Long studentId) {

        // Get student information from student_db
        Student student = studentService.getStudentById(studentId);

        // Get student's grades from grade_db
        List<Grade> grades = gradeService.getGradesByStudentId(studentId);

        // Combine grades with course information from course_db
        List<StudentTranscriptDto.TranscriptEntry> entries =
                grades.stream()
                        .map(grade -> {
                            Course course = courseService.getCourseById(
                                    grade.getCourseId()
                            );

                            return new StudentTranscriptDto.TranscriptEntry(
                                    course.getId(),
                                    course.getCourseName(),
                                    course.getCourseCode(),
                                    course.getCredits(),
                                    grade.getScore(),
                                    grade.getLetterGrade()
                            );
                        })
                        .toList();

        // Calculate average score
        double averageScore = grades.stream()
                .mapToInt(Grade::getScore)
                .average()
                .orElse(0.0);

        // Return combined transcript
        return new StudentTranscriptDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getMajor(),
                entries,
                Math.round(averageScore * 100.0) / 100.0
        );
    }
}
