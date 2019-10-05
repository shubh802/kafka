package com.shubh.kafkaproducer.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.shubh.kafkaproducer.producer.entity.Image;

@Service
public class ImageService {

	private static int counter =0;
	
	public Image generateImage(String type) {
		counter++;
		String name = "image-"+counter;
		long size = ThreadLocalRandom.current().nextLong(100, 10_000);
		
		return new Image(name, size, type);
	}
}
