package com.dulfinne.taxi.promocodeservice.service;

import com.dulfinne.taxi.promocodeservice.dto.request.DiscountRequest;
import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.DiscountResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeResponse;

public interface PromocodeService {
  PaginatedResponse<PromocodeResponse> getAllPromocodes(
      Integer offset, Integer limit, String sortField, String sortOrder);

  PromocodeResponse getPromocodeByCode(String code);

  PromocodeResponse createPromocode(PromocodeRequest request);

  PromocodeResponse updatePromocode(String code, PromocodeRequest request);

  DiscountResponse getDiscount(DiscountRequest request);
}
