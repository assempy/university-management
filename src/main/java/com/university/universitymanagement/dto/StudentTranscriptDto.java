
package com.university.universitymanagement.dto;

import java.util.List;

public class StudentTranscriptDto {

    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String major;

    private List<TranscriptEntry> grades;

    private Double averageScore;

    public StudentTranscriptDto() {
    }

    public StudentTranscriptDto(
            Long studentId,
            String firstName,
            String lastName,
            String email,
            String major,
            List<TranscriptEntry> grades,
            Double averageScore) {

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.major = major;
        this.grades = grades;
        this.averageScore = averageScore;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<TranscriptEntry> getGrades() {
        return grades;
    }

    public void setGrades(List<TranscriptEntry> grades) {
        this.grades = grades;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public static class TranscriptEntry {

        private Long courseId;
        private String courseName;
        private String courseCode;
        private Integer credits;
        private Integer score;
        private String letterGrade;

        public TranscriptEntry() {
        }

        public TranscriptEntry(
                Long courseId,
                String courseName,
                String courseCode,
                Integer credits,
                Integer score,
                String letterGrade) {

            this.courseId = courseId;
            this.courseName = courseName;
            this.courseCode = courseCode;
            this.credits = credits;
            this.score = score;
            this.letterGrade = letterGrade;
        }

        public Long getCourseId() {
            return courseId;
        }

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
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

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public String getLetterGrade() {
            return letterGrade;
        }

        public void setLetterGrade(String letterGrade) {
            this.letterGrade = letterGrade;
        }
    }
}
