package com.shubh.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shubh.kafkaproducer.producer.EmployeeJsonProducer;
import com.shubh.kafkaproducer.producer.FoodOrderProducer;
import com.shubh.kafkaproducer.producer.HelloKafkaProducer;
import com.shubh.kafkaproducer.producer.ImageProducer;
import com.shubh.kafkaproducer.producer.InvoiceProducer;
import com.shubh.kafkaproducer.producer.KafkaKeyProducer;
import com.shubh.kafkaproducer.producer.SimpleNumberProducer;
import com.shubh.kafkaproducer.producer.entity.Invoice;
import com.shubh.kafkaproducer.service.ImageService;
import com.shubh.kafkaproducer.service.InvoiceService;

@SpringBootApplication
//@EnableScheduling
public class KafkaProducerApplication implements CommandLineRunner {
	
	@Autowired
	HelloKafkaProducer helloKafkaProduce;
	
	@Autowired
	KafkaKeyProducer producer;
	
	@Autowired
	EmployeeJsonProducer producerJson;
	
	@Autowired
	FoodOrderProducer producerFood;
	
	@Autowired
	private SimpleNumberProducer producerNumber;
	
	@Autowired
	private ImageProducer producerImage;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	InvoiceProducer producerInvoice;
	

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		helloKafkaProduce.sendHello("Joshi "+Math.random());
//		for (int i = 0; i < 1000; i++) {
//			String key = "key-"+(i%4);
//			String data = "data "+i+" with key "+key;
//			producer.send(key, data);
//		}
		
//		for (int i = 0; i < 5; i++) {
//			Employee emp = new Employee("emp-"+i, "Employee "+i, LocalDate.now());
//			producerJson.sendMessage(emp);
//		}
		
//		FoodOrder fo1 =new FoodOrder(3, "Chicken");
//		FoodOrder fo2 =new FoodOrder(10, "Fish");
//		FoodOrder fo3 =new FoodOrder(6, "Pizza");
//		
//		producerFood.sendMessage(fo1);
//		producerFood.sendMessage(fo2);
//		producerFood.sendMessage(fo3);
//		
////		number producer
//		
//		for (int i = 100; i < 103; i++) {
//			SimpleNumber number =new SimpleNumber(i);
//			producerNumber.send(number);
//		}
		
//		Image image1 = imageService.generateImage("jpg");
//		Image image2 = imageService.generateImage("svg");
//		Image image3 = imageService.generateImage("png");
//
//		producerImage.send(image1);
//		producerImage.send(image2);
//		producerImage.send(image3);
		
		
		for (int i = 0; i < 10; i++) {
			Invoice invoice = invoiceService.generateInvoice();
			
			if (i >= 5) {
				invoice.setAmount(-1);
			}
			producerInvoice.send(invoice);
		}
	}

}
