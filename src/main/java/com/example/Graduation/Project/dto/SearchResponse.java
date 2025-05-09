package com.example.Graduation.Project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResponse {
    private String location;
    private double price;
    private String description;
    private int numberOfRooms;
    private String imageUrl;
}
