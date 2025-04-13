package com.example.demo.api.controllers.Order;

import com.example.demo.Model.CustomersOrder;
import com.example.demo.Model.User;
import com.example.demo.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private  OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public List<CustomersOrder> getOrders(@AuthenticationPrincipal User user) {
        return orderService.getOrder(user);
    }

}
