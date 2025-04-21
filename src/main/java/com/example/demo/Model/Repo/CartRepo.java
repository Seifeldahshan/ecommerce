package com.example.demo.Model.Repo;

import com.example.demo.Model.Cart;
import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}
