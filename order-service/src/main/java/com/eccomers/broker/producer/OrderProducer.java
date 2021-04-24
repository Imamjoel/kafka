package com.eccomers.broker.producer;

import com.eccomers.broker.message.OrderMessage;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class OrderProducer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);
    
    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;
    
    private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage message) {
    	double surpriceBonus = StringUtils.startsWithIgnoreCase(message.getOrderLocation(), "A") ? 25 : 15 ;
    	
    	List<Header> headers =  new ArrayList<>();
    	var surpriceBonusHeader = new RecordHeader("surpriceBonus", Double.toString(surpriceBonus).getBytes());
    	headers.add(surpriceBonusHeader);
    	
    	return new ProducerRecord<String, OrderMessage>("t.commodity.order", null, message.getOrderNumber(), message, headers);
    	
	}

    public void publish(OrderMessage message) {
    	var producerRecord = buildProducerRecord(message);
    	
//        kafkaTemplate.send("t_commodity_order", message.getOrderNumber(), message)
        	kafkaTemplate.send(producerRecord)
                .addCallback(new ListenableFutureCallback<SendResult<String, OrderMessage>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        LOG.error("Order {}, Item {} failed to publish, because {}", message.getOrderNumber(), message.getItemName(), throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<String, OrderMessage> stringOrderMessageSendResult) {
                        LOG.info("Order {}, Item {} publisher successsfuly", message.getOrderNumber(), message.getItemName());
                    }
                });
        LOG.info("Just a dummy message order {}, Item {} publisher successsfuly", message.getOrderNumber(), message.getItemName());

    }
}
