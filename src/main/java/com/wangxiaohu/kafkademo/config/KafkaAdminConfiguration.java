package com.wangxiaohu.kafkademo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaAdminConfiguration {

    private int numPartitions = 2;

    @Bean
    public NewTopic demoTopic() {
        return TopicBuilder.name("demo").partitions(numPartitions).replicas(1).build();
    }
}
