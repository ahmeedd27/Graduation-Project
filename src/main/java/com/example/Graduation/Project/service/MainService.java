package com.example.Graduation.Project.service;

import com.example.Graduation.Project.dao.ContactsRepo;
import com.example.Graduation.Project.dto.ComplaintsRequest;
import com.example.Graduation.Project.dto.PredictRequest;
import com.example.Graduation.Project.dto.PredictionResponse;
import com.example.Graduation.Project.model.Contacts;
import com.example.Graduation.Project.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MainService {

    private final RestTemplate restTemplate;
    private final ContactsRepo contactsRepo;

    public ResponseEntity<PredictionResponse> predict(@Valid PredictRequest predictRequest) {
        String flaskUrl = "https://web-production-dd163.up.railway.app/predict";
         return ResponseEntity.ok( restTemplate.postForEntity(
            flaskUrl ,
            predictRequest ,
                  PredictionResponse.class
          ).getBody());
    }

    public ResponseEntity<String> makeComplaint(ComplaintsRequest complaintsRequest , Authentication connectedUser) {
        User user=(User) connectedUser.getPrincipal();
        Contacts contact=Contacts
                .builder()
                .content(complaintsRequest.getContent())
                .user(user)
                .build();
        contactsRepo.save(contact);
        return ResponseEntity
                .ok("Your Complaint has been saved successfully , we will review it and come back soon.");
    }
}
