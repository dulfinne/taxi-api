package com.dulfinne.taxi.promocodeservice.controller;

import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeUsageRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeUsageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/promocodes/usages")
@RequiredArgsConstructor
@Validated
public class PromocodeUsageController {

  @GetMapping("/{id}")
  public ResponseEntity<PromocodeUsageResponse> getPromocodeUsageById(@PathVariable String id) {

    // TODO: Impl services
    PromocodeUsageResponse usageResponse = null;
    return ResponseEntity.ok(usageResponse);
  }

  @GetMapping
  public ResponseEntity<Page<PromocodeUsageResponse>> getAllPromocodeUsages(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "promocode") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") Sort.Direction sortOrder) {

    // TODO: Impl services
    Page<PromocodeUsageResponse> usageResponsePage = null;
    return ResponseEntity.ok(usageResponsePage);
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<Page<PromocodeUsageResponse>> getPromocodeUsageByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "usageDate") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") Sort.Direction sortOrder) {

    // TODO: Impl services
    Page<PromocodeUsageResponse> usageResponsePage = null;
    return ResponseEntity.ok(usageResponsePage);
  }

  @PostMapping
  public ResponseEntity<PromocodeUsageResponse> createPromocodeUsage(
      @RequestBody @Valid PromocodeUsageRequest request) {

    // TODO: Impl services
    PromocodeUsageResponse usageResponse = null;
    return ResponseEntity.status(HttpStatus.CREATED).body(usageResponse);
  }
}
