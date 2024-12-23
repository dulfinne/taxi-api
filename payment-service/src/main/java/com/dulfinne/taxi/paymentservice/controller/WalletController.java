package com.dulfinne.taxi.paymentservice.controller;

import com.dulfinne.taxi.paymentservice.dto.request.MoneyRequest;
import com.dulfinne.taxi.paymentservice.dto.response.CanPayByCardResponse;
import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.WalletResponse;
import com.dulfinne.taxi.paymentservice.service.WalletService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments/wallets")
@RequiredArgsConstructor
public class WalletController {
  private final WalletService service;

  @GetMapping
  public ResponseEntity<PaginatedResponse<WalletResponse>> getAllWallets(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "balance") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder) {

    PaginatedResponse<WalletResponse> walletsResponse =
        service.getAllWallets(offset, limit, sortField, sortOrder);
    return ResponseEntity.ok(walletsResponse);
  }

  // For ADMIN
  // TODO: Change path to "/{username}"
  @GetMapping("/username/{username}")
  public ResponseEntity<WalletResponse> getWalletByUsername(@PathVariable String username) {
    WalletResponse response = service.getWalletByUsername(username);
    return ResponseEntity.ok(response);
  }

  // For USER
  // TODO: Later will get 'username' from token
  @GetMapping("/{username}")
  public ResponseEntity<WalletResponse> getWallet(@PathVariable String username) {
    WalletResponse response = service.getWalletByUsername(username);
    return ResponseEntity.ok(response);
  }

  // TODO: Later will get 'username' from token
  @PostMapping("/{username}")
  public ResponseEntity<WalletResponse> createWallet(@PathVariable String username) {
    WalletResponse response = service.createWallet(username);
    return ResponseEntity.ok(response);
  }

  // TODO: Later will get 'username' from token
  @PostMapping("/{username}/credit")
  public ResponseEntity<WalletResponse> creditMoney(
      @PathVariable String username, @RequestBody @Valid MoneyRequest request) {
    WalletResponse response = service.creditMoney(username, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // TODO: Later will get 'username' from token
  @PostMapping("/{username}/debit")
  public ResponseEntity<WalletResponse> debitMoney(
      @PathVariable String username, @RequestBody @Valid MoneyRequest request) {
    WalletResponse response = service.debitMoney(username, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // TODO: Later will get 'username' from token
  @PostMapping("/{username}/repay-debt")
  public ResponseEntity<WalletResponse> repayDebt(@PathVariable String username) {
    WalletResponse response = service.repayDebt(username);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("{username}/card-payment-check")
  public ResponseEntity<CanPayByCardResponse> canPayWithCard(@PathVariable String username) {
    CanPayByCardResponse response = service.canPayWithCard(username);
    return ResponseEntity.ok(response);
  }
}
