package com.wangxiaohu.kafkademo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangxiaohu.kafkademo.model.Greeting;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumeService {

    private final static ObjectMapper _objectMapper = new ObjectMapper();

    @KafkaListener(topics = "demo", groupId = "MainConsumer", containerFactory = "listenerContainerFactory")
    public void onMessage(Greeting greeting) {
        if (null == greeting) {
            log.error("greeting is null");
            return;
        }

        String output;
        try {
            output = _objectMapper.writeValueAsString(greeting);
        } catch (JsonProcessingException e) {
            log.warn("unable to serialize greeting object", e);
            return;
        }
        log.info(output);
    }
}
