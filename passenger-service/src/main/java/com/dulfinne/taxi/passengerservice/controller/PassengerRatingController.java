package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.service.PassengerRatingService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
      @CurrentSecurityContext(expression = "authentication.name") String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    Page<PassengerRatingResponse> ratingResponsePage =
        passengerRatingService.getPassengerRatings(username, offset, limit, sortField);
    return ResponseEntity.ok(ratingResponsePage);
  }
}
