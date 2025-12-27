package com.guc.studentmanagement.dto;

import jakarta.validation.constraints.*;

public class CourseDto {

    private Long id;

    @NotBlank(message = "Course name cannot be blank")
    @Size(min = 2, max = 100, message = "Course name must be between 2 and 100 characters")
    private String name;

    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @NotNull(message = "Credit is required")
    @Min(value = 1, message = "Credit must be at least 1")
    @Max(value = 20, message = "Credit must be at most 20")
    private Integer credit;

    public CourseDto() {
    }

    public CourseDto(Long id, String name, String description, Integer credit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.credit = credit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
}
