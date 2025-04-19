package com.amazonCklone.backend.Repository;

import com.amazonCklone.backend.Model.Cart;
import com.amazonCklone.backend.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser(Users user);
}
