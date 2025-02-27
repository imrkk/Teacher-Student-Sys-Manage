package com.stu_teach.manage.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	
	@KafkaListener(topics = {"exam-date"},groupId="abc")
	public void messageConsumer(String message) {
		System.out.println(message);
	}

}
