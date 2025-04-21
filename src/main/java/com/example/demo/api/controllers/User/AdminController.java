package com.example.demo.api.controllers.User;

import com.example.demo.Model.CustomersOrder;
import com.example.demo.Model.Product;
import com.example.demo.Model.Repo.CustomerOrderRepo;
import com.example.demo.Model.Repo.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository productRepository;
    private final CustomerOrderRepo customerOrderRepo;

    public AdminController(ProductRepository productRepository, CustomerOrderRepo customerOrderRepo) {
        this.productRepository = productRepository;
        this.customerOrderRepo = customerOrderRepo;

    }

    @PostMapping("/add-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.ok("Product added.");
    }

    @DeleteMapping("/delete-product/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted.");
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CustomersOrder>> getAllOrders() {
        return ResponseEntity.ok(customerOrderRepo.findAll());
    }
}
