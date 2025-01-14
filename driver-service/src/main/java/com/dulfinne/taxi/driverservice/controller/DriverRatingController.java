package com.dulfinne.taxi.driverservice.controller;

import com.dulfinne.taxi.driverservice.controller.api.DriverRatingApi;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import com.dulfinne.taxi.driverservice.service.DriverRatingService;
import com.dulfinne.taxi.driverservice.util.HeaderConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/drivers")
@RequiredArgsConstructor
public class DriverRatingController implements DriverRatingApi {

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
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField) {

    Page<DriverRatingResponse> driverRatingResponsePage =
        ratingService.getAllDriverRatings(username, offset, limit, sortField);
    return ResponseEntity.ok(driverRatingResponsePage);
  }
}
