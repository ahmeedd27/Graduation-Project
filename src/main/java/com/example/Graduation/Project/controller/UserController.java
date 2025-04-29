package com.example.Graduation.Project.controller;

import com.example.Graduation.Project.dto.ChangePasswordRequest;
import com.example.Graduation.Project.dto.UserLogin;
import com.example.Graduation.Project.dto.UserRequest;
import com.example.Graduation.Project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "Endpoints related to user authentication and profile management")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Takes user details and registers them into the system")
    public ResponseEntity<String> register(
            @Valid @RequestBody UserRequest userRequest
    ) {
        return userService.register(userRequest);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates the user and returns a JWT token")
    public ResponseEntity<String> login(
            @Valid @RequestBody UserLogin userLogin
    ) {
        return userService.login(userLogin);
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change user password", description = "Allows logged-in users to change their password")
    public ResponseEntity<String> changePass(
            @Valid @RequestBody ChangePasswordRequest change,
            Authentication connectedUser
    ) {
        return userService.changePass(change, connectedUser);
    }


}
