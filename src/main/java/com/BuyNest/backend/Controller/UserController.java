package com.BuyNest.backend.Controller;

import com.BuyNest.backend.Model.AuthResponse;
import com.BuyNest.backend.Model.Users;
import com.BuyNest.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return service.register(user);

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody Users user){
        return service.verify(user);
    }
}
