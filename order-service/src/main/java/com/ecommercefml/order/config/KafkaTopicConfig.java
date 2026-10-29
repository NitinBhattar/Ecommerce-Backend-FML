package com.ecommercefml.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    // Spring register this returned object as bean,@Configuration only means, scan these methods as well
    // They maybe used to generate beans
    public NewTopic orderTopic() {
        return TopicBuilder
                .name("order-event")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
