package com.example.demo.Model;
import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cartItem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity = 1  ;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartItem(Product product, Cart cart , int quantity) {
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CartItem() {}


}