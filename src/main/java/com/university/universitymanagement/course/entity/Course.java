
package com.university.universitymanagement.course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(
        name = "courses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "course_code")
        }
)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotBlank
    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer credits;

    @NotBlank
    @Column(nullable = false)
    private String instructor;

    public Course() {
    }

    public Course(String courseName, String courseCode,
                  Integer credits, String instructor) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
        this.instructor = instructor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
