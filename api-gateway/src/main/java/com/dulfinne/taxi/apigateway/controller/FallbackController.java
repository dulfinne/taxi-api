package com.dulfinne.taxi.apigateway.controller;

import com.dulfinne.taxi.apigateway.dto.FallbackResponse;
import com.dulfinne.taxi.apigateway.util.FallbackMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

  @GetMapping(value = "/passenger")
  public FallbackResponse passengerServiceFallback() {
    return new FallbackResponse(
        HttpStatus.SERVICE_UNAVAILABLE, FallbackMessages.PASSENGER_SERVICE_DOWN);
  }

  @GetMapping(value = "/driver")
  public FallbackResponse driverServiceFallback() {
    return new FallbackResponse(
        HttpStatus.SERVICE_UNAVAILABLE, FallbackMessages.DRIVER_SERVICE_DOWN);
  }

  @GetMapping(value = "/ride")
  public FallbackResponse rideServiceFallback() {
    return new FallbackResponse(HttpStatus.SERVICE_UNAVAILABLE, FallbackMessages.RIDE_SERVICE_DOWN);
  }

  @GetMapping(value = "/auth")
  public FallbackResponse authServiceFallback() {
    return new FallbackResponse(HttpStatus.SERVICE_UNAVAILABLE, FallbackMessages.AUTH_SERVICE_DOWN);
  }

  @GetMapping(value = "/payment")
  public FallbackResponse paymentServiceFallback() {
    return new FallbackResponse(
        HttpStatus.SERVICE_UNAVAILABLE, FallbackMessages.PAYMENT_SERVICE_DOWN);
  }

  @GetMapping(value = "/promocode")
  public FallbackResponse promocodeServiceFallback() {
    return new FallbackResponse(
        HttpStatus.SERVICE_UNAVAILABLE, FallbackMessages.PROMOCODE_SERVICE_DOWN);
  }
}
