package com.dulfinne.taxi.driverservice.kafka.config;

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

  @Value("${spring.kafka.groups.driver-rating}")
  private String driverRatingGroup;

  @Value("${spring.kafka.topics.rate-driver}")
  private String rateDriverTopic;

  @Value("${spring.kafka.consumers-number}")
  private Integer consumersNumber;
}
