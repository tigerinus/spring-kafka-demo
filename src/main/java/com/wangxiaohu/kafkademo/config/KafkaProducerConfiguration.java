package com.wangxiaohu.kafkademo.config;

import java.util.HashMap;
import java.util.Map;

import com.wangxiaohu.kafkademo.model.Greeting;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfiguration {
    
    @Value("${spring.kafka.bootstrap-servers}")
    private String _bootstrapServers;

    @Bean
    public ProducerFactory<String, Greeting> producerFactory() {

        Map<String, Object> configurationMap = new HashMap<>();

        configurationMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, _bootstrapServers);
        configurationMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurationMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configurationMap);
    }

    @Bean
    public KafkaTemplate<String, Greeting> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
