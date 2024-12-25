package com.dulfinne.taxi.promocodeservice.kafka.service;


import com.dulfinne.taxi.avro.PromocodeUsageRequest;
import com.dulfinne.taxi.promocodeservice.kafka.config.KafkaProperties;
import com.dulfinne.taxi.promocodeservice.service.PromocodeUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final PromocodeUsageService usageService;
  private final KafkaProperties kafkaProperties;

  @KafkaListener(
      topics = "#{kafkaProperties.promocodeUsageTopic}",
      groupId = "#{kafkaProperties.promocodeUsageGroup}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(PromocodeUsageRequest request) {
      usageService.createPromocodeUsage(request);
  }
}
