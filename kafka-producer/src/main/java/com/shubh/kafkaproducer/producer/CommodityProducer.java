package com.shubh.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaproducer.producer.entity.Commodity;
import com.shubh.kafkaproducer.producer.entity.Employee;

@Service
public class CommodityProducer {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public void sendMessage(Commodity commodity) throws JsonProcessingException {
		String json = objectMapper.writeValueAsString(commodity);
		kafkaTemplate.send("t_comodity", commodity.getName(), json);
	}
}
