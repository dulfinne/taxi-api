package com.dulfinne.taxi.passengerservice.converter;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PassengerRequestToPassengerConverter implements Converter<PassengerRequest, Passenger> {
    @Override
    public Passenger convert(PassengerRequest source) {
        Passenger passenger = new Passenger();

        passenger.setUsername(source.username());
        passenger.setPassword(source.password());
        passenger.setEmail(source.email());

        return passenger;
    }
}
