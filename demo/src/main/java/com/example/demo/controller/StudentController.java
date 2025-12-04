package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CreateStudentRequest;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.UpdateStudentRequest;
import com.example.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 为 Page、Pageable、PageableDefault
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET /students  —— 返回所有学生（用 DTO）
    @GetMapping
    public ApiResponse<List<StudentDto>> getAllStudents() {
        return ApiResponse.success(studentService.getAllStudents());
    }

    // GET /students/{id}  —— 按 id 查询
    @GetMapping("/{id}")
    public ApiResponse<StudentDto> getStudentById(@PathVariable Long id) {
        return ApiResponse.success(studentService.getStudentById(id));
    }

    // POST /students  —— 新建学生
    @PostMapping
    public ApiResponse<StudentDto> addStudent(@RequestBody CreateStudentRequest request) {
        return ApiResponse.success(studentService.addStudent(request));
    }

    // PUT /students/{id}  —— 更新学生
    @PutMapping("/{id}")
    public ApiResponse<StudentDto> updateStudent(
            @PathVariable Long id,
            @RequestBody UpdateStudentRequest request
    ) {
        return ApiResponse.success(studentService.updateStudent(id, request));
    }

    // DELETE /students/{id}  —— 删除学生
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ApiResponse.success(null);
    }

    // ✅ 分页 + 搜索 + 年龄区间 + 排序 综合查询接口（带默认分页）
    @GetMapping("/search")
    public ApiResponse<Page<StudentDto>> searchStudents(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable
    ) {
        Page<StudentDto> result = studentService.searchStudents(keyword, minAge, maxAge, pageable);
        return ApiResponse.success(result);
    }
}
