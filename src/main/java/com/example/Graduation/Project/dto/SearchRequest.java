package com.example.Graduation.Project.dto;

import lombok.Data;

@Data
public class SearchRequest {

    private String location;
    private double price;
    private int numberOfRooms;

}
