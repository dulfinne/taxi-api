package com.dulfinne.taxi.promocodeservice.kafka.config;

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

  @Value("${spring.kafka.groups.promocode-usage}")
  private String promocodeUsageGroup;

  @Value("${spring.kafka.topics.promocode-usage}")
  private String promocodeUsageTopic;

  @Value("${spring.kafka.consumers-number}")
  private Integer consumersNumber;

  @Value("${spring.kafka.error-handler.backoff.min-interval}")
  private long minIntervalBackoff;

  @Value("${spring.kafka.error-handler.backoff.max-interval}")
  private long maxIntervalBackoff;

  @Value("${spring.kafka.error-handler.backoff.multiplier}")
  private double multiplierBackoff;
}
