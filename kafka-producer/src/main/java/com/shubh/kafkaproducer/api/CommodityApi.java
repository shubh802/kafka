package com.shubh.kafkaproducer.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.kafkaproducer.producer.entity.Commodity;
import com.shubh.kafkaproducer.service.CommodityService;

@RestController
@RequestMapping("/api/commodity/v1")
public class CommodityApi {
	
	@Autowired
	CommodityService commodityService;
	
	@GetMapping(value="/all")
	public List<Commodity> generateCommodities() {
		return commodityService.createDummyCommodities();
	}

}
