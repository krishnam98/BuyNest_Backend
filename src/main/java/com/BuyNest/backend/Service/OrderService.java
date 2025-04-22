package com.BuyNest.backend.Service;

import com.BuyNest.backend.Model.*;
import com.BuyNest.backend.Repository.CartRepo;
import com.BuyNest.backend.Repository.OrderItemRepo;
import com.BuyNest.backend.Repository.OrderRepo;
import com.BuyNest.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private OrderRepo orderRepo;

    public ResponseEntity<?> createOrder(Address address, Principal principal) {
        Users user= userRepo.findByUsername(principal.getName());
        Cart cart = cartRepo.findByUser(user).orElse(null);
        if (cart!=null){
            Orders orders =new Orders();
            orders.setBuyer(user);
            BigDecimal price= BigDecimal.ZERO;
            cart.getCartItems().forEach(item -> {
                OrderItem orderItem= new OrderItem();
                orderItem.setProduct(item.getProducts());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSeller(item.getProducts().getSeller());
                orderItem.setOrders(orders);
                System.out.println(orderItem);
                orders.getOrderItems().add(orderItem);
            });
            for(int i = 0; i< cart.getCartItems().size(); i++){
                price= price.add(cart.getCartItems().get(i).getProducts().getPrice().multiply(BigDecimal.valueOf(cart.getCartItems().get(i).getQuantity())));
            }
            orders.setPrice(price);
            orders.setAddress(address);
            orders.setDateOfCreation(new Date());
            orderRepo.save(orders);
            return new ResponseEntity<>("Order Created successfully",HttpStatus.CREATED);

        }else{
            System.out.println("No cart present");
            return new ResponseEntity<>("No cart present!", HttpStatus.BAD_REQUEST);
        }




    }
    public ResponseEntity<?> getOrders(Principal principal){
        Users user = userRepo.findByUsername(principal.getName());
        List <Orders> orders=orderRepo.findByBuyer(user);
       if(orders!=null){
           return new ResponseEntity<>(orders,HttpStatus.OK);
       }
       else{
           return  new ResponseEntity<>("null",HttpStatus.NOT_FOUND);
       }

    }
}
