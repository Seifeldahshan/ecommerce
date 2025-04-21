package com.example.demo.Model.Repo;

import com.example.demo.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo  extends JpaRepository<CartItem, Long> {
  CartItem findById(long id);
}
