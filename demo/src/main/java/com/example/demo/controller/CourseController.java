package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ------------------- 课程 CRUD -------------------

    // GET /courses
    @GetMapping
    public ApiResponse<List<CourseDto>> getAllCourses() {
        return ApiResponse.success(courseService.getAllCourses());
    }

    // GET /courses/{id}
    @GetMapping("/{id}")
    public ApiResponse<CourseDto> getCourseById(@PathVariable Long id) {
        return ApiResponse.success(courseService.getCourseById(id));
    }

    // POST /courses
    @PostMapping
    public ApiResponse<CourseDto> addCourse(@RequestBody CreateCourseRequest request) {
        return ApiResponse.success(courseService.addCourse(request));
    }

    // PUT /courses/{id}
    @PutMapping("/{id}")
    public ApiResponse<CourseDto> updateCourse(
            @PathVariable Long id,
            @RequestBody UpdateCourseRequest request
    ) {
        return ApiResponse.success(courseService.updateCourse(id, request));
    }

    // DELETE /courses/{id}
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ApiResponse.success(null);
    }

    // ------------------- 选课功能 -------------------

    /**
     * 学生选课
     * POST /courses/{courseId}/students/{studentId}
     */
    @PostMapping("/{courseId}/students/{studentId}")
    public ApiResponse<Void> enrollStudentToCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId
    ) {
        courseService.enrollStudentToCourse(studentId, courseId);
        return ApiResponse.success(null);
    }

    /**
     * 查询学生选了哪些课程
     * GET /courses/by-student/{studentId}
     */
    @GetMapping("/by-student/{studentId}")
    public ApiResponse<List<CourseDto>> getCoursesByStudent(@PathVariable Long studentId) {
        return ApiResponse.success(courseService.getCoursesByStudent(studentId));
    }

    /**
     * 查询一门课程有哪些学生
     * GET /courses/{courseId}/students
     */
    @GetMapping("/{courseId}/students")
    public ApiResponse<List<StudentDto>> getStudentsByCourse(@PathVariable Long courseId) {
        return ApiResponse.success(courseService.getStudentsByCourse(courseId));
    }

    /**
     * 查询学生选课详情
     * GET /courses/enrollments/by-student/{studentId}
     */
    @GetMapping("/enrollments/by-student/{studentId}")
    public ApiResponse<List<EnrollmentInfoDto>> getEnrollmentInfosByStudent(
            @PathVariable Long studentId
    ) {
        return ApiResponse.success(courseService.getEnrollmentInfosByStudent(studentId));
    }
}
