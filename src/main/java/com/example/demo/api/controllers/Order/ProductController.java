package com.example.demo.api.controllers.Order;

import com.example.demo.Model.Product;
import com.example.demo.Model.Repo.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
     List<Product> products = productRepository.findAll();
       return ResponseEntity.ok(products);
    }



}
