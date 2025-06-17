package com.example.Graduation.Project.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigInteger;

@Data
public class AddStatesRequest {
    @NotBlank(message = "Address must not be blank")
    @NotEmpty(message = "Address must not be empty")
    @NotNull(message = "Address must not be empty")
    private String address;

    @Positive(message = "Price must be a positive number")
    private double price;

    @Min(value = 1, message = "Rooms must be at least 1")
    private int rooms;

    @NotBlank(message = "Description must not be blank")
    @NotEmpty(message = "Description must not be empty")
    @NotNull(message = "Description must not be empty")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Image (Base64) is required")
    @NotEmpty(message = "Image must not be empty")
    @NotNull(message = "Image must not be empty")
    private String imageBase64;
}