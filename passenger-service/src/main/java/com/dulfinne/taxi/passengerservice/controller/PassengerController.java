package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.service.PassengerService;
import com.dulfinne.taxi.passengerservice.util.TokenConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
@Validated
public class PassengerController {

  private final PassengerService passengerService;

  @GetMapping("/all")
  public ResponseEntity<Page<PassengerResponse>> getAllPassengers(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rideCount") String sortField) {

    Page<PassengerResponse> infoResponsePage =
        passengerService.getAllPassengers(offset, limit, sortField);
    return ResponseEntity.ok(infoResponsePage);
  }

  @GetMapping("/{username}")
  public ResponseEntity<PassengerResponse> getPassengerByUsername(@PathVariable String username) {
    PassengerResponse response = passengerService.getPassengerByUsername(username);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<PassengerResponse> getPassenger(
      @CurrentSecurityContext(expression = "authentication.name") String username) {
    PassengerResponse response = passengerService.getPassengerByUsername(username);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<PassengerResponse> savePassenger(
      @CurrentSecurityContext(expression = "authentication.name") String username,
      @RequestBody @Valid PassengerRequest passengerRequest) {
    PassengerResponse infoResponse = passengerService.savePassenger(username, passengerRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(infoResponse);
  }

  @PutMapping
  public ResponseEntity<PassengerResponse> updatePassenger(
      @CurrentSecurityContext(expression = "authentication.name") String username,
      @RequestBody @Valid PassengerRequest passengerRequest) {
    PassengerResponse infoResponse = passengerService.updatePassenger(username, passengerRequest);
    return ResponseEntity.ok(infoResponse);
  }

  @DeleteMapping
  public ResponseEntity<Void> deletePassenger(
      @CurrentSecurityContext(expression = "authentication.name") String username) {
    passengerService.deletePassenger(username);
    return ResponseEntity.noContent().build();
  }
}
