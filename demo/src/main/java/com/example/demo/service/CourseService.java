package com.example.demo.service;

import com.example.demo.dto.CourseDto;
import com.example.demo.dto.CreateCourseRequest;
import com.example.demo.dto.EnrollmentInfoDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.UpdateCourseRequest;
import com.example.demo.exception.CourseNotFoundException;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // ================== 工具方法 ==================

    // Student -> StudentDto（在本 Service 内部用，不依赖 StudentService）
    private StudentDto toStudentDto(Student student) {
        if (student == null) return null;
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        return dto;
    }

    // ================== 课程 CRUD ==================

    // GET 所有课程
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return CourseMapper.toDtoList(courses);
    }

    // GET 按 id 查课程
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + id));
        return CourseMapper.toDto(course);
    }

    // POST 新建课程
    public CourseDto addCourse(CreateCourseRequest request) {
        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setCredit(request.getCredit());

        Course saved = courseRepository.save(course);
        return CourseMapper.toDto(saved);
    }

    // PUT 更新课程
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

    // DELETE 删除课程
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + id));

        courseRepository.delete(course);
    }

    // ================== 选课相关逻辑 ==================

    // 学生选课
    public void enrollStudentToCourse(Long studentId, Long courseId) {

        // 1. 确认学生存在
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + studentId));

        // 2. 确认课程存在
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + courseId));

        // 3. 防止重复选课
        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (exists) {
            // 这里选择“静默忽略”，不抛异常；你也可以自己改成抛异常
            return;
        }

        // 4. 创建选课记录
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());

        enrollmentRepository.save(enrollment);
    }

    // 查询某个学生选了哪些课程
    public List<CourseDto> getCoursesByStudent(Long studentId) {

        // 确认学生存在
        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id = " + studentId));

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        return enrollments.stream()
                .map(Enrollment::getCourse)
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
    }

    // 查询某门课有哪些学生
    public List<StudentDto> getStudentsByCourse(Long courseId) {

        // 确认课程存在
        courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + courseId));

        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);

        return enrollments.stream()
                .map(Enrollment::getStudent)
                .map(this::toStudentDto)
                .collect(Collectors.toList());
    }

    // 查询某个学生的选课详细信息（课程 + 选课时间）
    public List<EnrollmentInfoDto> getEnrollmentInfosByStudent(Long studentId) {

        // 确认学生存在
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

}
