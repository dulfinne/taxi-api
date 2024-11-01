package com.dulfinne.taxi.passengerservice.converter;

import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PassengerRatingToPassengerRatingResponseConverter implements Converter<PassengerRating, PassengerRatingResponse> {
    @Override
    public PassengerRatingResponse convert(PassengerRating source) {
        return new PassengerRatingResponse(
                source.getId(),
                source.getRating(),
                source.getFeedback()
        );
    }
}
