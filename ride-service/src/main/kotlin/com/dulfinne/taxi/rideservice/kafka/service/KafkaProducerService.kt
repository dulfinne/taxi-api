package com.dulfinne.taxi.rideservice.kafka.service

import com.dulfinne.taxi.avro.Rating
import com.dulfinne.taxi.rideservice.kafka.config.KafkaProperties
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(val kafkaTemplate: KafkaTemplate<String, Any>, val kafkaProperties: KafkaProperties) {

    fun sendPassengersRating(rating: Rating) {
        kafkaTemplate.send(kafkaProperties.ratePassengerTopic, rating.username, rating)
    }

    fun sendDriversRating(rating: Rating) {
        kafkaTemplate.send(kafkaProperties.rateDriverTopic, rating.username, rating)
    }
}
