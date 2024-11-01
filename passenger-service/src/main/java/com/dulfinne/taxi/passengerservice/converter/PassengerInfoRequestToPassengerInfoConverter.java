package com.dulfinne.taxi.passengerservice.converter;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerInfoRequest;
import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PassengerInfoRequestToPassengerInfoConverter implements Converter<PassengerInfoRequest, PassengerInfo> {
    @Override
    public PassengerInfo convert(PassengerInfoRequest source) {
        PassengerInfo passengerInfo = new PassengerInfo();

        passengerInfo.setFirstName(source.firstName());
        passengerInfo.setLastName(source.lastName());
        passengerInfo.setPhoneNumber(source.phoneNumber());
        passengerInfo.setPayment(source.payment());

        return passengerInfo;
    }
}
