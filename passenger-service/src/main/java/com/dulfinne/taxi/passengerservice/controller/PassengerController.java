package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {

  private final PassengerService passengerService;

  @GetMapping
  public ResponseEntity<Page<PassengerResponse>> getAllPassengers(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rideCount") String sortField) {

    Page<PassengerResponse> infoResponsePage =
        passengerService.getAllPassengers(offset, limit, sortField);

    return ResponseEntity.ok(infoResponsePage);
  }

  // For ADMIN
  @GetMapping("/{username}/admin")
  public ResponseEntity<PassengerResponse> getPassengerByUsername(@PathVariable String username) {
    PassengerResponse response = passengerService.getPassengerByUsername(username);
    return ResponseEntity.ok(response);
  }

  // For PASSENGER
  // TODO: Later will get username from token
  @GetMapping("/{username}")
  public ResponseEntity<PassengerResponse> getPassenger(@PathVariable String username) {
    PassengerResponse response = passengerService.getPassengerByUsername(username);
    return ResponseEntity.ok(response);
  }

  // TODO: Later will get username from token
  @PostMapping("/{username}")
  public ResponseEntity<PassengerResponse> savePassenger(
      @PathVariable String username, @RequestBody @Valid PassengerRequest passengerRequest) {

    PassengerResponse infoResponse = passengerService.savePassenger(username, passengerRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(infoResponse);
  }

  // TODO: Later will get username from token
  @PutMapping("/{username}")
  public ResponseEntity<PassengerResponse> updatePassenger(
      @PathVariable String username, @RequestBody @Valid PassengerRequest passengerRequest) {

    PassengerResponse infoResponse = passengerService.updatePassenger(username, passengerRequest);
    return ResponseEntity.ok(infoResponse);
  }

  // TODO: Later will get username from token
  @DeleteMapping("/{username}")
  public ResponseEntity<Void> deletePassenger(@PathVariable String username) {
    passengerService.deletePassenger(username);
    return ResponseEntity.noContent().build();
  }
}
