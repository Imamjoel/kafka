package com.eccomers.broker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.eccomers.broker.message.DiscountMessage;

@Service
public class DiscountProducer {

	@Autowired
	private KafkaTemplate<String, DiscountMessage> kafkaTemplate;

	public void publish(DiscountMessage message) {
		kafkaTemplate.send("t.commodity.promotion", message);
	}

}