package com.dulfinne.taxi.paymentservice.controller.api;

import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.TransactionResponse;
import com.dulfinne.taxi.paymentservice.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Transaction Controller", description = "Managing transactions")
public interface TransactionApi {

  @Operation(
      operationId = "getAllTransactions",
      summary = "Get all transactions",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaginatedResponse.class))),
      })
  @GetMapping("/all")
  ResponseEntity<PaginatedResponse<TransactionResponse>> getAllTransactions(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "id") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder);

  @Operation(
      operationId = "getTransactionsByUsername",
      summary = "Get all user's transactions for ADMIN",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaginatedResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Wallet not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/{username}")
  ResponseEntity<PaginatedResponse<TransactionResponse>> getTransactionsByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "id") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder);

  @Operation(
      operationId = "getTransactions",
      summary = "Get all user's transactions",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaginatedResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Wallet not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping
  ResponseEntity<PaginatedResponse<TransactionResponse>> getTransactions(
      String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "id") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder);
}
