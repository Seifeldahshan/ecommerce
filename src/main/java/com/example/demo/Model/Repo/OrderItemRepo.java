package com.example.demo.Model.Repo;

import com.example.demo.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
}
