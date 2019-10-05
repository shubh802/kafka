package com.shubh.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaproducer.producer.entity.Image;

@Service
public class ImageProducer {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public void send(Image image) throws JsonProcessingException {

		String json = mapper.writeValueAsString(image);
		kafkaTemplate.send("t_image", image.getType(), json);
	}
	
}
