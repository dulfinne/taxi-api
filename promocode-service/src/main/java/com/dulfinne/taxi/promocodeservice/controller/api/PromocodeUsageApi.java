package com.dulfinne.taxi.promocodeservice.controller.api;

import com.dulfinne.taxi.promocodeservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeUsageResponse;
import com.dulfinne.taxi.promocodeservice.exception.ErrorResponse;
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

@Tag(name = "Promocode usage Controller", description = "Work with promocode usages")
public interface PromocodeUsageApi {

  @Operation(
      operationId = "getPromocodeUsageById",
      summary = "Get usage by id",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PromocodeUsageResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "usage not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/{id}")
  ResponseEntity<PromocodeUsageResponse> getPromocodeUsageById(@PathVariable String id);

  @Operation(
      operationId = "getAllPromocodeUsages",
      summary = "Get all usages",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaginatedResponse.class))),
      })
  @GetMapping
  ResponseEntity<PaginatedResponse<PromocodeUsageResponse>> getAllPromocodeUsages(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "promocode") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder);

  @Operation(
      operationId = "getPromocodeUsagesByUsername",
      summary = "Get user's promocode usages",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaginatedResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/username/{username}")
  ResponseEntity<PaginatedResponse<PromocodeUsageResponse>> getPromocodeUsagesByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "usageDate") String sortField,
      @RequestParam(value = "order", defaultValue = "ASC") String sortOrder);
}
