package com.dulfinne.taxi.passengerservice.service.impl;

import com.dulfinne.taxi.passengerservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import com.dulfinne.taxi.passengerservice.repository.PassengerInfoRepository;
import com.dulfinne.taxi.passengerservice.service.PassengerInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerInfoServiceImpl implements PassengerInfoService {

    private final PassengerInfoRepository passengerInfoRepository;

    @Transactional(readOnly = true)
    @Override
    public List<PassengerInfo> getPassengersInfo() {
        return passengerInfoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public PassengerInfo getPassengerInfoById(Long id) {
        return getPassengerInfoIfExists(id);
    }

    @Transactional
    @Override
    public PassengerInfo updatePassengerInfo(Long id, PassengerInfo passengerInfo) {
        PassengerInfo passengerInfoForUpdate = getPassengerInfoIfExists(id);

        passengerInfoForUpdate.setFirstName(passengerInfo.getFirstName());
        passengerInfoForUpdate.setLastName(passengerInfo.getLastName());
        passengerInfoForUpdate.setPhoneNumber(passengerInfo.getPhoneNumber());
        passengerInfoForUpdate.setPayment(passengerInfo.getPayment());

        return passengerInfoRepository.save(passengerInfoForUpdate);
    }

    private PassengerInfo getPassengerInfoIfExists(Long id) {
        return passengerInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger info not found: id = " + id));
    }
}
