package com.shubh.kafkaproducer.config;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {
	
	@Autowired
	KafkaProperties kafkaProperties;
	
	@Bean
	public ProducerFactory<String, String> producerFactory(){
		Map<String, Object> properties = kafkaProperties.buildProducerProperties();
		properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, "180000");
		
		return new DefaultKafkaProducerFactory<String, String>(properties);
	}
	
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(){
		return new KafkaTemplate<String, String>(producerFactory());
	}

}
