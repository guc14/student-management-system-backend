package com.example.demo.mapper;

import com.example.demo.dto.StudentProfileDto;
import com.example.demo.model.Student;
import com.example.demo.model.StudentProfile;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileMapper {

    // entity -> dto
    public StudentProfileDto toDto(StudentProfile profile) {
        if (profile == null) {
            return null;
        }

        StudentProfileDto dto = new StudentProfileDto();
        dto.setId(profile.getId());
        dto.setPhone(profile.getPhone());
        dto.setAddress(profile.getAddress());
        dto.setEmergencyContact(profile.getEmergencyContact());

        if (profile.getStudent() != null) {
            dto.setStudentId(profile.getStudent().getId());
            dto.setStudentName(profile.getStudent().getName());
        }

        return dto;
    }

    // dto/input -> entity   ✅ 这一段就是刚刚缺少的 toEntity
    public StudentProfile toEntity(String phone,
                                   String address,
                                   String emergencyContact,
                                   Student student) {
        StudentProfile profile = new StudentProfile();
        profile.setPhone(phone);
        profile.setAddress(address);
        profile.setEmergencyContact(emergencyContact);
        profile.setStudent(student);
        return profile;
    }
}
