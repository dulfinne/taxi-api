package com.dulfinne.taxi.promocodeservice.controller;

import com.dulfinne.taxi.promocodeservice.dto.request.DiscountRequest;
import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.DiscountResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeResponse;
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

  @GetMapping
  public ResponseEntity<Page<PromocodeResponse>> getAllPromocodes(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "isActive") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") Sort.Direction sortOrder) {

    // TODO: Impl services
    Page<PromocodeResponse> promocodeResponsePage = null;
    return ResponseEntity.ok(promocodeResponsePage);
  }

  @GetMapping("/{code}")
  public ResponseEntity<PromocodeResponse> getPromocodeByCode(@PathVariable String code) {

    // TODO: Impl services
    PromocodeResponse promocodeResponse = null;
    return ResponseEntity.ok(promocodeResponse);
  }

  @PostMapping
  public ResponseEntity<PromocodeResponse> createPromocode(
      @RequestBody @Valid PromocodeRequest request) {

    // TODO: Impl services
    PromocodeResponse promocodeResponse = null;
    return ResponseEntity.status(HttpStatus.CREATED).body(promocodeResponse);
  }

  @PutMapping("/{code}")
  public ResponseEntity<PromocodeResponse> updatePromocode(
      @PathVariable String code, @RequestBody @Valid PromocodeRequest request) {

    // TODO: Impl services
    PromocodeResponse promocodeResponse = null;
    return ResponseEntity.ok(promocodeResponse);
  }

  @PostMapping("/discount")
  public ResponseEntity<DiscountResponse> getDiscount(@RequestBody @Valid DiscountRequest request) {

    // TODO: Impl services
    DiscountResponse discountResponse = null;
    return ResponseEntity.ok(discountResponse);
  }
}
