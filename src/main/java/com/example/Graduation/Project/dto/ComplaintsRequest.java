package com.example.Graduation.Project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ComplaintsRequest {
    @NotBlank(message = "Content must not be blank")
    @Size(min = 10, message = "Complaint content must be at least 10 characters long")
    private String content;
}
