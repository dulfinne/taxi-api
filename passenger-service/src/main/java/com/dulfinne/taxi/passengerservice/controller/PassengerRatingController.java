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

  @GetMapping("/ratings/{id}")
  public ResponseEntity<Page<PassengerRatingResponse>> getAllPassengerRatings(
      @PathVariable long id,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    Page<PassengerRatingResponse> ratingResponsePage =
        passengerRatingService.getPassengerRatings(id, offset, limit, sortField);

    return ResponseEntity.ok(ratingResponsePage);
  }

  @PostMapping("/rate/{id}")
  public ResponseEntity<PassengerRatingResponse> savePassengerRating(
      @PathVariable long id,
      @RequestBody @Valid PassengerRatingRequest passengerRatingRequest) {

    PassengerRatingResponse ratingResponse =
        passengerRatingService.savePassengerRating(id, passengerRatingRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(ratingResponse);
  }
}
