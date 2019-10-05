package com.shubh.kafkaconsumer.consumer;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaconsumer.entity.Commodity;

//@Service
public class CommodityDashboardConsumer {

	private ObjectMapper mapper = new ObjectMapper();
	
	private static final Logger log = LoggerFactory.getLogger(CommodityDashboardConsumer.class);
	
	@KafkaListener(topics = "t_comodity", groupId = "cg-notification")
	public void consume(String message) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		Commodity commodity = mapper.readValue(message, Commodity.class);
		Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
		log.info("Notification logic for {}", commodity);
	}
}
