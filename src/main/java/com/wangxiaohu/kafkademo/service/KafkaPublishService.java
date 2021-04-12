package com.wangxiaohu.kafkademo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangxiaohu.kafkademo.model.Greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaPublishService {

    @Autowired
    private KafkaTemplate<String, Greeting> _kafkaTemplate;

    private ObjectMapper _objectMapper;

    public void sendMessage(String key, Greeting value) {

        ListenableFuture<SendResult<String, Greeting>> future = _kafkaTemplate.send("demo", key, value);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Greeting>>() {

            @Override
            public void onSuccess(SendResult<String, Greeting> result) {
                String json = "(message could not be serialized)";
                try {
                    json = _objectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                log.info("Sent message=[" + json + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[" + value + "] due to : " + ex.getMessage());
            }
        });
    }
}
