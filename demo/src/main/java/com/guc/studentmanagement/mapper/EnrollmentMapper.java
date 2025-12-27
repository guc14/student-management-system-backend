package com.guc.studentmanagement.mapper;

import com.guc.studentmanagement.dto.EnrollmentInfoDto;
import com.guc.studentmanagement.entity.Enrollment;

import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentMapper {

    public static EnrollmentInfoDto toInfoDto(Enrollment enrollment) {
        if (enrollment == null) return null;

        EnrollmentInfoDto dto = new EnrollmentInfoDto();
        dto.setEnrollmentId(enrollment.getId());
        dto.setStudentId(enrollment.getStudent().getId());
        dto.setStudentName(enrollment.getStudent().getName());
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setCourseName(enrollment.getCourse().getName());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        return dto;
    }

    public static List<EnrollmentInfoDto> toInfoDtoList(List<Enrollment> enrollments) {
        return enrollments.stream()
                .map(EnrollmentMapper::toInfoDto)
                .collect(Collectors.toList());
    }
}
