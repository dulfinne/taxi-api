package com.dulfinne.taxi.passengerservice.controller;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerInfoRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerInfoResponse;
import com.dulfinne.taxi.passengerservice.mapper.PassengerInfoMapper;
import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import com.dulfinne.taxi.passengerservice.service.PassengerInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerInfoController {

    private final PassengerInfoService passengerInfoService;
    private final PassengerInfoMapper passengerInfoMapper;

    @GetMapping("/info")
    public ResponseEntity<List<PassengerInfoResponse>> getAllPassengersInfo() {
        List<PassengerInfo> passengerInfoList = passengerInfoService.getPassengersInfo();
        return ResponseEntity.ok(passengerInfoMapper.toPassengerInfoResponseList(passengerInfoList));
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<PassengerInfoResponse> getPassengerInfo(@PathVariable Long id) {
        PassengerInfo passengerInfo = passengerInfoService.getPassengerInfoById(id);
        return ResponseEntity.ok(passengerInfoMapper.toPassengerInfoResponse(passengerInfo));
    }

    @PutMapping("/{id}/info")
    public ResponseEntity<PassengerInfoResponse> updatePassengerInfo(@PathVariable Long id,
                                                                     @RequestBody @Valid PassengerInfoRequest passengerInfoRequest) {
        PassengerInfo passengerInfo = passengerInfoMapper.toPassengerInfo(passengerInfoRequest);
        passengerInfo = passengerInfoService.updatePassengerInfo(id, passengerInfo);
        return ResponseEntity.ok(passengerInfoMapper.toPassengerInfoResponse(passengerInfo));
    }
}
