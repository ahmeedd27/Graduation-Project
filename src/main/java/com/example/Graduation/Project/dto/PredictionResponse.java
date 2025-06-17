package com.example.Graduation.Project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PredictionResponse {
    @JsonProperty("predicted_price")
    private double predictedPrice;
}


