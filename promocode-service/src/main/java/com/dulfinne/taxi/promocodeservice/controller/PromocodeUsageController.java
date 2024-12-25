package com.dulfinne.taxi.promocodeservice.controller;

import com.dulfinne.taxi.promocodeservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeUsageResponse;
import com.dulfinne.taxi.promocodeservice.service.PromocodeUsageService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/promocodes/usages")
@RequiredArgsConstructor
@Validated
public class PromocodeUsageController {

  private final PromocodeUsageService service;

  @GetMapping("/{id}")
  public ResponseEntity<PromocodeUsageResponse> getPromocodeUsageById(@PathVariable String id) {
    PromocodeUsageResponse usageResponse = service.getPromocodeUsageById(id);
    return ResponseEntity.ok(usageResponse);
  }

  @GetMapping
  public ResponseEntity<PaginatedResponse<PromocodeUsageResponse>> getAllPromocodeUsages(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "promocode") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder) {

    PaginatedResponse<PromocodeUsageResponse> usagesResponse =
        service.getAllPromocodeUsages(offset, limit, sortField, sortOrder);
    return ResponseEntity.ok(usagesResponse);
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<PaginatedResponse<PromocodeUsageResponse>> getPromocodeUsagesByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "usageDate") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder) {

    PaginatedResponse<PromocodeUsageResponse> usageResponsePage =
        service.getPromocodeUsagesByUsername(username, offset, limit, sortField, sortOrder);
    return ResponseEntity.ok(usageResponsePage);
  }
}
