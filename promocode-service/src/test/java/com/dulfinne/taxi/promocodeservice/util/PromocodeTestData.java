package com.dulfinne.taxi.promocodeservice.util;

import com.dulfinne.taxi.promocodeservice.dto.request.DiscountRequest;
import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeResponse;
import com.dulfinne.taxi.promocodeservice.model.DiscountType;
import com.dulfinne.taxi.promocodeservice.model.Promocode;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PromocodeTestData {

  public static final String FIRST_ID = "6759f69c37b3e1256f49a5c9";
  public static final String FIRST_CODE = "PERC10";
  public static final BigDecimal FIRST_DISCOUNT = BigDecimal.TEN;
  public static final int FIRST_MAX_USAGES = 10;
  public static final int FIRST_USAGE_COUNT = 8;
  public static final boolean FIRST_IS_ACTIVE = true;
  public static final DiscountType FIRST_TYPE = DiscountType.PERCENTAGE;

  public static final String SECOND_ID = "1234f69c37b3e1256f49a5a4";
  public static final String SECOND_CODE = "AMOUNT20";
  public static final BigDecimal SECOND_DISCOUNT = BigDecimal.valueOf(20.0);
  public static final int SECOND_MAX_USAGES = 15;
  public static final int SECOND_USAGE_COUNT = 15;
  public static final boolean SECOND_IS_ACTIVE = true;
  public static final DiscountType SECOND_TYPE = DiscountType.FIXED_AMOUNT;

  public static final int START_USAGE_COUNT = 0;

  public static final String USERNAME = "user1";
  public static final BigDecimal PRICE = BigDecimal.valueOf(50.0);

  public static final BigDecimal PERCENTAGE_DISCOUNT = BigDecimal.valueOf(5.0);
  public static final BigDecimal FIXED_DISCOUNT = SECOND_DISCOUNT;

  public DiscountRequest getPercentageDiscountRequest() {
    return DiscountRequest.builder()
            .username(USERNAME)
            .price(PRICE)
            .code(FIRST_CODE)
            .build();
  }

  public DiscountRequest getFixedAmountDiscountRequest() {
    return DiscountRequest.builder()
            .username(USERNAME)
            .price(PRICE)
            .code(SECOND_CODE)
            .build();
  }

  public Promocode getFirst() {
    return Promocode.builder()
        .id(FIRST_ID)
        .code(FIRST_CODE)
        .discount(FIRST_DISCOUNT)
        .type(FIRST_TYPE.getId())
        .maxUsages(FIRST_MAX_USAGES)
        .usageCount(FIRST_USAGE_COUNT)
        .isActive(FIRST_IS_ACTIVE)
        .build();
  }

  public PromocodeResponse getFirstResponse() {
    return PromocodeResponse.builder()
        .id(FIRST_ID)
        .code(FIRST_CODE)
        .discount(FIRST_DISCOUNT)
        .type(FIRST_TYPE)
        .maxUsages(FIRST_MAX_USAGES)
        .usageCount(FIRST_USAGE_COUNT)
        .isActive(FIRST_IS_ACTIVE)
        .build();
  }

  public Promocode getSecond() {
    return Promocode.builder()
        .id(SECOND_ID)
        .code(SECOND_CODE)
        .discount(SECOND_DISCOUNT)
        .type(SECOND_TYPE.getId())
        .maxUsages(SECOND_MAX_USAGES)
        .usageCount(SECOND_USAGE_COUNT)
        .isActive(SECOND_IS_ACTIVE)
        .build();
  }

  public PromocodeResponse getSecondResponse() {
    return PromocodeResponse.builder()
        .id(SECOND_ID)
        .code(SECOND_CODE)
        .discount(SECOND_DISCOUNT)
        .type(SECOND_TYPE)
        .maxUsages(SECOND_MAX_USAGES)
        .usageCount(SECOND_USAGE_COUNT)
        .isActive(SECOND_IS_ACTIVE)
        .build();
  }

  public PromocodeRequest getCreateRequest() {
    return PromocodeRequest.builder()
        .code(FIRST_CODE)
        .discount(FIRST_DISCOUNT)
        .type(FIRST_TYPE)
        .maxUsages(FIRST_MAX_USAGES)
        .isActive(FIRST_IS_ACTIVE)
        .build();
  }

  public Promocode getCreated() {
    return Promocode.builder()
        .id(FIRST_ID)
        .code(FIRST_CODE)
        .discount(FIRST_DISCOUNT)
        .type(FIRST_TYPE.getId())
        .maxUsages(FIRST_MAX_USAGES)
        .usageCount(START_USAGE_COUNT)
        .isActive(FIRST_IS_ACTIVE)
        .build();
  }

  public PromocodeRequest getSecondRequest() {
    return PromocodeRequest.builder()
        .code(SECOND_CODE)
        .discount(SECOND_DISCOUNT)
        .type(SECOND_TYPE)
        .maxUsages(SECOND_MAX_USAGES)
        .isActive(SECOND_IS_ACTIVE)
        .build();
  }

  public Promocode getUpdated() {
    return Promocode.builder()
        .id(FIRST_ID)
        .code(SECOND_CODE)
        .discount(SECOND_DISCOUNT)
        .type(SECOND_TYPE.getId())
        .maxUsages(SECOND_MAX_USAGES)
        .usageCount(FIRST_USAGE_COUNT)
        .isActive(SECOND_IS_ACTIVE)
        .build();
  }

  public PromocodeResponse getUpdatedResponse() {
    return PromocodeResponse.builder()
        .id(FIRST_ID)
        .code(SECOND_CODE)
        .discount(SECOND_DISCOUNT)
        .type(SECOND_TYPE)
        .maxUsages(SECOND_MAX_USAGES)
        .usageCount(FIRST_USAGE_COUNT)
        .isActive(SECOND_IS_ACTIVE)
        .build();
  }

  public PromocodeResponse getCreatedResponse() {
    return PromocodeResponse.builder()
        .id(FIRST_ID)
        .code(FIRST_CODE)
        .discount(FIRST_DISCOUNT)
        .type(FIRST_TYPE)
        .maxUsages(FIRST_MAX_USAGES)
        .usageCount(START_USAGE_COUNT)
        .isActive(FIRST_IS_ACTIVE)
        .build();
  }
}
