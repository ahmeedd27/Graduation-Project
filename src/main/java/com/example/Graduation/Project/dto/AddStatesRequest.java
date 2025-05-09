package com.example.Graduation.Project.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class AddStatesRequest {
    private String address;
    private double price;
    private int rooms;
    private String description;
    private String imageUrl;
}
