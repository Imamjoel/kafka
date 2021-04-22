package com.eccomers.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eccomers.broker.message.DiscountMessage;
import com.eccomers.broker.message.PromotionMessage;

@Service
@KafkaListener(topics = "t_commodity_promotion")
public class PromotionListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionListener.class);
	
	@KafkaHandler
	public void listenPromotion(PromotionMessage message) {
		LOGGER.info("Processing promotion : {} ", message);
	}
	
	@KafkaHandler
	public void listenDiscount(DiscountMessage message) {
		LOGGER.info("Processing discount : {}", message);
	}
	
	
			
			

}
