package com.dulfinne.taxi.passengerservice.service.impl;

import com.dulfinne.taxi.passengerservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.passengerservice.exception.PassengerWithSuchUsernameAlreadyExists;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import com.dulfinne.taxi.passengerservice.model.Payment;
import com.dulfinne.taxi.passengerservice.repository.PassengerInfoRepository;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerInfoRepository passengerInfoRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Passenger> getPassengers() {
        return passengerRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Passenger getPassengerById(Long id) {
        return getPassengerIfExists(id);
    }

    @Transactional
    @Override
    public Passenger savePassenger(Passenger passenger) {
        String username = passenger.getUsername();
        if (passengerRepository.findByUsername(username).isPresent()) {
            throw new PassengerWithSuchUsernameAlreadyExists("Passenger with such username already exists: username = " + username);
        }
        passenger = passengerRepository.save(passenger);

        PassengerInfo passengerInfo = new PassengerInfo();
        passengerInfo.setPayment(Payment.CASH);
        passengerInfo.setRideCount(0);
        passengerInfo.setPassenger(passenger);
        passengerInfoRepository.save(passengerInfo);

        return passenger;
    }

    @Transactional
    @Override
    public Passenger updatePassenger(Long id, Passenger passenger) {
        Passenger passengerToUpdate = getPassengerIfExists(id);

        String username = passenger.getUsername();
        if (!passengerToUpdate.getUsername().equals(username)
                && passengerRepository.findByUsername(username).isPresent()) {
            throw new PassengerWithSuchUsernameAlreadyExists("Passenger with such username already exists: username = " + username);
        }

        passengerToUpdate.setUsername(passenger.getUsername());
        passengerToUpdate.setPassword(passenger.getPassword());
        passengerToUpdate.setEmail(passenger.getEmail());

        return passengerRepository.save(passengerToUpdate);
    }

    @Transactional
    @Override
    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new EntityNotFoundException("Passenger not found: id = " + id);
        }
        passengerRepository.deleteById(id);
    }

    private Passenger getPassengerIfExists(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found: id = " + id));
    }
}
