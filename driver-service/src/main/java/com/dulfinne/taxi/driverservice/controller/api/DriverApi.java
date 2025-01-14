package com.dulfinne.taxi.driverservice.controller.api;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.request.PointRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.dto.response.PointResponse;
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

@Tag(name = "Driver Controller", description = "Interactions with driver")
public interface DriverApi {
  @Operation(
      operationId = "getAllDrivers",
      summary = "Get all drivers",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DriverResponse.class))),
      })
  @GetMapping("/all")
  ResponseEntity<Page<DriverResponse>> getAllDrivers(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "experience") String sortField);

  @Operation(
      operationId = "getDriverByUsername",
      summary = "Get driver by username for ADMIN",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DriverResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Driver not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/{username}")
  ResponseEntity<DriverResponse> getDriverByUsername(@PathVariable String username);

  @Operation(
      operationId = "getDriver",
      summary = "Get driver by username for DRIVER",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DriverResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Driver not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping
  ResponseEntity<DriverResponse> getDriver(String username);

  @Operation(
      operationId = "saveDriver",
      summary = "Save driver",
      responses = {
        @ApiResponse(
            responseCode = "201",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DriverResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Not unique user data",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping
  ResponseEntity<DriverResponse> saveDriver(
      String username, @RequestBody @Valid DriverRequest driverRequest);

  @Operation(
      operationId = "updateDriver",
      summary = "Update driver",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DriverResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Driver not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Not unique phone number",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PutMapping
  ResponseEntity<DriverResponse> updateDriver(
      String username, @RequestBody @Valid DriverRequest driverRequest);

  @Operation(
      operationId = "deleteDriver",
      summary = "Delete driver",
      responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(
            responseCode = "404",
            description = "Driver not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @DeleteMapping
  ResponseEntity<Void> deleteDriver(String username);

  @Operation(
      operationId = "assignCarToDriver",
      summary = "Assign car to driver",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DriverResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Car or driver not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Car already assigned",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PutMapping("/{username}/assign-car/{carId}")
  ResponseEntity<DriverResponse> assignCarToDriver(
      @PathVariable String username, @PathVariable Long carId);

  @Operation(
      operationId = "removeCarFromDriver",
      summary = "Remove car from driver",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DriverResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Driver not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PutMapping("/{username}/remove-car")
  ResponseEntity<DriverResponse> removeCarFromDriver(@PathVariable String username);

  @Operation(
      operationId = "getDriverLocationByUsername",
      summary = "Get driver location by username for ADMIN",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PointResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Driver or his location is not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/{username}/location")
  ResponseEntity<PointResponse> getDriverLocationByUsername(@PathVariable String username);

  @Operation(
      operationId = "getDriverLocationByUsername",
      summary = "Get driver location for himself",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PointResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Driver or his location is not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @GetMapping("/location")
  ResponseEntity<PointResponse> getDriverLocation(String username);

  @Operation(
      operationId = "updateDriverLocation",
      summary = "Update driver's location",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PointResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Driver not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PutMapping("/location")
  ResponseEntity<PointResponse> updateDriverLocation(
      String username, @RequestBody PointRequest request);
}
