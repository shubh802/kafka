package com.shubh.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaproducer.producer.entity.CarLocation;

@Service
public class CarLocationProducer {
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public void send(CarLocation carLocation) throws JsonProcessingException {
		String json = mapper.writeValueAsString(carLocation);
		kafkaTemplate.send("t_location", carLocation.getCarId(), json);
	}

}
