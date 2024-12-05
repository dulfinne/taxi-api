package com.dulfinne.taxi.driverservice.kafka.service;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.driverservice.service.DriverRatingService;
import com.dulfinne.taxi.driverservice.kafka.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final DriverRatingService ratingService;
  private final KafkaProperties kafkaProperties;

  @KafkaListener(
      topics = "#{kafkaProperties.rateDriverTopic}",
      groupId = "#{kafkaProperties.driverRatingGroup}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(Rating rating) {
    ratingService.saveDriverRating(rating);
  }
}
