package com.example.demo.service;

import com.example.demo.dto.CourseDto;
import com.example.demo.dto.EnrollmentInfoDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.EnrollmentMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // 学生选课（enroll 学生到某门课）
    public void enrollStudentToCourse(Long studentId, Long courseId) {

        // 1. 先查出 Student & Course，不存在就报错
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found, id=" + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found, id=" + courseId));

        // 2. 防止重复选同一门课
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        // 3. 创建 Enrollment 记录（中间表插入一行）
        Enrollment enrollment = new Enrollment(student, course);

        // 4. 保存到数据库
        enrollmentRepository.save(enrollment);
    }

    // 查询某个学生选了哪些课程
    public List<CourseDto> getCoursesByStudent(Long studentId) {
        List<Course> courses = enrollmentRepository.findCoursesByStudentId(studentId);
        return CourseMapper.toDtoList(courses); // 你之前已经有 CourseMapper 了
    }
    // 查询某门课程有哪些学生
    public List<StudentDto> getStudentsByCourse(Long courseId) {
        List<Student> students = enrollmentRepository.findStudentsByCourseId(courseId);
        return StudentMapper.toDtoList(students); // 你之前已经有 StudentMapper 了
    }
    // 查询某个学生的所有选课详细记录
    public List<EnrollmentInfoDto> getEnrollmentInfosByStudent(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return EnrollmentMapper.toInfoDtoList(enrollments);
    }
}
