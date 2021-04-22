package com.eccomers.broker.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eccomers.broker.message.OrderMessage;

//@Service
public class OrderListener {

	private static final Logger LOG = LoggerFactory.getLogger(OrderListener.class);
	
	@KafkaListener(topics = "t_commodity_order")
	public void listen(ConsumerRecord<String, OrderMessage> consumerRecord) {
		var headers = consumerRecord.headers();
		var orderMessage = consumerRecord.value();
		
		LOG.info("Processing order {}, item {}, credit card number {}", orderMessage.getCreditCardNumber(),
				orderMessage.getItemName(), orderMessage.getCreditCardNumber());
		
		LOG.info("Header are :");
		headers.forEach(h -> LOG.info(" key : {}, value : {}", h.key(), new String(h.value())));
		
		var bonusPercentage = Double.parseDouble(new String(headers.lastHeader("surpriceBonus").value()));
		var bonusAmount = (bonusPercentage / 100) * orderMessage.getPrice() * orderMessage.getQuantity();
		
		LOG.info("Surprice Bonus is {}", bonusAmount);
	}
}
