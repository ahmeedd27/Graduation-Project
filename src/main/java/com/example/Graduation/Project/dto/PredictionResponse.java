package com.example.Graduation.Project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PredictionResponse {
    @JsonProperty("predicted_price")//👈 بيقوله لو شفت predicted_price في JSON، حطها هنا
    private double predictedPrice;
}


/*
* ✅ فايدة @JsonProperty:
تربط اسم الفيلد في JSON باسم مختلف في Java.

بتشتغل في serialization (من Java إلى JSON) و deserialization (من JSON إلى Java).

بتخلي كودك Java-style (camelCase) لكن يشتغل عادي مع أي API JSON-style (snake_case).
{
  "user_name": "mostafa"
}
Java:
java
Copy
Edit
public class User {

    @JsonProperty("user_name")
    private String userName;

    // getters & setters
}

*/