package com.shubh.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaproducer.producer.entity.SimpleNumber;

@Service
public class SimpleNumberProducer {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	ObjectMapper mapper = new ObjectMapper();

	public void send(SimpleNumber simpleNumber) throws JsonProcessingException {
		String json = mapper.writeValueAsString(simpleNumber);
		kafkaTemplate.send("t_simple_number", json);
		
	}
}
