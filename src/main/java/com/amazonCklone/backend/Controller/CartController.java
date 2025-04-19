package com.amazonCklone.backend.Controller;

import com.amazonCklone.backend.Model.CartItemDTO;
import com.amazonCklone.backend.Model.Product;
import com.amazonCklone.backend.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
}
