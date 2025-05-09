package com.example.Graduation.Project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class States {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private double price;
    private int rooms;
    private String description;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
