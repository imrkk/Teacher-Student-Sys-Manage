package com.stu_teach.manage.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class KafkaProducer {
	
	private static final String EXAM_DATE = "exam-date";
	
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	@PostMapping("/send")
	public String sendMessageToStudent(@RequestParam String message) {
		kafkaTemplate.send(EXAM_DATE,message);
		return "message sent successfully!!";
	}

}
