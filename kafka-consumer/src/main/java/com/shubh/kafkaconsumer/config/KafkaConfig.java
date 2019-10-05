package com.shubh.kafkaconsumer.config;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.kafkaconsumer.entity.CarLocation;
import com.shubh.kafkaconsumer.error.handler.GlobalErrorHandler;

@Configuration
public class KafkaConfig {

	@Autowired
	KafkaProperties kafkaProperties;

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory(){
		Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
		properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "120000");

		return new DefaultKafkaConsumerFactory<Object, Object>(properties);
	}

	@Bean(name= "farLocationContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer){

		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = 
				new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory,  consumerFactory());
		// To add filter
		factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {

			ObjectMapper mapper = new ObjectMapper();

			@Override
			public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {

				try {
					CarLocation carLocation = mapper.readValue(consumerRecord.value().toString(), CarLocation.class);
					return carLocation.getDistance() <=100 ;
				} catch (IOException e) {
					return false;
				}
			}
		});

		return factory;
	}

	@Bean(name= "kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = 
				new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory,  consumerFactory());
		//  To set global error handler
		factory.setErrorHandler(new GlobalErrorHandler());

		return factory;
	}

	private RetryTemplate createRetryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();
		//It will retry for 3 times
		RetryPolicy retryPolicy = new SimpleRetryPolicy(3);
		//Retry after 10 sec
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(10_000);

		retryTemplate.setRetryPolicy(retryPolicy);
		retryTemplate.setBackOffPolicy(backOffPolicy);

		return retryTemplate;
	}

	@Bean(name= "imageRetryContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> imageRetryContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = 
				new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory,  consumerFactory());

		factory.setErrorHandler(new GlobalErrorHandler());
		factory.setRetryTemplate(createRetryTemplate());

		return factory;
	}

	@Bean(name = "invoiceDltContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> invoiceDltContinerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			KafkaTemplate<Object, Object> kafkaTemplate){

		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = 
				new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());

		DeadLetterPublishingRecoverer recoverer = 
				new DeadLetterPublishingRecoverer(kafkaTemplate, (
						(record, ex) -> new TopicPartition("t_invoice_dlt", record.partition())));

		factory.getContainerProperties().setAckOnError(false);
//		Check for 5 attempts bfr sending to new topic
		SeekToCurrentErrorHandler errorHandler = new SeekToCurrentErrorHandler(recoverer, 5);
		factory.setErrorHandler(errorHandler);
		
		return factory;
	}

}
