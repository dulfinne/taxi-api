package com.dulfinne.taxi.promocodeservice.util;

import com.dulfinne.taxi.promocodeservice.dto.response.PaginatedResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PaginationTestData {

  public final Integer DEFAULT_OFFSET = 0;
  public final Integer DEFAULT_LIMIT = 2;
  public final String PROMOCODE_SORT_FIELD = "isActive";
  public final String USAGE_SORT_FIELD = "isActive";
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
