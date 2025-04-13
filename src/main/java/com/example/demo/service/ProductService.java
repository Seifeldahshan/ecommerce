package com.example.demo.service;

import com.example.demo.Model.Product;
import com.example.demo.Model.Repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct(){
        return  productRepository.findAll();

    }
}
