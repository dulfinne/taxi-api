package com.dulfinne.taxi.driverservice.controller;

import com.dulfinne.taxi.driverservice.dto.request.CarRequest;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
import com.dulfinne.taxi.driverservice.service.CarService;
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
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

  private final CarService carService;

  @GetMapping
  public ResponseEntity<Page<CarResponse>> getAllCars(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "carCategory") String sortField) {

    Page<CarResponse> carResponsePage = carService.getAllCars(offset, limit, sortField);
    return ResponseEntity.ok(carResponsePage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarResponse> getCarById(@PathVariable Long id) {
    CarResponse carResponse = carService.getCarById(id);
    return ResponseEntity.ok(carResponse);
  }

  @PostMapping
  public ResponseEntity<CarResponse> saveCar(@RequestBody @Valid CarRequest carRequest) {
    CarResponse carResponse = carService.saveCar(carRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(carResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CarResponse> updateCar(
      @PathVariable Long id, @RequestBody @Valid CarRequest carRequest) {

    CarResponse carResponse = carService.updateCar(id, carRequest);
    return ResponseEntity.ok(carResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return ResponseEntity.noContent().build();
  }
}
