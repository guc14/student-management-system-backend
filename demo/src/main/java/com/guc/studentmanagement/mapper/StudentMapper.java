package com.guc.studentmanagement.mapper;

import com.guc.studentmanagement.dto.StudentDto;
import com.guc.studentmanagement.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    // Entity -> DTO
    public static StudentDto toDto(Student student) {
        if (student == null) {
            return null;
        }

        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        return dto;
    }

    // List<Entity> -> List<DTO>
    public static List<StudentDto> toDtoList(List<Student> students) {
        return students.stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
        // Java 21 也可以写成：.toList()
    }
}
