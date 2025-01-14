package com.dulfinne.taxi.paymentservice.controller;

import com.dulfinne.taxi.paymentservice.controller.api.WalletApi;
import com.dulfinne.taxi.paymentservice.dto.request.MoneyRequest;
import com.dulfinne.taxi.paymentservice.dto.response.CanPayByCardResponse;
import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.WalletResponse;
import com.dulfinne.taxi.paymentservice.service.WalletService;
import com.dulfinne.taxi.paymentservice.util.HeaderConstants;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments/wallets")
@RequiredArgsConstructor
public class WalletController implements WalletApi {
  private final WalletService service;

  @GetMapping("/all")
  public ResponseEntity<PaginatedResponse<WalletResponse>> getAllWallets(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "balance") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder) {

    PaginatedResponse<WalletResponse> walletsResponse =
        service.getAllWallets(offset, limit, sortField, sortOrder);
    return ResponseEntity.ok(walletsResponse);
  }

  @GetMapping("/{username}")
  public ResponseEntity<WalletResponse> getWalletByUsername(@PathVariable String username) {
    WalletResponse response = service.getWalletByUsername(username);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<WalletResponse> getWallet(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username) {
    WalletResponse response = service.getWalletByUsername(username);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<WalletResponse> createWallet(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username) {
    WalletResponse response = service.createWallet(username);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/credit")
  public ResponseEntity<WalletResponse> creditMoney(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username,
      @RequestBody @Valid MoneyRequest request) {
    WalletResponse response = service.creditMoney(username, request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/debit")
  public ResponseEntity<WalletResponse> debitMoney(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username,
      @RequestBody @Valid MoneyRequest request) {
    WalletResponse response = service.debitMoney(username, request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/repay-debt")
  public ResponseEntity<WalletResponse> repayDebt(
      @RequestHeader(HeaderConstants.USERNAME_HEADER) String username) {
    WalletResponse response = service.repayDebt(username);
    return ResponseEntity.ok(response);
  }

  @PostMapping("{username}/card-payment-check")
  public ResponseEntity<CanPayByCardResponse> canPayWithCard(@PathVariable String username) {
    CanPayByCardResponse response = service.canPayWithCard(username);
    return ResponseEntity.ok(response);
  }
}
