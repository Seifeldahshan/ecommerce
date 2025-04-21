package com.example.demo.Model.Repo;

import com.example.demo.Model.CustomersOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepo extends JpaRepository<CustomersOrder , Long> {
}
