package com.dulfinne.taxi.promocodeservice.service;

import com.dulfinne.taxi.avro.PromocodeUsageRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeUsageResponse;

public interface PromocodeUsageService {

  PromocodeUsageResponse getPromocodeUsageById(String id);

  PaginatedResponse<PromocodeUsageResponse> getAllPromocodeUsages(
      Integer offset, Integer limit, String sortField, String sortOrder);

  PaginatedResponse<PromocodeUsageResponse> getPromocodeUsagesByUsername(
      String username, Integer offset, Integer limit, String sortField, String sortOrder);

  PromocodeUsageResponse createPromocodeUsage(PromocodeUsageRequest request);
}
