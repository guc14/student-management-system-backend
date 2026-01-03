package com.guc.studentmanagement.repository;

import com.guc.studentmanagement.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    //  Retrieve all enrollments for a given student
    List<Enrollment> findByStudentId(Long studentId);

    // Retrieve all enrollments for a given course
    List<Enrollment> findByCourseId(Long courseId);

    // Check whether a student has already enrolled in a course(to prevent duplicate enrollment)
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    // Used for course withdrawal(if a drop-course endpoint is added in the future)
    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
}
