package com.dulfinne.taxi.paymentservice.util;

import java.util.List;

import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaginationTestData {

  public final Integer DEFAULT_OFFSET = 0;
  public final Integer DEFAULT_LIMIT = 2;
  public final String SORT_FIELD = "id";
  public final String SORT_ORDER = "ASC";
  public final int DEFAULT_TOTAL_ELEMENTS = 2;
  public final int DEFAULT_TOTAL_PAGES = 1;

  public <T> PaginatedResponse<T> getPaginatedResponse(List<T> content) {
    return PaginatedResponse.<T>builder()
        .content(content)
        .pageNumber(DEFAULT_OFFSET)
        .pageSize(DEFAULT_LIMIT)
        .totalElements(DEFAULT_TOTAL_ELEMENTS)
        .totalPages(DEFAULT_TOTAL_PAGES)
        .build();
  }
}
