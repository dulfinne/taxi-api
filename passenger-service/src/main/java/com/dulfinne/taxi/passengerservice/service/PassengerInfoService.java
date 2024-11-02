package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerInfoRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerInfoResponse;
import org.springframework.data.domain.Page;

public interface PassengerInfoService {

  Page<PassengerInfoResponse> getAllPassengersInfo(Integer offset, Integer limit, String sortField);

  PassengerInfoResponse getPassengerInfoByPassengerId(Long id);

  PassengerInfoResponse savePassengerInfo(PassengerInfoRequest passengerInfoRequest);

  PassengerInfoResponse updatePassengerInfo(PassengerInfoRequest request);
}
