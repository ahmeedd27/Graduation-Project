package com.example.Graduation.Project.config;

import com.example.Graduation.Project.dao.TokenRepo;
import com.example.Graduation.Project.model.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final TokenRepo tokenRepo;

    @Override
    public void logout(
            HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication) {
        String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }
        jwt=authHeader.substring(7);
        Token t = tokenRepo.findTokenByName(jwt)
                .orElseThrow(() -> new NoSuchElementException("Not Found"));
        if (t != null) {
            t.setRevoked(true);
            tokenRepo.save(t);
        }
    }
}
