package com.dulfinne.taxi.paymentservice.service;

import com.dulfinne.taxi.paymentservice.dto.request.PaymentRequest;

public interface PaymentService {
  void handleRidePayment(PaymentRequest paymentRequest);
}
