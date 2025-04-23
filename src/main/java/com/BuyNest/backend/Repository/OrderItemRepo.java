package com.BuyNest.backend.Repository;

import com.BuyNest.backend.Model.OrderItem;
import com.BuyNest.backend.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findBySeller(Users seller);
}
