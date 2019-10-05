package com.shubh.kafkaconsumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaconsumer.entity.Invoice;

@Service
public class InvoiceConsumer {

	private static final Logger log = LoggerFactory.getLogger(InvoiceConsumer.class);

	ObjectMapper mapper = new ObjectMapper();
	
	@KafkaListener(topics = "t_invoice", containerFactory = "invoiceDltContainerFactory")
	public void consume(String message) throws JsonParseException, JsonMappingException, IOException {

		Invoice invoice = mapper.readValue(message, Invoice.class);
		
		if (invoice.getAmount() < 1) {
			throw new IllegalArgumentException("Invalid amount :"+ invoice.getNumber());
		}
		
		log.info("Processing invoice : {}", invoice);
		
	}

}
