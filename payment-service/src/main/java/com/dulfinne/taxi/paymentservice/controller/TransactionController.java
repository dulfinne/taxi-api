package com.dulfinne.taxi.paymentservice.controller;

import com.dulfinne.taxi.paymentservice.controller.api.TransactionApi;
import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.TransactionResponse;
import com.dulfinne.taxi.paymentservice.service.TransactionService;
import com.dulfinne.taxi.paymentservice.util.HeaderConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments/transactions")
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {
  private final TransactionService service;

  @GetMapping("/all")
  public ResponseEntity<PaginatedResponse<TransactionResponse>> getAllTransactions(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "id") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder) {

    PaginatedResponse<TransactionResponse> wallets =
        service.getAllTransactions(offset, limit, sortField, sortOrder);
    return ResponseEntity.ok(wallets);
  }

  @GetMapping("/{username}")
  public ResponseEntity<PaginatedResponse<TransactionResponse>> getTransactionsByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "id") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder) {

    PaginatedResponse<TransactionResponse> wallets =
        service.getTransactionsByUsername(username, offset, limit, sortField, sortOrder);
    return ResponseEntity.ok(wallets);
  }

  @GetMapping
  public ResponseEntity<PaginatedResponse<TransactionResponse>> getTransactions(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "id") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder) {

    PaginatedResponse<TransactionResponse> wallets =
        service.getTransactionsByUsername(username, offset, limit, sortField, sortOrder);
    return ResponseEntity.ok(wallets);
  }
}
