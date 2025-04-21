package com.example.demo.DTO;

public class AddToCartRequest {

    long ProductId;
    int quantity;
    public AddToCartRequest(long productId, int quantity) {
        ProductId = productId;
        quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        quantity = quantity;
    }

    public long getProductId() {
        return ProductId;
    }

    public void setProductId(long productId) {
        ProductId = productId;
    }
}
