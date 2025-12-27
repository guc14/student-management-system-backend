package com.guc.studentmanagement.service;

import com.guc.studentmanagement.dto.StudentProfileDto;
import com.guc.studentmanagement.mapper.StudentProfileMapper;
import com.guc.studentmanagement.entity.Student;
import com.guc.studentmanagement.entity.StudentProfile;
import com.guc.studentmanagement.repository.StudentProfileRepository;
import com.guc.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileService {

    private final StudentRepository studentRepository;
    private final StudentProfileRepository profileRepository;
    private final StudentProfileMapper profileMapper;

    public StudentProfileService(StudentRepository studentRepository,
                                 StudentProfileRepository profileRepository,
                                 StudentProfileMapper profileMapper) {
        this.studentRepository = studentRepository;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    // Create profile
    public StudentProfileDto createProfile(Long studentId,
                                           String phone,
                                           String address,
                                           String emergencyContact) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found with id = " + studentId));

        StudentProfile profile = profileMapper.toEntity(phone, address, emergencyContact, student);

        StudentProfile saved = profileRepository.save(profile);
        student.setProfile(saved);

        return profileMapper.toDto(saved);
    }

    // Get profile
    public StudentProfileDto getProfile(Long studentId) {
        StudentProfile profile = profileRepository.findByStudentId(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found for student id = " + studentId));

        return profileMapper.toDto(profile);
    }

    // Update profile
    public StudentProfileDto updateProfile(Long studentId,
                                           String phone,
                                           String address,
                                           String emergencyContact) {

        StudentProfile profile = profileRepository.findByStudentId(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found for student id = " + studentId));

        profile.setPhone(phone);
        profile.setAddress(address);
        profile.setEmergencyContact(emergencyContact);

        StudentProfile saved = profileRepository.save(profile);
        return profileMapper.toDto(saved);
    }

    // Delete profile
    public void deleteProfile(Long studentId) {
        // 1. 找到 profile
        StudentProfile profile = profileRepository.findByStudentId(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found for student id = " + studentId));

        // 2. 把 student 上的关联也清掉（更干净）
        Student student = profile.getStudent();
        if (student != null) {
            student.setProfile(null);
        }

        // 3. 删除 profile
        profileRepository.delete(profile);
    }

}
