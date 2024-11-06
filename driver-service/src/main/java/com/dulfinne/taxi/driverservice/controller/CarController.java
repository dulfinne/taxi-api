package com.dulfinne.taxi.driverservice.controller;

import com.dulfinne.taxi.driverservice.dto.request.CarRequest;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
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
@RequestMapping("/api/v1/cars")
public class CarController {

  @GetMapping
  public ResponseEntity<Page<CarResponse>> getAllCars(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "averageRating") String sortField) {

    // TODO: Services implemented

    Page<CarResponse> carResponsePage = null;

    return ResponseEntity.ok(carResponsePage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarResponse> getCarById(@PathVariable Long id) {

    // TODO: Services implemented

    CarResponse carResponse = null;
    return ResponseEntity.ok(carResponse);
  }

  @PostMapping
  public ResponseEntity<CarResponse> saveCar(@RequestBody @Valid CarRequest carRequest) {

    // TODO: Services implemented

    CarResponse carResponse = null;
    return ResponseEntity.status(HttpStatus.CREATED).body(carResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CarResponse> updateCar(
      @PathVariable Long id, @RequestBody @Valid CarRequest carRequest) {

    // TODO: Services implemented

    CarResponse carResponse = null;
    return ResponseEntity.ok(carResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable Long id) {

    // TODO: Services implemented

    CarResponse carResponse = null;
    return ResponseEntity.noContent().build();
  }
}
