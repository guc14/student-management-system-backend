package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments") // 对应数据库里的 enrollments 表
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 多个 Enrollment 对应一个 Student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // 多个 Enrollment 对应一个 Course
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // 选课时间
    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;

    // JPA 要求的无参构造
    public Enrollment() {
    }

    // 业务用：new Enrollment(student, course)
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrolledAt = LocalDateTime.now();
    }

    // ===== Getter & Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }
}
