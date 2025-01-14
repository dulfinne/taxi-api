package com.dulfinne.taxi.driverservice.controller.api;

import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Rating Controller", description = "Interactions with driver ratings")
public interface DriverRatingApi {

  @Operation(
      operationId = "getAllDriverRatingsByUsername",
      summary = "Get driver ratings by username for ADMIN",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DriverRatingResponse.class))),
      })
  @GetMapping("/{username}/ratings")
  ResponseEntity<Page<DriverRatingResponse>> getAllDriverRatingsByUsername(
      @Parameter(name = "username") String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField);

  @Operation(
      operationId = "getAllDriverRatings",
      summary = "Get all drivers ratings for himself",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DriverRatingResponse.class))),
      })
  @GetMapping("/ratings")
  ResponseEntity<Page<DriverRatingResponse>> getAllDriverRatings(
      String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField);
}
