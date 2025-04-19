package com.amazonCklone.backend.Controller;

import com.amazonCklone.backend.Model.AuthResponse;
import com.amazonCklone.backend.Model.Users;
import com.amazonCklone.backend.Service.UserService;
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
