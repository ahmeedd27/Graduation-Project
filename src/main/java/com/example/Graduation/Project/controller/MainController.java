package com.example.Graduation.Project.controller;

import com.example.Graduation.Project.dto.*;
import com.example.Graduation.Project.service.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Main", description = "Main Functionalities")
public class MainController {

    private final MainService mainService;

    @PostMapping("/predict")
    @Operation(summary = "Predict house price", description = "Uses a trained model to predict the price of a house based on input features")
    public ResponseEntity<PredictionResponse> predictPrice(
            @Valid @RequestBody PredictRequest predictRequest
    ) {
        return mainService.predict(predictRequest);
    }

    @PostMapping("/contact")
    @Operation(summary = "Submit a complaint", description = "Allows authenticated users to send complaints to admins")
    public ResponseEntity<String> makeComplaint(
            @RequestBody ComplaintsRequest complaintsRequest,
            Authentication connectedUser
    ) {
        return mainService.makeComplaint(complaintsRequest, connectedUser);
    }

    @PostMapping("/addState")
    @Operation(summary = "Adding State", description = "Allows authenticated users to add States they want to sale")
    public ResponseEntity<String> addState(
            Authentication connectedUser ,
            @RequestBody AddStatesRequest request
            ){
        return mainService.addState(connectedUser,request);
    }

    @PostMapping("/search")
    @Operation(summary = "Search State", description = "Allows all users to search on any state")
    public ResponseEntity<SearchResponse> searchForState(
            @RequestBody SearchRequest searchRequest
    ){
        return mainService.searchState(searchRequest);
    }
}
