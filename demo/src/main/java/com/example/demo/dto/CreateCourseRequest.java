package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCourseRequest {

    @NotBlank(message = "Course name cannot be blank")
    @Size(max = 100, message = "Course name length must be <= 100 characters")
    private String name;

    @Size(max = 500, message = "Description length must be <= 500 characters")
    private String description;

    @Min(value = 0, message = "Credit must be >= 0")
    private Integer credit;  // 可选，不传就是 null

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
