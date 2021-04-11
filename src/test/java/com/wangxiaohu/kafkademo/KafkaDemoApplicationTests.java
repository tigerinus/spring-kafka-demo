package com.wangxiaohu.kafkademo;

import com.wangxiaohu.kafkademo.service.KafkaPublishService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaDemoApplicationTests {

    @Autowired
    private KafkaPublishService _publishService;

	@Test
	void contextLoads() {
	}

    @Test
    void sendMessageTest() {
        _publishService.sendMessage("hello", "world");
    }
}
