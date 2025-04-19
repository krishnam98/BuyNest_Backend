package com.BuyNest.backend.Service;

import com.BuyNest.backend.Model.AuthResponse;
import com.BuyNest.backend.Model.Users;
import com.BuyNest.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);


    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public AuthResponse verify(Users user) {
        Authentication authentication=
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));


        if(authentication.isAuthenticated()){
            String token= jwtService.generateToken(user);
            String role= authentication.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("SELLER");

            return new AuthResponse(token,role);
        }

        throw new BadCredentialsException("Invalid username or password");


    }
}
