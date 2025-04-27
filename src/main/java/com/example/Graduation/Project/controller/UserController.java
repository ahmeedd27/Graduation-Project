package com.example.Graduation.Project.controller;

import com.example.Graduation.Project.dto.ChangePasswordRequest;
import com.example.Graduation.Project.dto.UserLogin;
import com.example.Graduation.Project.dto.UserRequest;
import com.example.Graduation.Project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody UserRequest userRequest
    ){
        return userService.register(userRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody UserLogin userLogin
    ){
        return userService.login(userLogin);
    }

    @PostMapping("change-password")
    public ResponseEntity<String> changePass(
            @Valid @RequestBody ChangePasswordRequest change ,
            Authentication connectedUser
            ){
        return userService.changePass(change , connectedUser);
    }

    @GetMapping("/test")
    public String test( ){
        return "hello from the secured one";
    }

}
