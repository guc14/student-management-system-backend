package com.guc.studentmanagement.controller;

import com.guc.studentmanagement.dto.ApiResponse;
import com.guc.studentmanagement.dto.StudentProfileDto;
import com.guc.studentmanagement.service.StudentProfileService;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/students")
@Tag(
        name = "Student Profile API",
        description = "Manage one-to-one profile information of students"
)
public class StudentProfileController {

    private final StudentProfileService profileService;

    public StudentProfileController(StudentProfileService profileService) {
        this.profileService = profileService;
    }

    // POST /students/{id}/profile —— create profile
    @Operation(
            summary = "Create student profile",
            description = "Create a new profile for a student using form-data parameters."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Profile created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Student not found"
            )
    })
    @PostMapping("/{id}/profile")
    public ApiResponse<StudentProfileDto> createProfile(
            @Parameter(description = "Student ID", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Phone number", example = "647-888-1234")
            @RequestParam String phone,

            @Parameter(description = "Address", example = "123 Main St, Toronto")
            @RequestParam String address,

            @Parameter(description = "Emergency contact (optional)", example = "Mom: 416-777-8888")
            @RequestParam(required = false) String emergencyContact
    ) {
        return ApiResponse.success(
                profileService.createProfile(id, phone, address, emergencyContact)
        );
    }

    // GET /students/{id}/profile
    @Operation(
            summary = "Get student profile",
            description = "Retrieve the profile information of a specific student."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Profile found successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Profile or student not found"
            )
    })
    @GetMapping("/{id}/profile")
    public ApiResponse<StudentProfileDto> getProfile(
            @Parameter(description = "Student ID", example = "1")
            @PathVariable Long id
    ) {
        return ApiResponse.success(profileService.getProfile(id));
    }

    // PUT /students/{id}/profile
    @Operation(
            summary = "Update student profile",
            description = "Update an existing student's profile using form-data parameters."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Profile updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Profile or student not found"
            )
    })
    @PutMapping("/{id}/profile")
    public ApiResponse<StudentProfileDto> updateProfile(
            @Parameter(description = "Student ID", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Phone number", example = "647-888-1234")
            @RequestParam String phone,

            @Parameter(description = "Address", example = "123 Main St, Toronto")
            @RequestParam String address,

            @Parameter(description = "Emergency contact (optional)", example = "Dad: 416-111-2222")
            @RequestParam(required = false) String emergencyContact
    ) {
        return ApiResponse.success(
                profileService.updateProfile(id, phone, address, emergencyContact)
        );
    }

    // DELETE /students/{id}/profile
    @Operation(
            summary = "Delete student profile",
            description = "Delete the profile associated with the given student ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Profile deleted successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Profile or student not found"
            )
    })
    @DeleteMapping("/{id}/profile")
    public ApiResponse<Void> deleteProfile(
            @Parameter(description = "Student ID", example = "1")
            @PathVariable Long id
    ) {
        profileService.deleteProfile(id);
        return ApiResponse.success(null);
    }
}
