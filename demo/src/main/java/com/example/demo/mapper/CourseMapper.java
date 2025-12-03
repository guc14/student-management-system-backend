package com.example.demo.mapper;

import com.example.demo.dto.CourseDto;
import com.example.demo.model.Course;

import java.util.List;
import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseDto toDto(Course course) {
        if (course == null) return null;
        return new CourseDto(
                course.getId(),
                course.getName(),
                course.getDescription()
        );
    }

    public static List<CourseDto> toDtoList(List<Course> courses) {
        return courses.stream()
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
    }
}
