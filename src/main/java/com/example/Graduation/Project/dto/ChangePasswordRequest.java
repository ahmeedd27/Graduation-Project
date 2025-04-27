package com.example.Graduation.Project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "password required")
    @NotEmpty(message = "password required")
    @Size(min = 8 , message = "password must be greater than 8 characters")
    private String oldPassword;
    @NotBlank(message = "password required")
    @NotEmpty(message = "password required")
    @Size(min = 8 , message = "password must be greater than 8 characters")
    private String newPassword;
}
