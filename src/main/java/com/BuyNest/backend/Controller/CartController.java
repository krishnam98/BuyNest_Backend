package com.BuyNest.backend.Controller;

import com.BuyNest.backend.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/getCartItems")
    public ResponseEntity<?> getCartItems(Principal principal){
       return cartService.getCartItems(principal.getName());
    }

    @PostMapping("/add/{productId}")
    public void addToCart(@PathVariable  Long productId, Principal principal){
        cartService.addToCart(productId,principal.getName());
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long productId, Principal principal){
       return cartService.deleteItem(productId,principal);
    }
}
