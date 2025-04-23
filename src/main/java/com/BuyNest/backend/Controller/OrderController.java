package com.BuyNest.backend.Controller;

import com.BuyNest.backend.Model.Address;
import com.BuyNest.backend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrders")
    public ResponseEntity<?> getOrders(Principal principal){
           return orderService.getOrders(principal);
    }

    @GetMapping("/getSellerOrders")
    public ResponseEntity<?> getSellerOrders(Principal principal){
        return orderService.getSellerOrders(principal);
    }



    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody Address address, Principal principal){
        return orderService.createOrder(address,principal);
    }
}
