package com.dulfinne.taxi.passengerservice.converter;

import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PassengerToPassengerResponseConverter implements Converter<Passenger, PassengerResponse> {

    @Override
    public PassengerResponse convert(Passenger source) {
        return new PassengerResponse(
                source.getId(),
                source.getUsername(),
                source.getEmail(),
                source.getCreatedAt()
        );
    }
}
