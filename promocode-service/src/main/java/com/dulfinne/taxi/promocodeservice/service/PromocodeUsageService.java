package com.dulfinne.taxi.promocodeservice.service;

import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeUsageRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeUsageResponse;
import org.springframework.data.domain.Page;

public interface PromocodeUsageService {

  PromocodeUsageResponse getPromocodeUsageById(String id);

  Page<PromocodeUsageResponse> getAllPromocodeUsages(
      Integer offset, Integer limit, String sortField, String sortOrder);

  Page<PromocodeUsageResponse> getPromocodeUsagesByUsername(
      String username, Integer offset, Integer limit, String sortField, String sortOrder);

  PromocodeUsageResponse createPromocodeUsage(PromocodeUsageRequest request);
}
