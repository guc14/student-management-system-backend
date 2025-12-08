package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Swagger 注解
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
// ⚠️ 不要 import Swagger 的 ApiResponse，避免和你的 ApiResponse DTO 冲突
// import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/courses")
@Tag(
        name = "Course API",
        description = "Course management and enrollment operations"
)
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ------------------- 课程 CRUD -------------------

    @Operation(
            summary = "Get all courses",
            description = "Return all courses without pagination."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned course list"
            )
    })
    @GetMapping
    public ApiResponse<List<CourseDto>> getAllCourses() {
        return ApiResponse.success(courseService.getAllCourses());
    }

    @Operation(
            summary = "Get course by ID",
            description = "Find a course using its unique ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Course found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Course not found"
            )
    })
    @GetMapping("/{id}")
    public ApiResponse<CourseDto> getCourseById(
            @Parameter(description = "Course ID", example = "1")
            @PathVariable Long id
    ) {
        return ApiResponse.success(courseService.getCourseById(id));
    }

    @Operation(
            summary = "Create a new course",
            description = "Create a new course using JSON request body."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Course created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            )
    })
    @PostMapping
    public ApiResponse<CourseDto> addCourse(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Course creation request payload",
                    required = true
            )
            @RequestBody CreateCourseRequest request
    ) {
        return ApiResponse.success(courseService.addCourse(request));
    }

    @Operation(
            summary = "Update an existing course",
            description = "Update a course by its ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Course updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Course not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            )
    })
    @PutMapping("/{id}")
    public ApiResponse<CourseDto> updateCourse(
            @Parameter(description = "Course ID", example = "1")
            @PathVariable Long id,
            @RequestBody UpdateCourseRequest request
    ) {
        return ApiResponse.success(courseService.updateCourse(id, request));
    }

    @Operation(
            summary = "Delete a course",
            description = "Delete a course by its ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Course deleted successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Course not found"
            )
    })
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCourse(
            @Parameter(description = "Course ID", example = "1")
            @PathVariable Long id
    ) {
        courseService.deleteCourse(id);
        return ApiResponse.success(null);
    }

    // ------------------- 选课功能 -------------------

    @Operation(
            summary = "Enroll a student into a course",
            description = "Bind a student to a course using their IDs."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Student enrolled successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Student or course not found"
            )
    })
    @PostMapping("/{courseId}/students/{studentId}")
    public ApiResponse<Void> enrollStudentToCourse(
            @Parameter(description = "Course ID", example = "1")
            @PathVariable Long courseId,
            @Parameter(description = "Student ID", example = "2")
            @PathVariable Long studentId
    ) {
        courseService.enrollStudentToCourse(studentId, courseId);
        return ApiResponse.success(null);
    }

    @Operation(
            summary = "Get all courses selected by a student",
            description = "Return all courses that a student has enrolled in."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned student course list"
            )
    })
    @GetMapping("/by-student/{studentId}")
    public ApiResponse<List<CourseDto>> getCoursesByStudent(
            @Parameter(description = "Student ID", example = "2")
            @PathVariable Long studentId
    ) {
        return ApiResponse.success(courseService.getCoursesByStudent(studentId));
    }

    @Operation(
            summary = "Get all students enrolled in a course",
            description = "Return all students who selected the given course."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned student list"
            )
    })
    @GetMapping("/{courseId}/students")
    public ApiResponse<List<StudentDto>> getStudentsByCourse(
            @Parameter(description = "Course ID", example = "1")
            @PathVariable Long courseId
    ) {
        return ApiResponse.success(courseService.getStudentsByCourse(courseId));
    }

    @Operation(
            summary = "Get enrollment details of a student",
            description = "Return enrollment info of a student (course + extra details)."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned enrollment info"
            )
    })
    @GetMapping("/enrollments/by-student/{studentId}")
    public ApiResponse<List<EnrollmentInfoDto>> getEnrollmentInfosByStudent(
            @Parameter(description = "Student ID", example = "2")
            @PathVariable Long studentId
    ) {
        return ApiResponse.success(courseService.getEnrollmentInfosByStudent(studentId));
    }
}
