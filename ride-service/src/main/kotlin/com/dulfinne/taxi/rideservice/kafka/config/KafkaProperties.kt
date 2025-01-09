package com.dulfinne.taxi.rideservice.kafka.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KafkaProperties {

    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var bootstrapServers: String

    @Value("\${spring.kafka.schema-registry.url}")
    lateinit var schemaRegistryUrl: String

    @Value("\${spring.kafka.topics.rate-passenger}")
    lateinit var ratePassengerTopic: String

    @Value("\${spring.kafka.topics.rate-driver}")
    lateinit var rateDriverTopic: String

    @Value("\${spring.kafka.topics.promocode-usage}")
    lateinit var promocodeUsageTopic: String

    @Value("\${spring.kafka.topics.ride-payment}")
    lateinit var ridePaymentTopic: String

    @Value("\${spring.kafka.partition-number}")
    var partitionNumber: Int = 1

    @Value("\${spring.kafka.replicas-number}")
    var replicasNumber: Int = 1
}