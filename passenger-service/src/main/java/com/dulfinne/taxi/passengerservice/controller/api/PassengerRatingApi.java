package com.dulfinne.taxi.passengerservice.controller.api;

import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.exception.ErrorResponse;
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

import java.util.List;

@Tag(name = "Rating Controller", description = "Interactions with passenger ratings")
public interface PassengerRatingApi {

  @Operation(
      operationId = "getAllPassengerRatingsByUsername",
      summary = "Get all ratings of passenger for ADMIN",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "passenger ratings",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PassengerRatingResponse.class))),
        @ApiResponse(
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/{username}/ratings")
  ResponseEntity<List<PassengerRatingResponse>> getAllPassengerRatingsByUsername(
      @PathVariable String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField);

  @Operation(
      operationId = "getAllPassengerRatings",
      summary = "Get all ratings of passenger for PASSENGER",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "passenger ratings",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PassengerRatingResponse.class))),
        @ApiResponse(
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/ratings")
  ResponseEntity<List<PassengerRatingResponse>> getAllPassengerRatings(
      String username,
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "rating") String sortField);
}
