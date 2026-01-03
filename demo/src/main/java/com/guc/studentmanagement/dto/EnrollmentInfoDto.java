package com.guc.studentmanagement.dto;

import java.time.LocalDateTime;

public class EnrollmentInfoDto {

    // ID of the enrollment record
    private Long enrollmentId;

    // Student information
    private Long studentId;
    private String studentName;

    // Course information
    private Long courseId;
    private String courseName;

    // Enrollment timestamp
    private LocalDateTime enrolledAt;

    public EnrollmentInfoDto() {
    }

    public EnrollmentInfoDto(Long enrollmentId,
                             Long studentId,
                             String studentName,
                             Long courseId,
                             String courseName,
                             LocalDateTime enrolledAt) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseName = courseName;
        this.enrolledAt = enrolledAt;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }
}
