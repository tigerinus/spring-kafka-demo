package com.wangxiaohu.kafkademo.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wangxiaohu.kafkademo.model.Greeting;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private List<String> _bootstrapServers;

    @Bean
    public ConsumerFactory<String, Greeting> consumerFactory() {

        Map<String, Object> configurationMap = new HashMap<>();
        configurationMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", _bootstrapServers));
        configurationMap.put(ConsumerConfig.GROUP_ID_CONFIG, "MainConsumer");
        configurationMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        configurationMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configurationMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configurationMap.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        configurationMap.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
        configurationMap.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        configurationMap.put(JsonDeserializer.KEY_DEFAULT_TYPE, String.class);
        configurationMap.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Greeting.class);

        return new DefaultKafkaConsumerFactory<>(configurationMap);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Greeting> listenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Greeting> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
