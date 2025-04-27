package com.example.Graduation.Project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/ahmed")
    public String getName(){
        return "hello ahmed";
    }

}
