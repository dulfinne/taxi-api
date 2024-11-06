package com.dulfinne.taxi.driverservice.controller;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

  @GetMapping
  public ResponseEntity<Page<DriverResponse>> getAllDrivers(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "averageRating") String sortField) {

    // TODO: Services implemented

    Page<DriverResponse> driverResponsePage = null;

    return ResponseEntity.ok(driverResponsePage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DriverResponse> getDriverById(@PathVariable Long id) {

    // TODO: Services implemented

    DriverResponse driverResponse = null;
    return ResponseEntity.ok(driverResponse);
  }

  // TODO: Later will get username from token
  @PostMapping("/{username}")
  public ResponseEntity<DriverResponse> saveDriver(
      @PathVariable String username, @RequestBody @Valid DriverRequest driverRequest) {

    // TODO: Services implemented

    DriverResponse driverResponse = null;
    return ResponseEntity.status(HttpStatus.CREATED).body(driverResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DriverResponse> updateDriver(
      @PathVariable Long id, @RequestBody @Valid DriverRequest driverRequest) {

    // TODO: Services implemented

    DriverResponse driverResponse = null;
    return ResponseEntity.ok(driverResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {

    // TODO: Services implemented

    DriverResponse driverResponse = null;
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{driverId}/assign-car/{carId}")
  public ResponseEntity<DriverResponse> assignCarToDriver(
      @PathVariable Long driverId, @PathVariable Long carId) {

    // TODO: Services implemented

    DriverResponse driverResponse = null;
    return ResponseEntity.ok(driverResponse);
  }

  @PutMapping("/{driverId}/remove-car/{carId}")
  public ResponseEntity<DriverResponse> removeCarFromDriver(
      @PathVariable Long driverId, @PathVariable Long carId) {

    // TODO: Services implemented

    DriverResponse driverResponse = null;
    return ResponseEntity.ok(driverResponse);
  }
}
