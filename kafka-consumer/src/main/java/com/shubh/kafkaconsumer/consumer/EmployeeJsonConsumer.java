package com.shubh.kafkaconsumer.consumer;

import java.io.IOException;

import org.apache.kafka.common.protocol.types.Field.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaconsumer.entity.Employee;

//@Service
public class EmployeeJsonConsumer {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeJsonConsumer.class);

	@KafkaListener(topics = "t_employee")
	public void consume(String message) throws JsonParseException, JsonMappingException, IOException {
		Employee emp = objectMapper.readValue(message, Employee.class);
		log.info("Employee is {} ",emp);
		
	}
}
