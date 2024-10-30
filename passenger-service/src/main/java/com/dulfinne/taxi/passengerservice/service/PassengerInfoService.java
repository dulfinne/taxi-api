package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.passengerservice.model.PassengerInfo;

import java.util.List;

public interface PassengerInfoService {

    List<PassengerInfo> getPassengersInfo();

    PassengerInfo getPassengerInfoById(Long id);

    PassengerInfo updatePassengerInfo(Long id, PassengerInfo passengerInfo);
}
