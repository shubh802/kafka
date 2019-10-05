package com.shubh.kafkaproducer.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.shubh.kafkaproducer.producer.entity.Invoice;

@Service
public class InvoiceService {

	private static int counter = 0;
	
	public Invoice generateInvoice() {
		counter++;
		String invoiceNumber = "INV-"+ counter;
		double invoiceAmount = ThreadLocalRandom.current().nextInt(1, 1000);
		
		return new Invoice(invoiceNumber, invoiceAmount, "USD");
	} 
}
