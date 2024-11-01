package com.dulfinne.taxi.passengerservice.converter;

import com.dulfinne.taxi.passengerservice.dto.response.PassengerInfoResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PassengerInfoToPassengerInfoResponseConverter implements Converter<PassengerInfo, PassengerInfoResponse> {

    @Override
    public PassengerInfoResponse convert(PassengerInfo source) {
        return new PassengerInfoResponse(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getPhoneNumber(),
                source.getPayment(),
                source.getRideCount(),
                source.getAverageRating()
        );
    }
}
