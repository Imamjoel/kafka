package com.eccomers.command.service;

import com.eccomers.api.request.OrderRequest;
import com.eccomers.command.action.OrderAction;
import com.eccomers.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderAction orderAction;


    public String saveOrder(OrderRequest orderRequest) {

        Order order = orderAction.convertToOrder(orderRequest);

        orderAction.saveToDatabase(order);
        order.getOrderItem().forEach(orderAction::publishToKafka);
        return order.getOrderNumber();
    }
}
