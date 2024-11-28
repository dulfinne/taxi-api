package com.dulfinne.taxi.driverservice.controller;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.service.DriverService;
import com.dulfinne.taxi.driverservice.util.TokenConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

  private final DriverService driverService;

  @GetMapping("/all")
  public ResponseEntity<Page<DriverResponse>> getAllDrivers(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(50) Integer limit,
      @RequestParam(value = "sort", defaultValue = "experience") String sortField) {

    Page<DriverResponse> driverResponsePage = driverService.getAllDrivers(offset, limit, sortField);
    return ResponseEntity.ok(driverResponsePage);
  }

  @GetMapping("/{username}")
  public ResponseEntity<DriverResponse> getDriverByUsername(@PathVariable String username) {
    DriverResponse driverResponse = driverService.getDriverByUsername(username);
    return ResponseEntity.ok(driverResponse);
  }

  @GetMapping
  public ResponseEntity<DriverResponse> getDriver(Principal principal) {
    DriverResponse driverResponse = driverService.getDriverByUsername(getUsername(principal));
    return ResponseEntity.ok(driverResponse);
  }

  @PostMapping
  public ResponseEntity<DriverResponse> saveDriver(
      Principal principal, @RequestBody @Valid DriverRequest driverRequest) {

    DriverResponse driverResponse = driverService.saveDriver(getUsername(principal), driverRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(driverResponse);
  }

  @PutMapping
  public ResponseEntity<DriverResponse> updateDriver(
      Principal principal, @RequestBody @Valid DriverRequest driverRequest) {

    DriverResponse driverResponse = driverService.updateDriver(getUsername(principal), driverRequest);
    return ResponseEntity.ok(driverResponse);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteDriver(Principal principal) {
    driverService.deleteDriver(getUsername(principal));
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{username}/assign-car/{carId}")
  public ResponseEntity<DriverResponse> assignCarToDriver(
      @PathVariable String username, @PathVariable Long carId) {

    DriverResponse driverResponse = driverService.assignCarToDriver(username, carId);
    return ResponseEntity.ok(driverResponse);
  }

  @PutMapping("/{username}/remove-car")
  public ResponseEntity<DriverResponse> removeCarFromDriver(@PathVariable String username) {
    DriverResponse driverResponse = driverService.removeCarFromDriver(username);
    return ResponseEntity.ok(driverResponse);
  }

  public String getUsername(Principal principal) {
    Authentication authentication = (Authentication) principal;
    Jwt jwt = (Jwt) authentication.getPrincipal();
    return jwt.getClaim(TokenConstants.USERNAME_CLAIM);
  }
}
