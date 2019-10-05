package com.shubh.kafkaconsumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaconsumer.entity.SimpleNumber;

//@Service
public class SimpleNumberConsumer {

	private static final Logger log = LoggerFactory.getLogger(SimpleNumberConsumer.class);

	private ObjectMapper mapper = new ObjectMapper();

	@KafkaListener(topics = "t_simple_number")
	public void consume(String message) throws JsonParseException, JsonMappingException, IOException {
		SimpleNumber simpleNumber = mapper.readValue(message, SimpleNumber.class);
		
		if (simpleNumber.getNumber() %2  !=0) {
			throw new IllegalArgumentException("Odd Number");
		}
		
		log.info("Valid number: {}", simpleNumber);
	}
}
