package com.dulfinne.taxi.paymentservice.service;

import com.dulfinne.taxi.avro.PaymentRequest;

public interface PaymentService {
  void handleRidePayment(PaymentRequest paymentRequest);
}
