package com.guc.studentmanagement.service;

import com.guc.studentmanagement.dto.CourseDto;
import com.guc.studentmanagement.dto.CreateCourseRequest;
import com.guc.studentmanagement.dto.EnrollmentInfoDto;
import com.guc.studentmanagement.dto.StudentDto;
import com.guc.studentmanagement.dto.UpdateCourseRequest;
import com.guc.studentmanagement.exception.CourseNotFoundException;
import com.guc.studentmanagement.exception.StudentNotFoundException;
import com.guc.studentmanagement.mapper.CourseMapper;
import com.guc.studentmanagement.entity.Course;
import com.guc.studentmanagement.entity.Enrollment;
import com.guc.studentmanagement.entity.Student;
import com.guc.studentmanagement.repository.CourseRepository;
import com.guc.studentmanagement.repository.EnrollmentRepository;
import com.guc.studentmanagement.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseService(CourseRepository courseRepository,
                         StudentRepository studentRepository,
                         EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // ================== Utility Methods ==================

    // Student -> StudentDto
    private StudentDto toStudentDto(Student student) {
        if (student == null) return null;
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        return dto;
    }

    // ================== Course CRUD  ==================

    //GET all courses
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return CourseMapper.toDtoList(courses);
    }

    // GET course by id
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + id));
        return CourseMapper.toDto(course);
    }

    // POST create new course
    public CourseDto addCourse(CreateCourseRequest request) {
        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setCredit(request.getCredit());

        Course saved = courseRepository.save(course);
        return CourseMapper.toDto(saved);
    }

    // PUT updateCourse
    public CourseDto updateCourse(Long id, UpdateCourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + id));

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setCredit(request.getCredit());

        Course saved = courseRepository.save(course);
        return CourseMapper.toDto(saved);
    }

    // DELETE deleteCourse
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + id));

        courseRepository.delete(course);
    }

    // ================== Course Enrollment Logic ==================

    // Student enrolls in a course
    public void enrollStudentToCourse(Long studentId, Long courseId) {

        // 1. Verify that the student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + studentId));

        // 2. Verify that the course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + courseId));

        // 3.  Prevent duplicate enrollment
        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (exists) {
            return;
        }

        // 4. Create enrollment record
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());

        enrollmentRepository.save(enrollment);
    }

    // Retrieve courses selected by a student
    public List<CourseDto> getCoursesByStudent(Long studentId) {

        // Verify that the student exists
        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + studentId));

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        return enrollments.stream()
                .map(Enrollment::getCourse)
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
    }

    // Retrieve students enrolled in a course (simple list version)
    public List<StudentDto> getStudentsByCourse(Long courseId) {

        //  Verify that the course exists
        courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + courseId));

        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);

        return enrollments.stream()
                .map(Enrollment::getStudent)
                .map(this::toStudentDto)
                .collect(Collectors.toList());
    }

    //  Retrieve detailed enrollment information for a student (course + enrollment time)
    public List<EnrollmentInfoDto> getEnrollmentInfosByStudent(Long studentId) {

        // Verify that the student exists
        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + studentId));

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        return enrollments.stream()
                .map(e -> new EnrollmentInfoDto(
                        e.getId(),                     // enrollmentId
                        e.getStudent().getId(),        // studentId
                        e.getStudent().getName(),      // studentName
                        e.getCourse().getId(),         // courseId
                        e.getCourse().getName(),       // courseName
                        e.getEnrolledAt()              // enrolledAt
                ))
                .collect(Collectors.toList());
    }

    // ================== ⭐ New: Search students by course with filters(advanced query with pagination)  ==================

    public Page<StudentDto> searchStudentsByCourse(Long courseId,
                                                   String keyword,
                                                   Integer minAge,
                                                   Integer maxAge,
                                                   Pageable pageable) {

        // First verify that the course exists; return 404 if not found
        courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + courseId));

        // 1) Retrieve all enrollment records for the given course
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);

        // 2) Map enrollments to students and remove duplicates
        List<Student> students = enrollments.stream()
                .map(Enrollment::getStudent)
                .distinct()
                .toList();

        // 3) Apply conditional filters using Stream
        Stream<Student> stream = students.stream();

        // keyword: fuzzy search by name (case-insensitive)
        if (keyword != null && !keyword.isBlank()) {
            String kw = keyword.trim().toLowerCase();
            stream = stream.filter(s ->
                    s.getName() != null && s.getName().toLowerCase().contains(kw)
            );
        }

        // Minimum age
        if (minAge != null) {
            stream = stream.filter(s ->
                    s.getAge() != null && s.getAge() >= minAge
            );
        }

        // Maximum age
        if (maxAge != null) {
            stream = stream.filter(s ->
                    s.getAge() != null && s.getAge() <= maxAge
            );
        }

        // 4) Sort results (by id in ascending order; can be changed to sort by name)
        List<Student> filtered = stream
                .sorted(Comparator.comparing(Student::getId))
                .toList();

        // 5) Perform manual pagination using PageImpl
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int fromIndex = pageNumber * pageSize;

        if (fromIndex >= filtered.size()) {
            // Out of range, return an empty page
            return new PageImpl<>(List.of(), pageable, filtered.size());
        }

        int toIndex = Math.min(fromIndex + pageSize, filtered.size());

        List<StudentDto> pageContent = filtered.subList(fromIndex, toIndex)
                .stream()
                .map(this::toStudentDto)
                .toList();

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }
}
