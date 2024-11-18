package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface PassengerService {

  Page<PassengerResponse> getAllPassengers(Integer offset, Integer limit, String sortField);

  PassengerResponse getPassengerByUsername(String username);

  PassengerResponse savePassenger(String username, PassengerRequest passengerRequest);

  PassengerResponse updatePassenger(String username, PassengerRequest request);

  void deletePassenger(String username);
}
