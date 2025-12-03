package com.example.demo.repository;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // 防止重复选同一门课
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    // 查某个学生选了哪些课程
    @Query("select e.course from Enrollment e where e.student.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);
    // 查某门课有哪些学生
    @Query("select e.student from Enrollment e where e.course.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);
    // 查某个学生的所有 Enrollment 记录（包含课程和时间）
    List<Enrollment> findByStudentId(Long studentId);

}
