package com.example.demo.service;

import com.example.demo.dto.CreateStudentRequest;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.UpdateStudentRequest;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

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

    // GET 所有学生
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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
}
