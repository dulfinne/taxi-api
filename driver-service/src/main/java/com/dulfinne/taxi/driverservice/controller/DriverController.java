package com.dulfinne.taxi.driverservice.controller;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.service.DriverService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DriverController {

  private final DriverService driverService;

  @GetMapping
  public ResponseEntity<Page<DriverResponse>> getAllDrivers(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "experience") String sortField) {

    Page<DriverResponse> driverResponsePage = driverService.getAllDrivers(offset, limit, sortField);
    return ResponseEntity.ok(driverResponsePage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DriverResponse> getDriverById(@PathVariable Long id) {
    DriverResponse driverResponse = driverService.getDriverById(id);
    return ResponseEntity.ok(driverResponse);
  }

  // TODO: Later will get username from token
  @PostMapping("/{username}")
  public ResponseEntity<DriverResponse> saveDriver(
      @PathVariable String username, @RequestBody @Valid DriverRequest driverRequest) {

    DriverResponse driverResponse = driverService.saveDriver(username, driverRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(driverResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DriverResponse> updateDriver(
      @PathVariable Long id, @RequestBody @Valid DriverRequest driverRequest) {

    DriverResponse driverResponse = driverService.updateDriver(id, driverRequest);
    return ResponseEntity.ok(driverResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
    driverService.deleteDriver(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{driverId}/assign-car/{carId}")
  public ResponseEntity<DriverResponse> assignCarToDriver(
      @PathVariable Long driverId, @PathVariable Long carId) {

    DriverResponse driverResponse = driverService.assignCarToDriver(driverId, carId);
    return ResponseEntity.ok(driverResponse);
  }

  @PutMapping("/{driverId}/remove-car/{carId}")
  public ResponseEntity<DriverResponse> removeCarFromDriver(
      @PathVariable Long driverId, @PathVariable Long carId) {

    DriverResponse driverResponse = driverService.removeCarFromDriver(driverId, carId);
    return ResponseEntity.ok(driverResponse);
  }
}
