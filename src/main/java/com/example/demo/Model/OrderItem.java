package com.example.demo.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private double priceAtPurchase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomersOrder getOrder() {
        return order;
    }

    public void setOrder(CustomersOrder order) {
        this.order = order;
    }

    public OrderItem() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomersOrder order;

    public OrderItem(Long id, CustomersOrder order, Product product, int quantity, double priceAtPurchase) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }



    // getters and setters
}
