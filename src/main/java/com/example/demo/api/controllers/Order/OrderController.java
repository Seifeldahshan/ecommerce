package com.example.demo.api.controllers.Order;

import com.example.demo.Model.*;
import com.example.demo.Model.Repo.CartItemRepo;
import com.example.demo.Model.Repo.CartRepo;
import com.example.demo.Model.Repo.CustomerOrderRepo;
import com.example.demo.Model.Repo.OrderItemRepo;
import com.example.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    private CartRepo cartRepo;
    private CustomerOrderRepo customerOrderRepo;
    private OrderItemRepo orderItemRepo;
    private CartItemRepo cartItemRepo;
    private  OrderService orderService;

    public OrderController(CartRepo cartRepo, CustomerOrderRepo customerOrderRepo, OrderItemRepo orderItemRepo, CartItemRepo cartItemRepo, OrderService orderService) {
        this.cartRepo = cartRepo;
        this.customerOrderRepo = customerOrderRepo;
        this.orderItemRepo = orderItemRepo;
        this.cartItemRepo = cartItemRepo;
        this.orderService = orderService;
    }

    @GetMapping
    public List<CustomersOrder> getOrders(@AuthenticationPrincipal User user) {
        return orderService.getOrder(user);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@AuthenticationPrincipal User user) {
        Optional<Cart> optionalCart = cartRepo.findByUser(user);

        if (!optionalCart.isPresent() || optionalCart.get().getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        Cart cart = optionalCart.get();

        CustomersOrder order = new CustomersOrder();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        customerOrderRepo.save(order);

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice()); // snapshot price
            orderItem.setOrder(order);
            orderItemRepo.save(orderItem);
        }


        cartItemRepo.deleteAll(cart.getItems());
        cartRepo.delete(cart);

        return ResponseEntity.ok("Order placed successfully");
    }


}
