package com.dulfinne.taxi.promocodeservice.util;

import com.dulfinne.taxi.avro.PromocodeUsageRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeUsageResponse;
import com.dulfinne.taxi.promocodeservice.model.PromocodeUsage;
import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class UsageTestData {

  public static final Instant USAGE_DATE = Instant.now();
  public static final String USAGE_ID = "6759f65c37b3e1256f49a5c9";
  public static final Long USAGE_RIDE_ID = 1L;

  public PromocodeUsageRequest getUsageRequest() {
    return PromocodeUsageRequest.newBuilder()
        .setCode(PromocodeTestData.FIRST_CODE)
        .setUsername(PromocodeTestData.USERNAME)
        .setRideId(USAGE_RIDE_ID)
        .build();
  }

  public PromocodeUsage getUsage() {
    return PromocodeUsage.builder()
        .id(USAGE_ID)
        .code(PromocodeTestData.FIRST_CODE)
        .username(PromocodeTestData.USERNAME)
        .usageDate(USAGE_DATE)
        .rideId(USAGE_RIDE_ID)
        .build();
  }

  public PromocodeUsageResponse getUsageResponse() {
    return PromocodeUsageResponse.builder()
        .id(USAGE_ID)
        .code(PromocodeTestData.FIRST_CODE)
        .username(PromocodeTestData.USERNAME)
        .usageDate(USAGE_DATE)
        .rideId(USAGE_RIDE_ID)
        .build();
  }
}
