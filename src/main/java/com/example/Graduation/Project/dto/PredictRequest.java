package com.example.Graduation.Project.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PredictRequest {

    @Min(100)
    @Max(20000)
    private int livingArea;

    @Min(0)
    @Max(20)
    private int bedrooms;

    @Min(0)
    @Max(20)
    private int bathrooms;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private double longitude;

    @Min(1800)
    @Max(2025)
    private int yearBuilt;

    @NotBlank
    @Pattern(regexp = "Single_Family|Townhouse|Condo", message = "homeType must be one of: Single_Family, Townhouse, Condo")
    private String homeType;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private double propertyTaxRate;

    @DecimalMin("0.0")
    private double annualHomeownersInsurance;


}
