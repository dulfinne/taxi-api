package com.dulfinne.taxi.driverservice.controller.api;

import com.dulfinne.taxi.driverservice.dto.request.CarRequest;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
import com.dulfinne.taxi.driverservice.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Car Controller", description = "Interactions with cars")
public interface CarApi {

  @Operation(
      operationId = "getAllCars",
      summary = "Get all cars",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CarResponse.class))),
      })
  @GetMapping
  ResponseEntity<Page<CarResponse>> getAllCars(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "carCategory") String sortField);

  @Operation(
      operationId = "getCarById",
      summary = "Get car by id",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CarResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/{id}")
  ResponseEntity<CarResponse> getCarById(@PathVariable Long id);

  @Operation(
      operationId = "saveCar",
      summary = "Save car",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Car created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CarResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Registration number is not unique",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping
  ResponseEntity<CarResponse> saveCar(@RequestBody @Valid CarRequest carRequest);

  @Operation(
      operationId = "updateCar",
      summary = "Update Car",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CarResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Registration number is not unique",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PutMapping("/{id}")
  ResponseEntity<CarResponse> updateCar(
      @PathVariable Long id, @RequestBody @Valid CarRequest carRequest);

  @Operation(
      operationId = "deleteCar",
      summary = "Delete car by id",
      responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(
            responseCode = "404",
            description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteCar(@PathVariable Long id);
}
