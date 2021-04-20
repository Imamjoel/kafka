package com.eccomers.broker.producer;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.eccomers.broker.message.PromotionMessage;

@Service
public class PromotionProducer {
	
	private static final Logger LOG = LoggerFactory.getLogger(PromotionProducer.class);
	
	@Autowired
	private KafkaTemplate<String, PromotionMessage> kafkaTemplate;
	
	public void publish(PromotionMessage message) {
		try {
			var sendResult = kafkaTemplate.send("t_commodity_promotion", message).get();
			
			LOG.info("send result success for message {}", sendResult.getProducerRecord().value());
		} catch (InterruptedException | ExecutionException  e) {
			// TODO: handle exception
			LOG.error("error publishing {}, cause {}", message, e.getMessage());
		}
		
		
	}
	
}
