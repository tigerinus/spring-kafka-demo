package com.wangxiaohu.kafkademo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaAdminConfiguration {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configMap);
    }

    @Bean
    public NewTopic demoTopic() {
        return new NewTopic("demo", 1, (short) 1);
    }
}