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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerRatingController {

  private final PassengerRatingService passengerRatingService;

  // For ADMIN
  @GetMapping("/ratings/{username}/admin")
  public ResponseEntity<Page<PassengerRatingResponse>> getAllPassengerRatingsByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    Page<PassengerRatingResponse> ratingResponsePage =
        passengerRatingService.getPassengerRatings(username, offset, limit, sortField);
    return ResponseEntity.ok(ratingResponsePage);
  }

  // For DRIVER
  // TODO: Later will get username from token
  @GetMapping("/ratings/{username}")
  public ResponseEntity<Page<PassengerRatingResponse>> getAllPassengerRatings(
          @PathVariable String username,
          @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
          @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
          @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    Page<PassengerRatingResponse> ratingResponsePage =
            passengerRatingService.getPassengerRatings(username, offset, limit, sortField);
    return ResponseEntity.ok(ratingResponsePage);
  }

  @PostMapping("/rate/{username}")
  public ResponseEntity<PassengerRatingResponse> savePassengerRating(
      @PathVariable String username,
      @RequestBody @Valid PassengerRatingRequest passengerRatingRequest) {

    PassengerRatingResponse ratingResponse =
        passengerRatingService.savePassengerRating(username, passengerRatingRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(ratingResponse);
  }
}
