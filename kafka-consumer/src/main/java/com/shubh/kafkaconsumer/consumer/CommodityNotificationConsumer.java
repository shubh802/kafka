package com.shubh.kafkaconsumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaconsumer.entity.Commodity;

//@Service
public class CommodityNotificationConsumer {

	private ObjectMapper mapper = new ObjectMapper();
	
	private static final Logger log = LoggerFactory.getLogger(CommodityNotificationConsumer.class);
	
	@KafkaListener(topics = "t_comodity", groupId = "cg-dashboard")
	public void consume(String message) throws JsonParseException, JsonMappingException, IOException {
		Commodity commodity = mapper.readValue(message, Commodity.class);
		log.info("Dashbord logic for {}", commodity);
	}
}
