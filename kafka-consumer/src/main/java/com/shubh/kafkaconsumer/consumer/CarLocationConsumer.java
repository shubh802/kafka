package com.shubh.kafkaconsumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaconsumer.entity.CarLocation;

//@Service
public class CarLocationConsumer {

	private static final Logger log = LoggerFactory.getLogger(CarLocationConsumer.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	@KafkaListener(topics = "t_location", groupId = "cg-all-location")
	public void listenAll(String message) throws JsonParseException, JsonMappingException, IOException {
		CarLocation carLocation = mapper.readValue(message, CarLocation.class);
		log.info("Listen All : {}", carLocation);
	}
	
	@KafkaListener(topics = "t_location", groupId = "cg-far-location", containerFactory = "farLocationContainerFactory")
	public void listenFar(String message) throws JsonParseException, JsonMappingException, IOException {
		CarLocation carLocation = mapper.readValue(message, CarLocation.class);
		
		
		
		log.info("Listen Far  : {}", carLocation);
	}
}
