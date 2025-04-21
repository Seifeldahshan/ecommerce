package com.example.demo.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

        @Id
        @GeneratedValue
        private Long id;

        @OneToOne
        private User user;

        @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE, orphanRemoval = true)
        private List<CartItem> items = new ArrayList<>();

        public Cart() {}

    public Cart(Long id, User user, List<CartItem> items) {
        this.id = id;
        this.user = user;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}



