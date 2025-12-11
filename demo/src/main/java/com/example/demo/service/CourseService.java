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

    // 查询某门课有哪些学生（简单 List 版）
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

    // ================== ⭐ 新增：按课程 + 条件过滤学生（高级查询 + 分页） ==================

    public Page<StudentDto> searchStudentsByCourse(Long courseId,
                                                   String keyword,
                                                   Integer minAge,
                                                   Integer maxAge,
                                                   Pageable pageable) {

        // 先确认课程存在，不存在直接 404
        courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course not found with id = " + courseId));

        // 1) 找出这门课的所有选课记录
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);

        // 2) 把 Enrollment 映射成 Student，并去重
        List<Student> students = enrollments.stream()
                .map(Enrollment::getStudent)
                .distinct()
                .toList();

        // 3) 用 Stream 按条件过滤
        Stream<Student> stream = students.stream();

        // keyword：按 name 模糊搜索（忽略大小写）
        if (keyword != null && !keyword.isBlank()) {
            String kw = keyword.trim().toLowerCase();
            stream = stream.filter(s ->
                    s.getName() != null && s.getName().toLowerCase().contains(kw)
            );
        }

        // 最小年龄
        if (minAge != null) {
            stream = stream.filter(s ->
                    s.getAge() != null && s.getAge() >= minAge
            );
        }

        // 最大年龄
        if (maxAge != null) {
            stream = stream.filter(s ->
                    s.getAge() != null && s.getAge() <= maxAge
            );
        }

        // 4) 排序（按 id 升序；你也可以改成按 name）
        List<Student> filtered = stream
                .sorted(Comparator.comparing(Student::getId))
                .toList();

        // 5) 手动做分页（PageImpl）
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int fromIndex = pageNumber * pageSize;

        if (fromIndex >= filtered.size()) {
            // 超出范围，返回空页
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
