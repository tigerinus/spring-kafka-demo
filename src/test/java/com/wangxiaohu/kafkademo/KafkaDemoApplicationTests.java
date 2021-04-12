package com.wangxiaohu.kafkademo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangxiaohu.kafkademo.model.Greeting;
import com.wangxiaohu.kafkademo.service.KafkaPublishService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaDemoApplicationTests {

    @Autowired
    private KafkaPublishService _publishService;

	@Test
	void contextLoads() throws JsonProcessingException {
	}

    @Test
    void sendMessageTest() throws InterruptedException {
        Greeting greeting = Greeting.builder().name("World").message("Hello").build();
        Thread.sleep(5000);
        _publishService.sendMessage("hello", greeting);
    }
}
