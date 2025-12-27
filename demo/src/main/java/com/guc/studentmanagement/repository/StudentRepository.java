package com.guc.studentmanagement.repository;

import com.guc.studentmanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends
        JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {   // ⭐ 新加这个

    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // 按年龄区间查询
    Page<Student> findByAgeBetween(int minAge, int maxAge, Pageable pageable);

    // 名字 + 年龄区间一起过滤（新加）, 按名字模糊搜索（忽略大小写）
    Page<Student> findByNameContainingIgnoreCaseAndAgeBetween(
            String name,
            int minAge,
            int maxAge,
            Pageable pageable
    );
}
