package com.eccomers.command.action;

import com.eccomers.api.request.OrderItemRequest;
import com.eccomers.api.request.OrderRequest;
import com.eccomers.broker.message.OrderMessage;
import com.eccomers.broker.producer.OrderProducer;
import com.eccomers.entity.Order;
import com.eccomers.entity.OrderItem;
import com.eccomers.repository.OrderItemRepository;
import com.eccomers.repository.OrderRepository;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Component
public class OrderAction {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderProducer producer;
    
    @Autowired
    private OrderRepository repository;

    public Order convertToOrder(OrderRequest orderRequest) {
        var result = new Order();

        result.setCreditCardNumber(orderRequest.getCreditCardNumber());
        result.setOrderLocation(orderRequest.getOrderLocation());
        result.setOrderDateTime(LocalDateTime.now());
        result.setOrderNumber(RandomString.hashOf(8).toUpperCase(Locale.ROOT));

        List<OrderItem> items = orderRequest.getItems().stream().map(this::convertToOrderItem).collect(Collectors.toList());
        items.forEach(item -> item.setOrder(result));
        result.setOrderItem(items);
        return result;
    }

    private OrderItem convertToOrderItem(OrderItemRequest request) {
    	var result = new OrderItem();
    	
    	result.setItemName(request.getItemName());
    	result.setPrice(request.getPrice());
    	result.setQuantitiy(request.getQuantity());
    	
    	return result;
    	
    }
    
    public void publishToKafka(OrderItem orderItem) {
    	var orderMessage = new OrderMessage();
    	
    	orderMessage.setItemName(orderItem.getItemName());
    	orderMessage.setPrice(orderItem.getPrice());
    	orderMessage.setQuantity(orderItem.getQuantitiy());
    	
    	orderMessage.setOrderDateTime(orderItem.getOrder().getOrderDateTime());
    	orderMessage.setOrderLocation(orderItem.getOrder().getOrderLocation());
    	orderMessage.setOrderNumber(orderItem.getOrder().getOrderNumber());
    	orderMessage.setCreditCardNumber(orderItem.getOrder().getCreditCardNumber());
    	
    	producer.publish(orderMessage);
    	
    }

    public void saveToDatabase(Order order) {
    	repository.save(order);
    	order.getOrderItem().forEach(orderItemRepository::save);
    }
}
