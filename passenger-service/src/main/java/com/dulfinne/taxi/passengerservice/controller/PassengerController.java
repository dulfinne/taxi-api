package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.mapper.PassengerMapper;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import com.dulfinne.taxi.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getPassengers();
        return ResponseEntity.ok(passengerMapper.toPassengerResponseList(passengers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassenger(@PathVariable Long id) {
        Passenger passenger = passengerService.getPassengerById(id);
        return ResponseEntity.ok(passengerMapper.toPassengerResponse(passenger));
    }

    @PostMapping
    public ResponseEntity<PassengerResponse> savePassenger(@RequestBody @Valid PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toPassenger(passengerRequest);
        passenger = passengerService.savePassenger(passenger);
        return ResponseEntity.ok(passengerMapper.toPassengerResponse(passenger));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(@PathVariable Long id,
                                                             @RequestBody @Valid PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toPassenger(passengerRequest);
        passenger = passengerService.updatePassenger(id, passenger);
        return ResponseEntity.ok(passengerMapper.toPassengerResponse(passenger));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}

