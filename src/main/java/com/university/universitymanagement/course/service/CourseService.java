
package com.university.universitymanagement.course.service;

import com.university.universitymanagement.course.entity.Course;
import com.university.universitymanagement.course.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(
            transactionManager = "courseTransactionManager",
            readOnly = true
    )
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(
            transactionManager = "courseTransactionManager",
            readOnly = true
    )
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found with ID: " + id
                ));
    }

    @Transactional(transactionManager = "courseTransactionManager")
    public Course createCourse(Course course) {
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Course with this code already exists"
            );
        }

        return courseRepository.save(course);
    }

    @Transactional(transactionManager = "courseTransactionManager")
    public Course updateCourse(Long id, Course updatedCourse) {
        Course course = getCourseById(id);

        courseRepository.findByCourseCode(updatedCourse.getCourseCode())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Course code is already used"
                    );
                });

        course.setCourseName(updatedCourse.getCourseName());
        course.setCourseCode(updatedCourse.getCourseCode());
        course.setCredits(updatedCourse.getCredits());
        course.setInstructor(updatedCourse.getInstructor());

        return courseRepository.save(course);
    }

    @Transactional(transactionManager = "courseTransactionManager")
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }
}
