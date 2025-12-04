package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // 查某学生的所有选课
    List<Enrollment> findByStudentId(Long studentId);

    // 查某课程下的所有学生
    List<Enrollment> findByCourseId(Long courseId);

    // 判断是否已经选过（防止重复选课）
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    // 退课时用（如果以后要做退课接口）
    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
}
