package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.passengerservice.model.Passenger;

import java.util.List;

public interface PassengerService {

    List<Passenger> getPassengers();

    Passenger getPassengerById(Long id);

    Passenger savePassenger(Passenger passenger);

    Passenger updatePassenger(Long id, Passenger passenger);

    void deletePassenger(Long id);
}
