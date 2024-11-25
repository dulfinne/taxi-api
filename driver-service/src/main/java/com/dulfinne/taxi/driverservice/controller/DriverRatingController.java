package com.dulfinne.taxi.driverservice.controller;

import com.dulfinne.taxi.driverservice.dto.request.DriverRatingRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import com.dulfinne.taxi.driverservice.service.DriverRatingService;
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
@RequestMapping("api/v1/drivers")
@RequiredArgsConstructor
public class DriverRatingController {

  private final DriverRatingService ratingService;

  @GetMapping("/{username}/ratings")
  public ResponseEntity<Page<DriverRatingResponse>> getAllDriverRatingsByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    Page<DriverRatingResponse> driverRatingResponsePage =
        ratingService.getAllDriverRatings(username, offset, limit, sortField);
    return ResponseEntity.ok(driverRatingResponsePage);
  }

  @GetMapping("/ratings")
  public ResponseEntity<Page<DriverRatingResponse>> getAllDriverRatings(
      Principal principal,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    Page<DriverRatingResponse> driverRatingResponsePage =
        ratingService.getAllDriverRatings(principal.getName(), offset, limit, sortField);
    return ResponseEntity.ok(driverRatingResponsePage);
  }

  //TODO: Remove endpoint after adding kafka in ride-service
  @PostMapping("{username}/rate")
  public ResponseEntity<DriverRatingResponse> saveDriverRating(
      @PathVariable String username, @RequestBody @Valid DriverRatingRequest driverRatingRequest) {

    DriverRatingResponse driverRatingResponse =
        ratingService.saveDriverRating(username, driverRatingRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(driverRatingResponse);
  }
}
