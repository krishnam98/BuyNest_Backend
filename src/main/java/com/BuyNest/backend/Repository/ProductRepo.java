package com.BuyNest.backend.Repository;

import com.BuyNest.backend.Model.Product;
import com.BuyNest.backend.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    @Query("SELECT p from Product p WHERE "+
    "LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%' )) OR " +
    "LOWER(p.description) LIKE LOWER(CONCAT('%',:keyword,'%' )) OR " +
    "LOWER(p.brand) LIKE LOWER(CONCAT('%',:keyword,'%' )) OR " +
    "LOWER(p.category) LIKE LOWER(CONCAT('%',:keyword,'%' ))")
    List<Product> searchProduct(String keyword);

    List<Product> findBySeller(Users user);
}
