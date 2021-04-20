package com.eccomers.broker.producer;

import com.eccomers.broker.message.OrderMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class OrderProducer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);
    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void publish(OrderMessage message) {
        kafkaTemplate.send("t_commodity_order", message.getOrderNumber(), message)
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
