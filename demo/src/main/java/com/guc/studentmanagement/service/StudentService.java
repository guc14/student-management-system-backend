package com.guc.studentmanagement.service;

import com.guc.studentmanagement.dto.CreateStudentRequest;
import com.guc.studentmanagement.dto.StudentDto;
import com.guc.studentmanagement.dto.UpdateStudentRequest;
import com.guc.studentmanagement.exception.StudentNotFoundException;
import com.guc.studentmanagement.entity.Student;
import com.guc.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

// ✅  Pagination-related imports
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor-based dependency injection for the repository
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ----------  Internal utility method: entity -> DTO ----------
    private StudentDto toDto(Student student) {
        if (student == null) return null;

        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        return dto;
    }

    // ---------- Methods corresponding to Controller endpoints ----------

    //  GET all students (without pagination)
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅  GET paginated list of all students — corresponds to /students/page
    public Page<StudentDto> getStudentsPage(Pageable pageable) {
        Page<Student> page = studentRepository.findAll(pageable);
        return page.map(this::toDto);
    }

    // GET student by id
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + id));
        return toDto(student);
    }

    // POST create a new student — corresponds to addStudent(...) in the Controller
    public StudentDto addStudent(CreateStudentRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setAge(request.getAge());

        Student saved = studentRepository.save(student);
        return toDto(saved);
    }

    // PUT update an existing student — corresponds to updateStudent(...) in the Controller
    public StudentDto updateStudent(Long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + id));

        student.setName(request.getName());
        student.setAge(request.getAge());

        Student saved = studentRepository.save(student);
        return toDto(saved);
    }

    // DELETE student
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + id));

        studentRepository.delete(student);
    }

    // ✅ Comprehensive search (name keyword + age range + pagination + sorting)
    public Page<StudentDto> searchStudents(
            String keyword,
            Integer minAge,
            Integer maxAge,
            Pageable pageable
    ) {
        // 1. Preprocess keyword by trimming leading and trailing spaces
        String trimmedKeyword = (keyword == null ? null : keyword.trim());

        boolean hasKeyword = (trimmedKeyword != null && !trimmedKeyword.isEmpty());
        boolean hasAgeRange = (minAge != null && maxAge != null);

        // ✅ Small optimization: if minAge > maxAge, automatically swap the values
        if (hasAgeRange && minAge > maxAge) {
            int tmp = minAge;
            minAge = maxAge;
            maxAge = tmp;
        }

        // 2.  Call the corresponding repository method based on different combinations
        Page<Student> page;

        if (hasKeyword && hasAgeRange) {
            // Name keyword + age range
            page = studentRepository.findByNameContainingIgnoreCaseAndAgeBetween(
                    trimmedKeyword,
                    minAge,
                    maxAge,
                    pageable
            );
        } else if (hasKeyword) {
            // Name keyword only (fuzzy search)
            page = studentRepository.findByNameContainingIgnoreCase(
                    trimmedKeyword,
                    pageable
            );
        } else if (hasAgeRange) {
            //  Age range only
            page = studentRepository.findByAgeBetween(
                    minAge,
                    maxAge,
                    pageable
            );
        } else {
            //  No filters provided: retrieve all students with pagination
            page = studentRepository.findAll(pageable);
        }

        // 3. Map Page<Student> to Page<StudentDto>
        return page.map(this::toDto);
    }
}
