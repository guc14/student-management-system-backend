package com.example.demo.spec;

import com.example.demo.model.Student;
import com.example.demo.model.Enrollment;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecifications {

    /**
     * 构建一个 Specification：
     * - 必须选了指定 courseId
     * - 可选：keyword（匹配 name）
     * - 可选：minAge / maxAge
     */
    public static Specification<Student> byCourseAndFilters(
            Long courseId,
            String keyword,
            Integer minAge,
            Integer maxAge
    ) {
        return (root, query, cb) -> {

            // ⚠️ 这里的 "enrollments" 要和你 Student 实体里的字段名一致
            // 比如 Student 里有：
            // @OneToMany(mappedBy = "student")
            // private Set<Enrollment> enrollments;
            Join<Student, Enrollment> enrollmentJoin = root.join("enrollments");

            // 先筛选：这名学生的 enrollment 对应的 course.id = 指定 courseId
            var predicate = cb.equal(enrollmentJoin.get("course").get("id"), courseId);

            // keyword 模糊匹配 name
            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.trim().toLowerCase() + "%";
                var nameLike = cb.like(cb.lower(root.get("name")), pattern);
                predicate = cb.and(predicate, nameLike);
            }

            // 最小年龄
            if (minAge != null) {
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(root.get("age"), minAge));
            }

            // 最大年龄
            if (maxAge != null) {
                predicate = cb.and(predicate,
                        cb.lessThanOrEqualTo(root.get("age"), maxAge));
            }

            // 去重，防止重复学生
            query.distinct(true);

            return predicate;
        };
    }
}
