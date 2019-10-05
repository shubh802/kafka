package com.shubh.kafkaconsumer.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaconsumer.entity.Image;

//@Service
public class ImageConsumer {

	private static final Logger log = LoggerFactory.getLogger(ImageConsumer.class);

	ObjectMapper mapper = new ObjectMapper();
	
	@KafkaListener(topics = "t_image", containerFactory = "imageRetryContainerFactory")
	public void consume(String message) throws JsonParseException, JsonMappingException, IOException, TimeoutException {

		Image image = mapper.readValue(message, Image.class);
		
		if (image.getType().equalsIgnoreCase("svg")) {
			throw new TimeoutException("Failed API call");
		}
		log.info("Processing image: {}",image);
		
	} 

}
