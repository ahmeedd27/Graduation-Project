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

    public void revokeAllTokens(User user){
        List<Token> tokens=tokenRepo.findAllTokensByUserId(user.getId());
        tokens.forEach(t -> t.setRevoked(true));
        tokenRepo.saveAll(tokens);
    }

    public ResponseEntity<String> register(UserRequest userRequest) {
        if(userRepo.findByEmail(userRequest.getEmail()).isPresent()){
            throw new BadCredentialsException("email exist");
        }
        User user=User.builder()
                .name(userRequest.getName())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .role(Role.USER)
                .build();
        String jwt=jwtService.generateToken(user);
        Token token=Token.builder()
                .token(jwt)
                .user(user)
                .isRevoked(false)
                .build();
        userRepo.save(user);
        tokenRepo.save(token);
        return ResponseEntity.ok(jwt);
    }

    public ResponseEntity<String> login(UserLogin userLogin){
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
            throw new BadCredentialsException("Invalid email or password provided");
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> changePass(ChangePasswordRequest change, Authentication connectedUser) {
        User user=(User) connectedUser.getPrincipal();
        if(!passwordEncoder.matches( change.getOldPassword() , user.getPassword())){
            throw new BadCredentialsException("wrong password");
        }
        user.setPassword(passwordEncoder.encode(change.getNewPassword()));
        userRepo.save(user);
        return ResponseEntity.ok("changed successfully");
    }
}
