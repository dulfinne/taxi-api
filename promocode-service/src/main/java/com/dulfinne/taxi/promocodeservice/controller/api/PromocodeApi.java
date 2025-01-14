package com.dulfinne.taxi.promocodeservice.controller.api;

import com.dulfinne.taxi.promocodeservice.dto.request.DiscountRequest;
import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.DiscountResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeResponse;
import com.dulfinne.taxi.promocodeservice.exception.ErrorResponse;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Pramocode Controller", description = "Managing promocodes")
public interface PromocodeApi {
  @Operation(
      operationId = "getAllPromocodes",
      summary = "Get all promocodes",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaginatedResponse.class))),
      })
  @GetMapping
  ResponseEntity<PaginatedResponse<PromocodeResponse>> getAllPromocodes(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "isActive") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder);

  @Operation(
      operationId = "getPromocodeByCode",
      summary = "Get promocode by code",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PromocodeResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Promocode not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/{code}")
  ResponseEntity<PromocodeResponse> getPromocodeByCode(@PathVariable String code);

  @Operation(
      operationId = "createPromocode",
      summary = "Create promocode",
      responses = {
        @ApiResponse(
            responseCode = "201",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PromocodeResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Code not unique",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping
  ResponseEntity<PromocodeResponse> createPromocode(@RequestBody @Valid PromocodeRequest request);

  @Operation(
      operationId = "updatePromocode",
      summary = "Update promocode",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PromocodeResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Promocode not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Conflicting update data",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PutMapping("/{code}")
  ResponseEntity<PromocodeResponse> updatePromocode(
      @PathVariable String code, @RequestBody @Valid PromocodeRequest request);

  @Operation(
      operationId = "getDiscount",
      summary = "Counts discount",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DiscountResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Promocode not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Can't use promocode",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping("/discount")
  ResponseEntity<DiscountResponse> getDiscount(@RequestBody @Valid DiscountRequest request);
}
