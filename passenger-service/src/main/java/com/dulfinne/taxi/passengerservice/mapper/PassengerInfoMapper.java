package com.dulfinne.taxi.passengerservice.mapper;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerInfoRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerInfoResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerInfoMapper {

    PassengerInfo toPassengerInfo(PassengerInfoRequest request);

    PassengerInfoResponse toPassengerInfoResponse(PassengerInfo passengerInfo);

    List<PassengerInfoResponse> toPassengerInfoResponseList(List<PassengerInfo> passengersInfo);
}
