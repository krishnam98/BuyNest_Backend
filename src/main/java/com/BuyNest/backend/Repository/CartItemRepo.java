package com.BuyNest.backend.Repository;

import com.BuyNest.backend.Model.Cart;
import com.BuyNest.backend.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    List<CartItem> findByCart(Cart cart);
}
