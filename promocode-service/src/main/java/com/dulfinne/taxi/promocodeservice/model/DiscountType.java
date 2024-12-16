package com.dulfinne.taxi.promocodeservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountType {
  PERCENTAGE(1),
  FIXED_AMOUNT(2);

  private final int id;

  public static DiscountType fromId(int id) {
    for (DiscountType type : values()) {
      if (type.getId() == id) {
        return type;
      }
    }
    throw new IllegalArgumentException(String.format("Invalid id for DiscountType: %d", id));
  }
}
