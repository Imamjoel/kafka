package com.eccomers.command.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eccomers.api.request.PromotionRequest;
import com.eccomers.broker.message.PromotionMessage;
import com.eccomers.broker.producer.PromotionProducer;

@Component
public class PromotionAction {

	@Autowired
	private PromotionProducer producer;
	
	public void publishToKafka(PromotionRequest requset) {
		// TODO Auto-generated method stub
		var message = new PromotionMessage(requset.getPromotionCode());
		
		producer.publish(message);
		
	}

}
