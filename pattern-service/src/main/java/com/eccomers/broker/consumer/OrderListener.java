package com.eccomers.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eccomers.broker.message.OrderMessage;

@Service
public class OrderListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderListener.class);
	
	
	@KafkaListener(topics = "t_commodity_order")
	public void listener(OrderMessage message) {
		var totalItemAmount = message.getPrice() * message.getQuantity();
		
		LOG.info("proccessing order {}, item {}, credit card number {}, total amount for this item is {}", message.getOrderNumber(), message.getItemName(), message.getCreditCardNumber()
				, totalItemAmount);
		
	
		
		
	}
}
