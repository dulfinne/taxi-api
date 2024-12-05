package com.dulfinne.taxi.passengerservice.kafka.service;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.passengerservice.kafka.config.KafkaProperties;
import com.dulfinne.taxi.passengerservice.service.PassengerRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final PassengerRatingService ratingService;
  private final KafkaProperties kafkaProperties;

  @KafkaListener(
      topics = "#{kafkaProperties.ratePassengerTopic}",
      groupId = "#{kafkaProperties.passengerRatingGroup}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(Rating rating) {
    ratingService.savePassengerRating(rating);
  }
}
