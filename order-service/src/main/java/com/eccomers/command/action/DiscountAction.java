package com.eccomers.command.action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eccomers.api.request.DiscountRequest;
import com.eccomers.broker.message.DiscountMessage;
import com.eccomers.broker.producer.DiscountProducer;

@Component
public class DiscountAction {

	@Autowired
	private DiscountProducer producer;

	public void publishToKafka(DiscountRequest request) {
		var message = new DiscountMessage(request.getDiscountCode(), request.getDiscountPercentage());
		producer.publish(message);
	}

}
