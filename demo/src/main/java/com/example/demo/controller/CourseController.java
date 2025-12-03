package com.example.demo.controller;

import com.example.demo.dto.CourseDto;
import com.example.demo.dto.EnrollmentInfoDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * 学生选课
     * POST /courses/{courseId}/students/{studentId}
     *
     * 例子：
     * POST http://localhost:8080/courses/1/students/2
     * 表示：id=2 的学生选 id=1 的课程
     */
    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Void> enrollStudentToCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        courseService.enrollStudentToCourse(studentId, courseId);
        // 201 Created，没有返回 body
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 查询某个学生选了哪些课程
     * GET /courses/by-student/{studentId}
     *
     * 例子：
     * GET http://localhost:8080/courses/by-student/2
     */
    @GetMapping("/by-student/{studentId}")
    public List<CourseDto> getCoursesByStudent(@PathVariable Long studentId) {
        return courseService.getCoursesByStudent(studentId);
    }
    /**
     * 查询某门课程有哪些学生
     * GET /courses/{courseId}/students
     *
     * 例子：
     * GET http://localhost:8080/courses/2/students
     */
    @GetMapping("/{courseId}/students")
    public List<StudentDto> getStudentsByCourse(@PathVariable Long courseId) {
        return courseService.getStudentsByCourse(courseId);
    }
    /**
     * 查询某个学生的选课详细记录（包含课程名和选课时间）
     * GET /courses/enrollments/by-student/{studentId}
     */
    @GetMapping("/enrollments/by-student/{studentId}")
    public List<EnrollmentInfoDto> getEnrollmentInfosByStudent(@PathVariable Long studentId) {
        return courseService.getEnrollmentInfosByStudent(studentId);
    }
}
