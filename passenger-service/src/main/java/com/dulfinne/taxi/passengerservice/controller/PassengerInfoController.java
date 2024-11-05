package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerInfoRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerInfoResponse;
import com.dulfinne.taxi.passengerservice.service.PassengerInfoService;
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
public class PassengerInfoController {

  private final PassengerInfoService passengerInfoService;

  @GetMapping("/info")
  public ResponseEntity<Page<PassengerInfoResponse>> getAllPassengersInfo(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "averageRating") String sortField) {

    Page<PassengerInfoResponse> infoResponsePage =
        passengerInfoService.getAllPassengersInfo(offset, limit, sortField);

    return ResponseEntity.ok(infoResponsePage);
  }

  @GetMapping("/info/{passengerId}")
  public ResponseEntity<PassengerInfoResponse> getPassengerInfo(@PathVariable Long passengerId) {

    PassengerInfoResponse infoResponse =
        passengerInfoService.getPassengerInfoByPassengerId(passengerId);

    return ResponseEntity.ok(infoResponse);
  }

  @PostMapping("/info")
  public ResponseEntity<PassengerInfoResponse> savePassengerInfo(@RequestBody @Valid PassengerInfoRequest passengerInfoRequest) {

    PassengerInfoResponse infoResponse = passengerInfoService.savePassengerInfo(passengerInfoRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(infoResponse);
  }

  @PutMapping("/info")
  public ResponseEntity<PassengerInfoResponse> updatePassengerInfo(
      @RequestBody @Valid PassengerInfoRequest passengerInfoRequest) {

    PassengerInfoResponse infoResponse =
        passengerInfoService.updatePassengerInfo(passengerInfoRequest);

    return ResponseEntity.ok(infoResponse);
  }
}
