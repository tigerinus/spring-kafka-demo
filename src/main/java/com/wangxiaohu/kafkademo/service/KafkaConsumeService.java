package com.wangxiaohu.kafkademo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangxiaohu.kafkademo.model.Greeting;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumeService {

    private final static ObjectMapper _objectMapper = new ObjectMapper();

    @KafkaListener(topics = "demo", groupId = "MainConsumer", containerFactory = "listenerContainerFactory")
    public void onMessage(ConsumerRecord<String, Greeting> record, Acknowledgment acknowledgment) {
        if (null == record) {
            log.error("record is null");
            return;
        }

        Greeting greeting = record.value();
        String output;
        try {
            output = _objectMapper.writeValueAsString(greeting);
        } catch (JsonProcessingException e) {
            log.warn("unable to serialize greeting object", e);
            return;
        }

        acknowledgment.acknowledge();
        log.info(output);
    }
}
