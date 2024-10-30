package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.mapper.PassengerRatingMapper;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import com.dulfinne.taxi.passengerservice.service.PassengerRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerRatingController {

    private final PassengerRatingService passengerRatingService;
    private final PassengerRatingMapper passengerRatingMapper;

    @GetMapping("/{passengerId}/ratings")
    public ResponseEntity<List<PassengerRatingResponse>> getAllPassengerRatings(@PathVariable long passengerId) {
        List<PassengerRating> passengerRatingList = passengerRatingService.getPassengerRatings(passengerId);
        return ResponseEntity.ok(passengerRatingMapper.toPassengerRatingResponseList(passengerRatingList));
    }

    @PostMapping("/{passengerId}/rate")
    public ResponseEntity<PassengerRatingResponse> savePassengerRating(@PathVariable long passengerId,
                                                                       @RequestBody @Valid PassengerRatingRequest passengerRatingRequest) {
        PassengerRating passengerRating = passengerRatingMapper.toPassengerRating(passengerRatingRequest);
        passengerRating = passengerRatingService.savePassengerRating(passengerId, passengerRating);
        return ResponseEntity.ok(passengerRatingMapper.toPassengerRatingResponse(passengerRating));
    }
}
