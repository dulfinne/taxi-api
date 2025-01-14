package com.dulfinne.taxi.paymentservice.controller.api;

import com.dulfinne.taxi.paymentservice.dto.request.MoneyRequest;
import com.dulfinne.taxi.paymentservice.dto.response.CanPayByCardResponse;
import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.WalletResponse;
import com.dulfinne.taxi.paymentservice.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Wallet Controller", description = "Interactions with wallet")
public interface WalletApi {
  @Operation(
      operationId = "getAllWallets",
      summary = "Get all wallets",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaginatedResponse.class))),
      })
  @GetMapping("/all")
  ResponseEntity<PaginatedResponse<WalletResponse>> getAllWallets(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "balance") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder);

  @Operation(
      operationId = "getWalletByUsername",
      summary = "Get user's wallet for ADMIN",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = WalletResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Wallet not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/{username}")
  ResponseEntity<WalletResponse> getWalletByUsername(@PathVariable String username);

  @Operation(
      operationId = "getWallet",
      summary = "Get user's wallet",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = WalletResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Wallet not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping
  ResponseEntity<WalletResponse> getWallet(String username);

  @Operation(
      operationId = "createWallet",
      summary = "Create wallet",
      responses = {
        @ApiResponse(
            responseCode = "201",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = WalletResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Wallet already created",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping
  ResponseEntity<WalletResponse> createWallet(String username);

  @Operation(
      operationId = "creditMoney",
      summary = "Credit money to wallet",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = WalletResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Invalid amount",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Wallet not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping("/credit")
  ResponseEntity<WalletResponse> creditMoney(
      String username, @RequestBody @Valid MoneyRequest request);

  @Operation(
      operationId = "debitMoney",
      summary = "Debit money from wallet",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = WalletResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Invalid amount",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Wallet not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping("/debit")
  ResponseEntity<WalletResponse> debitMoney(
      String username, @RequestBody @Valid MoneyRequest request);

  @Operation(
      operationId = "repayDebt",
      summary = "Repay debt",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = WalletResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Can't repay debt",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Wallet not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping("/repay-debt")
  ResponseEntity<WalletResponse> repayDebt(String username);

  @Operation(
      operationId = "canPayWithCard",
      summary = "Check card payment availability",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CanPayByCardResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Wallet not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping("{username}/card-payment-check")
  ResponseEntity<CanPayByCardResponse> canPayWithCard(@PathVariable String username);
}
