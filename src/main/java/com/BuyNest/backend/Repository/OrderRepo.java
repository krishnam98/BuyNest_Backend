package com.BuyNest.backend.Repository;

import com.BuyNest.backend.Model.Orders;
import com.BuyNest.backend.Model.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Orders,Long> {
    List<Orders> findByBuyer(Users buyer);
}
