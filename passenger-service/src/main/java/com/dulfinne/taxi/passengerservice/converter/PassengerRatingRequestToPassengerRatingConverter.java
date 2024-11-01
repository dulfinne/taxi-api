package com.dulfinne.taxi.passengerservice.converter;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PassengerRatingRequestToPassengerRatingConverter implements Converter<PassengerRatingRequest, PassengerRating> {
    @Override
    public PassengerRating convert(PassengerRatingRequest source) {
        PassengerRating passengerRating = new PassengerRating();

        passengerRating.setRating(source.rating());
        passengerRating.setFeedback(source.feedback());

        return passengerRating;
    }
}
