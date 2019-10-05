package com.shubh.kafkaconsumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaconsumer.entity.FoodOrder;

//@Service
public class FoodOrderConsumer {

	private static final Logger log = LoggerFactory.getLogger(FoodOrderConsumer.class);

	ObjectMapper mapper = new ObjectMapper();
	
	private static final int MAX_AMOUNT_ORDER = 7;
	
	@KafkaListener(topics = "t_food_order", errorHandler = "myFoodOrderErrorHandler")
	public void consume(String message) throws JsonParseException, JsonMappingException, IOException {

		FoodOrder foodOrder = mapper.readValue(message, FoodOrder.class);
		
		if (foodOrder.getAmount() > MAX_AMOUNT_ORDER) {
			throw new IllegalArgumentException("Food order amount is too many");
		}
		
		log.info("Food order valid : {}", foodOrder);
	}
	
}
