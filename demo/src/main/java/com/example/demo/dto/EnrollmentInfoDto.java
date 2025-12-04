package com.example.demo.dto;

import java.time.LocalDateTime;

public class EnrollmentInfoDto {

    // 选课记录自身的 id
    private Long enrollmentId;

    // 学生信息
    private Long studentId;
    private String studentName;

    // 课程信息
    private Long courseId;
    private String courseName;

    // 选课时间
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
