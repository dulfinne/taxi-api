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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {

  private final PassengerService passengerService;

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<PassengerResponse>> getAllPassengers(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rideCount") String sortField) {

    Page<PassengerResponse> infoResponsePage =
        passengerService.getAllPassengers(offset, limit, sortField);

    return ResponseEntity.ok(infoResponsePage);
  }

  @GetMapping("/{username}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<PassengerResponse> getPassengerByUsername(@PathVariable String username) {
    PassengerResponse response = passengerService.getPassengerByUsername(username);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  @PreAuthorize("hasRole('PASSENGER')")
  public ResponseEntity<PassengerResponse> getPassenger(Principal principal) {
    PassengerResponse response = passengerService.getPassengerByUsername(principal.getName());
    return ResponseEntity.ok(response);
  }

  @PostMapping
  @PreAuthorize("hasRole('PASSENGER')")
  public ResponseEntity<PassengerResponse> savePassenger(
          Principal principal, @RequestBody @Valid PassengerRequest passengerRequest) {

    PassengerResponse infoResponse = passengerService.savePassenger(principal.getName(), passengerRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(infoResponse);
  }

  @PutMapping
  @PreAuthorize("hasRole('PASSENGER')")
  public ResponseEntity<PassengerResponse> updatePassenger(
          Principal principal, @RequestBody @Valid PassengerRequest passengerRequest) {

    PassengerResponse infoResponse = passengerService.updatePassenger(principal.getName(), passengerRequest);
    return ResponseEntity.ok(infoResponse);
  }

  @DeleteMapping
  @PreAuthorize("hasRole('PASSENGER')")
  public ResponseEntity<Void> deletePassenger(Principal principal) {
    passengerService.deletePassenger(principal.getName());
    return ResponseEntity.noContent().build();
  }
}
