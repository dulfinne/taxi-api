package com.dulfinne.taxi.passengerservice.mapper;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerRatingMapper {

    PassengerRating toPassengerRating(PassengerRatingRequest request);

    PassengerRatingResponse toPassengerRatingResponse(PassengerRating passengerRating);

    List<PassengerRatingResponse> toPassengerRatingResponseList(List<PassengerRating> passengerRatings);
}
