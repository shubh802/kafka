package com.shubh.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaproducer.producer.entity.FoodOrder;

@Service
public class FoodOrderProducer {
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public void sendMessage(FoodOrder foodOrder) throws JsonProcessingException {
		String json = mapper.writeValueAsString(foodOrder);
        kafkaTemplate.send("t_food_order", json);
	}

}
