package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.controller.api.PassengerApi;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.service.PassengerService;
import com.dulfinne.taxi.passengerservice.util.HeaderConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
@Validated
public class PassengerController implements PassengerApi {

  private final PassengerService passengerService;

  @GetMapping("/all")
  public ResponseEntity<List<PassengerResponse>> getAllPassengers(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rideCount") String sortField) {

    List<PassengerResponse> infoResponsePage =
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
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username) {
    PassengerResponse response = passengerService.getPassengerByUsername(username);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<PassengerResponse> savePassenger(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username,
      @RequestBody @Valid PassengerRequest passengerRequest) {
    PassengerResponse infoResponse = passengerService.savePassenger(username, passengerRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(infoResponse);
  }

  @PutMapping
  public ResponseEntity<PassengerResponse> updatePassenger(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username,
      @RequestBody @Valid PassengerRequest passengerRequest) {
    PassengerResponse infoResponse = passengerService.updatePassenger(username, passengerRequest);
    return ResponseEntity.ok(infoResponse);
  }

  @DeleteMapping
  public ResponseEntity<Void> deletePassenger(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username) {
    passengerService.deletePassenger(username);
    return ResponseEntity.noContent().build();
  }
}
