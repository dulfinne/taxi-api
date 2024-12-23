package com.dulfinne.taxi.paymentservice.mapper;

import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginatedMapper {

  public <T> PaginatedResponse<T> toPaginatedResponse(Page<T> page) {
    return PaginatedResponse.<T>builder()
        .content(page.getContent())
        .pageNumber(page.getNumber())
        .pageSize(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .build();
  }
}
