package com.shubh.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaproducer.producer.entity.Invoice;

@Service
public class InvoiceProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public void send(Invoice invoice) throws JsonProcessingException {

		String json = mapper.writeValueAsString(invoice);
		kafkaTemplate.send("t_invoice", invoice.getNumber(), json);
		
	}
}
