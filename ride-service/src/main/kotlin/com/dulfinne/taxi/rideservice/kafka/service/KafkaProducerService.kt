package com.dulfinne.taxi.rideservice.kafka.service

import com.dulfinne.taxi.avro.PaymentRequest
import com.dulfinne.taxi.avro.PromocodeUsageRequest
import com.dulfinne.taxi.avro.Rating
import com.dulfinne.taxi.rideservice.kafka.config.KafkaProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(val kafkaTemplate: KafkaTemplate<String, Any>, val kafkaProperties: KafkaProperties) {
    private val log: Logger = LoggerFactory.getLogger(KafkaProducerService::class.java)

    fun sendPassengersRating(rating: Rating) {
        log.info("Sending passenger's rating. Started. Username = ${rating.username}")
        kafkaTemplate.send(kafkaProperties.ratePassengerTopic, rating.username, rating)
    }

    fun sendDriversRating(rating: Rating) {
        log.info("Sending driver's rating. Started. Username = ${rating.username}")
        kafkaTemplate.send(kafkaProperties.rateDriverTopic, rating.username, rating)
    }

    fun sendPromocodeUsage(usage: PromocodeUsageRequest) {
        log.info("Sending promocode usage request. Started. Ride id = ${usage.rideId}")
        kafkaTemplate.send(kafkaProperties.promocodeUsageTopic, usage.username, usage)
    }

    fun sendRidePayment(payment: PaymentRequest) {
        log.info("Sending ride payment request. Started. Ride id = ${payment.rideId}")
        kafkaTemplate.send(kafkaProperties.ridePaymentTopic, payment.passengerUsername, payment)
    }
}
