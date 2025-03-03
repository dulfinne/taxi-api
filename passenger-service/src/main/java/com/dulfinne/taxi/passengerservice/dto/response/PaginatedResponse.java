package com.dulfinne.taxi.passengerservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginatedResponse<T>(
        List<T> content,
        int offset,
        int limit,
        long totalElements,
        int totalPages
) {
}
