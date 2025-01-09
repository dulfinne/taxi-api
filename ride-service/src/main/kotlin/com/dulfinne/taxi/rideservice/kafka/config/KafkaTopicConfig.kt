package com.dulfinne.taxi.rideservice.kafka.config

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig(private val kafkaProperties: KafkaProperties) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val props = HashMap<String, Any>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        return KafkaAdmin(props)
    }

    @Bean
    fun ratePassengerTopic(): NewTopic {
        return TopicBuilder.name(kafkaProperties.ratePassengerTopic)
            .partitions(kafkaProperties.partitionNumber)
            .replicas(kafkaProperties.replicasNumber)
            .build()
    }

    @Bean
    fun rateDriverTopic(): NewTopic {
        return TopicBuilder.name(kafkaProperties.rateDriverTopic)
            .partitions(kafkaProperties.partitionNumber)
            .replicas(kafkaProperties.replicasNumber)
            .build()
    }

    @Bean
    fun promocodeUsageTopic(): NewTopic {
        return TopicBuilder.name(kafkaProperties.promocodeUsageTopic)
            .partitions(kafkaProperties.partitionNumber)
            .replicas(kafkaProperties.replicasNumber)
            .build()
    }

    @Bean
    fun ridePaymentTopic(): NewTopic {
        return TopicBuilder.name(kafkaProperties.ridePaymentTopic)
            .partitions(kafkaProperties.partitionNumber)
            .replicas(kafkaProperties.replicasNumber)
            .build()
    }
}