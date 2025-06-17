package com.example.Graduation.Project.service;

import com.example.Graduation.Project.dao.TokenRepo;
import com.example.Graduation.Project.dao.UserRepo;
import com.example.Graduation.Project.dto.ChangePasswordRequest;
import com.example.Graduation.Project.dto.UserLogin;
import com.example.Graduation.Project.dto.UserRequest;
import com.example.Graduation.Project.model.Role;
import com.example.Graduation.Project.model.Token;
import com.example.Graduation.Project.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final AuthenticationManager authenticationManager;

    public void revokeAllTokens(User user) {
        List<Token> tokens;
        try {
            tokens = tokenRepo.findAllTokensByUserId(user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to revoke tokens", e);
        }
        tokens.forEach(t -> t.setRevoked(true));
        try {
            tokenRepo.saveAll(tokens);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save revoked tokens", e);
        }
    }

    public ResponseEntity<String> register(UserRequest userRequest) {
        if (userRepo.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new BadCredentialsException("Email already exists");
        }

        try {
            User user = User.builder()
                    .name(userRequest.getName())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .email(userRequest.getEmail())
                    .role(Role.USER)
                    .build();

            String jwt = jwtService.generateToken(user);
            Token token = Token.builder()
                    .token(jwt)
                    .user(user)
                    .isRevoked(false)
                    .build();

            userRepo.save(user);
            tokenRepo.save(token);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Registration failed", e);
        }
    }

    public ResponseEntity<String> login(UserLogin userLogin) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword())
            );
            User user = (User) auth.getPrincipal();
            revokeAllTokens(user);

            Map<String, Object> claims = new HashMap<>();
            claims.put("fullName", user.getName());

            String jwt = jwtService.generateToken(claims, user);
            Token token = Token.builder()
                    .isRevoked(false)
                    .user(user)
                    .token(jwt)
                    .build();
            tokenRepo.save(token);
            return ResponseEntity.ok(jwt);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        } catch (Exception e) {
            throw new RuntimeException("Login failed", e);
        }
    }

    public ResponseEntity<String> changePass(ChangePasswordRequest change, Authentication connectedUser) {
        try {
            User user = (User) connectedUser.getPrincipal();
            if (!passwordEncoder.matches(change.getOldPassword(), user.getPassword())) {
                throw new BadCredentialsException("Wrong old password");
            }
            user.setPassword(passwordEncoder.encode(change.getNewPassword()));
            userRepo.save(user);
            return ResponseEntity.ok("Password changed successfully");
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Password change failed", e);
        }
    }
}
