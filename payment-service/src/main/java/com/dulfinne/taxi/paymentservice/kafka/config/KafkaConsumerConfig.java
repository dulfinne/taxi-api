package com.dulfinne.taxi.paymentservice.kafka.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerConfig {

  private final KafkaProperties kafkaProperties;

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
    props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
    props.put("schema.registry.url", kafkaProperties.getSchemaRegistryUrl());
    props.put(
        ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,
        CooperativeStickyAssignor.class.getName());

    return props;
  }

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setConcurrency(kafkaProperties.getConsumersNumber());
    factory.setCommonErrorHandler(errorHandler());
    return factory;
  }

  @Bean
  public DefaultErrorHandler errorHandler() {
    ExponentialBackOff backOff = new ExponentialBackOff();
    backOff.setInitialInterval(kafkaProperties.getMinIntervalBackoff());
    backOff.setMaxInterval(kafkaProperties.getMaxIntervalBackoff());
    backOff.setMultiplier(kafkaProperties.getMultiplierBackoff());

    DefaultErrorHandler errorHandler =
        new DefaultErrorHandler(
            (record, ex) -> {
              // TODO: Sending to DLT topic
              log.error("Message failed after retries, sending to DLT: {}", ex.getMessage());
            },
            backOff);

    return errorHandler;
  }
}
