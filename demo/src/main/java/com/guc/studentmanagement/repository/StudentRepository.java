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

    // Query by age range
    Page<Student> findByAgeBetween(int minAge, int maxAge, Pageable pageable);

    // Filter by name and age range , Fuzzy search by name (case-insensitive)
    Page<Student> findByNameContainingIgnoreCaseAndAgeBetween(
            String name,
            int minAge,
            int maxAge,
            Pageable pageable
    );
}
