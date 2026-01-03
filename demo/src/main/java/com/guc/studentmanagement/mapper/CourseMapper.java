package com.guc.studentmanagement.mapper;

import com.guc.studentmanagement.dto.CourseDto;
import com.guc.studentmanagement.entity.Course;

import java.util.List;
import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseDto toDto(Course course) {
        if (course == null) return null;
        return new CourseDto(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getCredit()
        );
    }

    public static List<CourseDto> toDtoList(List<Course> courses) {
        return courses.stream()
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
    }
}
