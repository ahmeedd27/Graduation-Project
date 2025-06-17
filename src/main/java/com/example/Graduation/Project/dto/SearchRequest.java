package com.example.Graduation.Project.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SearchRequest {

    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    @NotBlank(message = "location must not be blank")
    @NotEmpty(message = "location must not be empty")
    @NotNull(message = "location must not be empty")
    private String location;

    @DecimalMin(value = "100.0", message = "Minimum price must be at least 100")
    private double price;

    @Min(value = 1, message = "Number of rooms must be at least 1")
    @Max(value = 20, message = "Number of rooms cannot exceed 20")
    private int numberOfRooms;

}
