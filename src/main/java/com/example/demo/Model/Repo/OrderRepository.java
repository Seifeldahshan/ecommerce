package com.example.demo.Model.Repo;

import com.example.demo.Model.CustomersOrder;
import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<CustomersOrder, Integer> {

    List<CustomersOrder> findByUser(User user);
}
