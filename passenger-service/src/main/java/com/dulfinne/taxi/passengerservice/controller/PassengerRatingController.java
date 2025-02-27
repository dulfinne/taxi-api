package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.controller.api.PassengerRatingApi;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.service.PassengerRatingService;
import com.dulfinne.taxi.passengerservice.util.HeaderConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerRatingController implements PassengerRatingApi {

  private final PassengerRatingService passengerRatingService;

  @GetMapping("/{username}/ratings")
  public ResponseEntity<List<PassengerRatingResponse>> getAllPassengerRatingsByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    List<PassengerRatingResponse> ratingResponsePage =
        passengerRatingService.getPassengerRatings(username, offset, limit, sortField);
    return ResponseEntity.ok(ratingResponsePage);
  }

  @GetMapping("/ratings")
  public ResponseEntity<List<PassengerRatingResponse>> getAllPassengerRatings(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    List<PassengerRatingResponse> ratingResponsePage =
        passengerRatingService.getPassengerRatings(username, offset, limit, sortField);
    return ResponseEntity.ok(ratingResponsePage);
  }
}
