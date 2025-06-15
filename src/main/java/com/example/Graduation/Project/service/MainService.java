package com.example.Graduation.Project.service;

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

@Service
@RequiredArgsConstructor
public class MainService {

    private final RestTemplate restTemplate;
    private final ContactsRepo contactsRepo;
    private final StatesRepo statesRepo;

    public ResponseEntity<PredictionResponse> predict(@Valid PredictRequest predictRequest) {
        String flaskUrl = "https://web-production-dff89.up.railway.app/predict";
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

    public ResponseEntity<String> addState(Authentication connectedUser, AddStatesRequest request) {
        User user=(User) connectedUser.getPrincipal();
        States state= States.builder()
                .user(user)
                .description(request.getDescription())
                .rooms(request.getRooms())
                .imageUrl(request.getImageUrl())
                .address(request.getAddress())
                .price(request.getPrice())
                .build();
        statesRepo.save(state);
        return  ResponseEntity.ok("Saved Successfully");
    }

    public ResponseEntity<SearchResponse> searchState(SearchRequest searchRequest) {
      States state=statesRepo.getState(searchRequest.getLocation()
              , searchRequest.getPrice() , searchRequest.getNumberOfRooms());
      return ResponseEntity.ok( SearchResponse.builder()
              .numberOfRooms(state.getRooms())
              .location(state.getAddress())
              .description(state.getDescription())
              .imageUrl(state.getImageUrl())
              .price(state.getPrice())
              .build());
    }
}
