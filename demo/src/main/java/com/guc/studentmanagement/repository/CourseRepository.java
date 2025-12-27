package com.guc.studentmanagement.repository;

import com.guc.studentmanagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // 后面我们可以加：按 name 模糊搜索 + 分页
}
