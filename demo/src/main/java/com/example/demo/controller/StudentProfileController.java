package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.StudentProfileDto;
import com.example.demo.service.StudentProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentProfileController {

    private final StudentProfileService profileService;

    public StudentProfileController(StudentProfileService profileService) {
        this.profileService = profileService;
    }

    // POST /students/{id}/profile  —— 创建 profile
    @PostMapping("/{id}/profile")
    public ApiResponse<StudentProfileDto> createProfile(
            @PathVariable Long id,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam(required = false) String emergencyContact
    ) {
        return ApiResponse.success(
                profileService.createProfile(id, phone, address, emergencyContact)
        );
    }

    // GET /students/{id}/profile  —— 查询 profile
    @GetMapping("/{id}/profile")
    public ApiResponse<StudentProfileDto> getProfile(@PathVariable Long id) {
        return ApiResponse.success(profileService.getProfile(id));
    }

    // PUT /students/{id}/profile  —— 更新 profile
    @PutMapping("/{id}/profile")
    public ApiResponse<StudentProfileDto> updateProfile(
            @PathVariable Long id,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam(required = false) String emergencyContact
    ) {
        return ApiResponse.success(
                profileService.updateProfile(id, phone, address, emergencyContact)
        );
    }

    // DELETE /students/{id}/profile  —— 删除 profile
    @DeleteMapping("/{id}/profile")
    public ApiResponse<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ApiResponse.success(null);
    }
}
