package com.BuyNest.backend.Repository;

import com.BuyNest.backend.Model.Cart;
import com.BuyNest.backend.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser(Users user);
}
