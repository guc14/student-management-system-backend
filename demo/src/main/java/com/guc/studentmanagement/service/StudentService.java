package com.guc.studentmanagement.service;

import com.guc.studentmanagement.dto.CreateStudentRequest;
import com.guc.studentmanagement.dto.StudentDto;
import com.guc.studentmanagement.dto.UpdateStudentRequest;
import com.guc.studentmanagement.exception.StudentNotFoundException;
import com.guc.studentmanagement.entity.Student;
import com.guc.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

// ✅ 分页相关 import
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // 构造函数注入 Repository
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ---------- 内部工具方法：entity -> dto ----------
    private StudentDto toDto(Student student) {
        if (student == null) return null;

        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        // 如果以后 StudentDto 里有更多字段，在这里继续 set 就好
        return dto;
    }

    // ---------- 对应 Controller 的方法 ----------

    // GET 所有学生（未分页）
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ GET 分页查询所有学生 —— 对应 /students/page
    public Page<StudentDto> getStudentsPage(Pageable pageable) {
        Page<Student> page = studentRepository.findAll(pageable);
        return page.map(this::toDto);
    }

    // GET 按 id 查询学生
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + id));
        return toDto(student);
    }

    // POST 新增学生  —— 对应 Controller 里的 addStudent(...)
    public StudentDto addStudent(CreateStudentRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setAge(request.getAge());

        Student saved = studentRepository.save(student);
        return toDto(saved);
    }

    // PUT 更新学生 —— 对应 Controller 里的 updateStudent(...)
    public StudentDto updateStudent(Long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + id));

        student.setName(request.getName());
        student.setAge(request.getAge());

        Student saved = studentRepository.save(student);
        return toDto(saved);
    }

    // DELETE 删除学生
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + id));

        studentRepository.delete(student);
    }

    // ✅ 综合查询（名字 keyword + 年龄区间 + 分页 + 排序）
    public Page<StudentDto> searchStudents(
            String keyword,      // 可选：名字关键字（模糊搜索）
            Integer minAge,      // 可选：最小年龄
            Integer maxAge,      // 可选：最大年龄
            Pageable pageable    // 分页 + 排序
    ) {
        // 1. 先处理 keyword，去掉首尾空格
        String trimmedKeyword = (keyword == null ? null : keyword.trim());

        boolean hasKeyword = (trimmedKeyword != null && !trimmedKeyword.isEmpty());
        boolean hasAgeRange = (minAge != null && maxAge != null);

        // ✅ 小优化：如果传进来的 minAge > maxAge，自动帮用户交换
        if (hasAgeRange && minAge > maxAge) {
            int tmp = minAge;
            minAge = maxAge;
            maxAge = tmp;
        }

        // 2. 根据不同的组合，调用 Repository 里对应的方法
        Page<Student> page;

        if (hasKeyword && hasAgeRange) {
            // 名字 + 年龄区间
            page = studentRepository.findByNameContainingIgnoreCaseAndAgeBetween(
                    trimmedKeyword,
                    minAge,
                    maxAge,
                    pageable
            );
        } else if (hasKeyword) {
            // 只有名字模糊搜索
            page = studentRepository.findByNameContainingIgnoreCase(
                    trimmedKeyword,
                    pageable
            );
        } else if (hasAgeRange) {
            // 只有年龄区间
            page = studentRepository.findByAgeBetween(
                    minAge,
                    maxAge,
                    pageable
            );
        } else {
            // 都没传：查全部 + 分页
            page = studentRepository.findAll(pageable);
        }

        // 3. 把 Page<Student> 映射成 Page<StudentDto>
        return page.map(this::toDto);
    }
}
