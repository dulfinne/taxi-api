package com.dulfinne.taxi.passengerservice.controller.api;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Passenger Controller", description = "Interactions with passenger")
public interface PassengerApi {

  @Operation(
      operationId = "getAllPassengers",
      summary = "Get all passengers",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PassengerResponse.class))),
        @ApiResponse(
            responseCode = "400",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  ResponseEntity<PaginatedResponse<PassengerResponse>> getAllPassengers(
      @RequestParam(value = "offset", defaultValue = "0")
          @Min(0)
          @Parameter(description = "Start Page")
          Integer offset,
      @RequestParam(value = "limit", defaultValue = "10")
          @Min(1)
          @Max(50)
          @Parameter(description = "Items per page")
          Integer limit,
      @RequestParam(value = "sort", defaultValue = "rideCount")
          @Parameter(description = "Field to sort by")
          String sortField);

  @Operation(
      operationId = "getPassengerByUsername",
      summary = "Get passenger by username",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PassengerResponse.class))),
        @ApiResponse(
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  ResponseEntity<PassengerResponse> getPassengerByUsername(
      @Parameter(name = "username", in = ParameterIn.PATH) @PathVariable String username);

  @Operation(
      operationId = "getPassenger",
      summary = "Get passenger by username for PASSENGER",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PassengerResponse.class))),
        @ApiResponse(
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  ResponseEntity<PassengerResponse> getPassenger(  String username);

  @Operation(
      operationId = "savePassenger",
      summary = "Save passenger",
      responses = {
        @ApiResponse(
            responseCode = "201",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PassengerResponse.class))),
        @ApiResponse(
            responseCode = "409",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  ResponseEntity<PassengerResponse> savePassenger(
       String username,
      @Parameter(name = "Passenger data") @RequestBody @Valid PassengerRequest passengerRequest);

  @Operation(
      operationId = "updatePassenger",
      summary = "Update passenger",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PassengerResponse.class))),
        @ApiResponse(
            responseCode = "409",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  ResponseEntity<PassengerResponse> updatePassenger(
       String username,
      @RequestBody @Valid PassengerRequest passengerRequest);

  @Operation(
      operationId = "deletePassenger",
      summary = "Delete passenger",
      responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  ResponseEntity<Void> deletePassenger( String username);
}
