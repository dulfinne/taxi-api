package com.dulfinne.taxi.passengerservice.kafka.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class KafkaProperties {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.schema-registry.url}")
    private String schemaRegistryUrl;

    @Value("${spring.kafka.groups.passenger-rating}")
    private String passengerRatingGroup;

    @Value("${spring.kafka.topics.rate-passenger}")
    private String ratePassengerTopic;

    @Value("${spring.kafka.consumers-number}")
    private Integer consumersNumber;

    @Value("${spring.kafka.error-handler.backoff.min-interval}")
    private long minIntervalBackoff;

    @Value("${spring.kafka.error-handler.backoff.max-interval}")
    private long maxIntervalBackoff;

    @Value("${spring.kafka.error-handler.backoff.multiplier}")
    private double multiplierBackoff;
}
