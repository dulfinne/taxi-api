package com.dulfinne.taxi.promocodeservice.controller;

import com.dulfinne.taxi.promocodeservice.dto.request.DiscountRequest;
import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.DiscountResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeResponse;
import com.dulfinne.taxi.promocodeservice.service.PromocodeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/promocodes")
@RequiredArgsConstructor
@Validated
public class PromocodeController {

  private final PromocodeService service;

  @GetMapping
  public ResponseEntity<PaginatedResponse<PromocodeResponse>> getAllPromocodes(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "isActive") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder) {

    PaginatedResponse<PromocodeResponse> promocodesResponse =
        service.getAllPromocodes(offset, limit, sortField, sortOrder);
    return ResponseEntity.ok(promocodesResponse);
  }

  @GetMapping("/{code}")
  public ResponseEntity<PromocodeResponse> getPromocodeByCode(@PathVariable String code) {
    PromocodeResponse promocodeResponse = service.getPromocodeByCode(code);
    return ResponseEntity.ok(promocodeResponse);
  }

  @PostMapping
  public ResponseEntity<PromocodeResponse> createPromocode(
      @RequestBody @Valid PromocodeRequest request) {
    PromocodeResponse promocodeResponse = service.createPromocode(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(promocodeResponse);
  }

  @PutMapping("/{code}")
  public ResponseEntity<PromocodeResponse> updatePromocode(
      @PathVariable String code, @RequestBody @Valid PromocodeRequest request) {
    PromocodeResponse promocodeResponse = service.updatePromocode(code, request);
    return ResponseEntity.ok(promocodeResponse);
  }

  @PostMapping("/discount")
  public ResponseEntity<DiscountResponse> getDiscount(@RequestBody @Valid DiscountRequest request) {
    DiscountResponse discountResponse = service.getDiscount(request);
    return ResponseEntity.ok(discountResponse);
  }
}
