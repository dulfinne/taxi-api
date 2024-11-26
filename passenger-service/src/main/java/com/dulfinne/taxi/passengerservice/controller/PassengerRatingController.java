package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.service.PassengerRatingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerRatingController {

  private final PassengerRatingService passengerRatingService;

  @GetMapping("/{username}/ratings")
  public ResponseEntity<Page<PassengerRatingResponse>> getAllPassengerRatingsByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    Page<PassengerRatingResponse> ratingResponsePage =
        passengerRatingService.getPassengerRatings(username, offset, limit, sortField);
    return ResponseEntity.ok(ratingResponsePage);
  }

  @GetMapping("/ratings")
  public ResponseEntity<Page<PassengerRatingResponse>> getAllPassengerRatings(
      Principal principal,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    Page<PassengerRatingResponse> ratingResponsePage =
        passengerRatingService.getPassengerRatings(principal.getName(), offset, limit, sortField);
    return ResponseEntity.ok(ratingResponsePage);
  }

  // TODO: Remove endpoint after adding kafka in ride-service
  @PostMapping("/{username}/rate")
  public ResponseEntity<PassengerRatingResponse> savePassengerRating(
      @PathVariable String username,
      @RequestBody @Valid PassengerRatingRequest passengerRatingRequest) {

    PassengerRatingResponse ratingResponse =
        passengerRatingService.savePassengerRating(username, passengerRatingRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(ratingResponse);
  }
}
