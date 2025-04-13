package com.example.demo.service;

import com.example.demo.Model.CustomersOrder;
import com.example.demo.Model.Repo.OrderRepository;
import com.example.demo.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List <CustomersOrder> getOrder(User user) {
        return orderRepository.findByUser(user);
    }
}
