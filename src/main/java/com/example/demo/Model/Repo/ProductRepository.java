package com.example.demo.Model.Repo;

import com.example.demo.Model.Product;
import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product, Long> {

}
