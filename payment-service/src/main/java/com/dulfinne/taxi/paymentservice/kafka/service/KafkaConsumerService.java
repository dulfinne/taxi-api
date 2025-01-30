package com.dulfinne.taxi.paymentservice.kafka.service;

import com.dulfinne.taxi.avro.PaymentRequest;
import com.dulfinne.taxi.paymentservice.kafka.config.KafkaProperties;
import com.dulfinne.taxi.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

  private final PaymentService paymentService;
  private final KafkaProperties kafkaProperties;

  @KafkaListener(
      topics = "#{kafkaProperties.ridePaymentTopic}",
      groupId = "#{kafkaProperties.ridePaymentGroup}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(PaymentRequest request) {
    log.info("Received payment request. Handling. Ride id = {}", request.getRideId());
    paymentService.handleRidePayment(request);
  }
}
