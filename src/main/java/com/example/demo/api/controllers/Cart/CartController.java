package com.example.demo.api.controllers.Cart;

import com.example.demo.DTO.AddToCartRequest;
import com.example.demo.Model.Cart;
import com.example.demo.Model.CartItem;
import com.example.demo.Model.Product;
import com.example.demo.Model.Repo.CartItemRepo;
import com.example.demo.Model.Repo.CartRepo;
import com.example.demo.Model.Repo.ProductRepository;
import com.example.demo.Model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartItemRepo cartItemRepo;
    private ProductRepository productRepository;
    private CartRepo cartRepo;

    public CartController(CartItemRepo cartItemRepo , ProductRepository productRepository, CartRepo cartRepo) {
        this.cartItemRepo = cartItemRepo;
        this.productRepository = productRepository;
        this.cartRepo = cartRepo;
    }


    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@AuthenticationPrincipal User user, @RequestBody AddToCartRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product1 = optionalProduct.get();
        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.saveAndFlush(newCart);
                });

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product1);
        cartItem.setCart(cart);
        cartItem.setQuantity(request.getQuantity());

        cartItemRepo.save(cartItem);

        return ResponseEntity.ok("Product added to cart.");
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@AuthenticationPrincipal User user, @PathVariable Long cartItemId) {
        Optional<CartItem> cartItemOpt = cartItemRepo.findById(cartItemId);

        if (!cartItemOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.");
        }

        CartItem cartItem = cartItemOpt.get();
        Cart cart = cartItem.getCart();

        if (!cart.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to delete this item.");
        }

        cartItemRepo.delete(cartItem);

        if (cart.getItems().isEmpty()) {
            cartRepo.delete(cart);
        }

        return ResponseEntity.ok("Cart item removed successfully.");
    }
}
