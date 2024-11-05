package com.dulfinne.taxi.driverservice.controller;

import com.dulfinne.taxi.driverservice.dto.request.DriverRatingRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/drivers")
public class DriverRatingController {

  @GetMapping("/ratings/{driverId}")
  public ResponseEntity<Page<DriverRatingResponse>> getAllDriverRatings(
      @PathVariable Long driverId,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "averageRating") String sortField) {

    // TODO: Services implemented

    Page<DriverRatingResponse> driverRatingResponsePage = null;

    return ResponseEntity.ok(driverRatingResponsePage);
  }

  @PostMapping("/rate/{driverId}")
  public ResponseEntity<DriverRatingResponse> getDriverRatingById(
      @PathVariable Long driverId, @RequestBody @Valid DriverRatingRequest driverRatingRequest) {

    // TODO: Services implemented

    DriverRatingResponse driverRatingResponse = null;
    return ResponseEntity.ok(driverRatingResponse);
  }
}
