package com.guc.studentmanagement.controller;

import com.guc.studentmanagement.dto.ApiResponse;
import com.guc.studentmanagement.dto.CreateStudentRequest;
import com.guc.studentmanagement.dto.StudentDto;
import com.guc.studentmanagement.dto.UpdateStudentRequest;
import com.guc.studentmanagement.service.StudentService;

// Swagger / OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.*;

import java.util.List;

//  Page、Pageable、PageableDefault
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
@Tag(
        name = "Student API",
        description = "Student management (list, search, pagination, CRUD)"
)
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET /students  —— return student(DTO）
    @Operation(
            summary = "Get all students",
            description = "Return all students without pagination."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned all students"
            )
    })
    @GetMapping
    public ApiResponse<List<StudentDto>> getAllStudents() {
        return ApiResponse.success(studentService.getAllStudents());
    }

    // GET /students/{id}
    @Operation(
            summary = "Get student by ID",
            description = "Find a student by its unique ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Student found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Student not found"
            )
    })
    @GetMapping("/{id}")
    public ApiResponse<StudentDto> getStudentById(
            @Parameter(description = "Student ID", example = "1")
            @PathVariable Long id
    ) {
        return ApiResponse.success(studentService.getStudentById(id));
    }

    // POST /students
    @Operation(
            summary = "Create a new student",
            description = "Create a new student using JSON request body."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Student created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            )
    })
    @PostMapping
    public ApiResponse<StudentDto> addStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Student creation request payload",
                    required = true
            )
            @Valid @RequestBody CreateStudentRequest request
    ) {
        return ApiResponse.success(studentService.addStudent(request));
    }

    // PUT /students/{id}
    @Operation(
            summary = "Update an existing student",
            description = "Update a student by ID with the provided JSON request body."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Student updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Student not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            )
    })
    @PutMapping("/{id}")
    public ApiResponse<StudentDto> updateStudent(
            @Parameter(description = "Student ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateStudentRequest request
    ) {
        return ApiResponse.success(studentService.updateStudent(id, request));
    }

    // DELETE /students/{id}
    @Operation(
            summary = "Delete a student",
            description = "Delete a student by its ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Student deleted successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Student not found"
            )
    })
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStudent(
            @Parameter(description = "Student ID", example = "1")
            @PathVariable Long id
    ) {
        studentService.deleteStudent(id);
        return ApiResponse.success(null);
    }

    // ✅ Comprehensive Query API with Pagination, Search, Age Range Filtering, and Sorting (Default Pagination Enabled)
    @Operation(
            summary = "Search students",
            description = "Search students by keyword and optional age range with pagination."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned search result"
            )
    })
    @GetMapping("/search")
    public ApiResponse<Page<StudentDto>> searchStudents(
            @Parameter(description = "Keyword in name or email", example = "John")
            @RequestParam(required = false) String keyword,

            @Parameter(description = "Minimum age filter", example = "18")
            @RequestParam(required = false) Integer minAge,

            @Parameter(description = "Maximum age filter", example = "30")
            @RequestParam(required = false) Integer maxAge,

            @Parameter(
                    description = "Pagination information (page, size, sort). " +
                            "Default: page=0, size=5, sort=id"
            )
            @PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable
    ) {
        Page<StudentDto> result = studentService.searchStudents(keyword, minAge, maxAge, pageable);
        return ApiResponse.success(result);
    }

    // GET /students/page —
    @Operation(
            summary = "Get students by page",
            description = "Return a pageable list of students with default page size = 5."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned paged students"
            )
    })
    @GetMapping("/page")
    public ApiResponse<Page<StudentDto>> getStudentsPage(
            @Parameter(
                    description = "Pagination information (page, size, sort). " +
                            "Default: page=0, size=5, sort=id"
            )
            @PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable
    ) {
        Page<StudentDto> pageResult = studentService.getStudentsPage(pageable);
        return ApiResponse.success(pageResult);
    }

}
