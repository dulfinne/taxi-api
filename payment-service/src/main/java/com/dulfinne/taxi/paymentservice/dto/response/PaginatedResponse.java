package com.dulfinne.taxi.paymentservice.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record PaginatedResponse<T>(
    List<T> content,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages
) {
}
