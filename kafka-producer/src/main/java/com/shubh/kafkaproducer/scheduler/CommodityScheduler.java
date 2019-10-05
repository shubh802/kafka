package com.shubh.kafkaproducer.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shubh.kafkaproducer.producer.CommodityProducer;
import com.shubh.kafkaproducer.producer.entity.Commodity;

@Service
public class CommodityScheduler {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private CommodityProducer producer;
	
	@Scheduled(fixedRate = 5000)
	public void fetchCommodities() {
		
	List<Commodity> commodities = (List<Commodity>) restTemplate.exchange("http://localhost:8080/api/commodity/v1/all",
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Commodity>>() {}).getBody();
	
	commodities.forEach( c-> {
		try {
			producer.sendMessage(c);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	});
	
	
	}

}
