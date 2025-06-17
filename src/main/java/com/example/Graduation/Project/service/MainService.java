package com.example.Graduation.Project.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Graduation.Project.dao.ContactsRepo;
import com.example.Graduation.Project.dao.StatesRepo;
import com.example.Graduation.Project.dto.*;
import com.example.Graduation.Project.model.Contacts;
import com.example.Graduation.Project.model.States;
import com.example.Graduation.Project.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainService {

    private final RestTemplate restTemplate;
    private final ContactsRepo contactsRepo;
    private final StatesRepo statesRepo;
    private final Cloudinary cloudinary;

    public ResponseEntity<PredictionResponse> predict(@Valid PredictRequest predictRequest) {
        String flaskUrl = "https://web-production-dff89.up.railway.app/predict";
        try {
            return ResponseEntity.ok(
                    restTemplate.postForEntity(flaskUrl, predictRequest, PredictionResponse.class).getBody()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to get prediction from model", e);
        }
    }

    public ResponseEntity<String> makeComplaint(ComplaintsRequest complaintsRequest, Authentication connectedUser) {
        try {
            User user = (User) connectedUser.getPrincipal();
            Contacts contact = Contacts.builder()
                    .content(complaintsRequest.getContent())
                    .user(user)
                    .build();
            contactsRepo.save(contact);
            return ResponseEntity.ok("Your Complaint has been saved successfully, we will review it soon.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to save complaint", e);
        }
    }
    public ResponseEntity<String> addState(Authentication connectedUser, AddStatesRequest request) {
        User user = (User) connectedUser.getPrincipal();
        Map<String, Object> options = ObjectUtils.asMap(
                "resource_type", "image",
                "timestamp", System.currentTimeMillis() / 1000
        );
        String base64Data;
        String url="";
        try {
            base64Data = request.getImageBase64().split(",")[1];
            byte[] fileData = Base64.getDecoder().decode(base64Data);
            Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, options);
            url = uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Cloudinary", e);
        } catch (Exception e) {
            throw new RuntimeException("Invalid base64 or upload error", e);
        }

        try {
            States state = States.builder()
                    .user(user)
                    .description(request.getDescription())
                    .rooms(request.getRooms())
                    .imageUrl(url)
                    .address(request.getAddress())
                    .price(request.getPrice())
                    .build();
            statesRepo.save(state);
            return ResponseEntity.ok("Saved Successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to save state", e);
        }
    }

    public ResponseEntity<SearchResponse> searchState(SearchRequest searchRequest) {
        try {
            States state = statesRepo.getState(searchRequest.getLocation(),
                    searchRequest.getPrice(), searchRequest.getNumberOfRooms());

            return ResponseEntity.ok(SearchResponse.builder()
                    .numberOfRooms(state.getRooms())
                    .location(state.getAddress())
                    .description(state.getDescription())
                    .imageUrl(state.getImageUrl())
                    .price(state.getPrice())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to find matching state", e);
        }
    }
}
