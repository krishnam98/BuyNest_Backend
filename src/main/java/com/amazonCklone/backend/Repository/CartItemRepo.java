package com.amazonCklone.backend.Repository;

import com.amazonCklone.backend.Model.Cart;
import com.amazonCklone.backend.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    List<CartItem> findByCart(Cart cart);
}
