package com.BuyNest.backend.Service;

import com.BuyNest.backend.Model.AuthResponse;
import com.BuyNest.backend.Model.ResponseDTO;
import com.BuyNest.backend.Model.Users;
import com.BuyNest.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
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


    public ResponseEntity<?> register(Users user){
        String name= user.getUsername();
        Users existingUser=repo.findByUsername(name);
        ResponseDTO response= new ResponseDTO();
        if(existingUser!=null){
            response.setMessage("Username Exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
       // creating new instance for verification
        Users newUser= new Users();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());

//       Registering user
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);

//      Logging In the new user
        AuthResponse ar=verify(newUser);
        return new ResponseEntity<>(ar,HttpStatus.CREATED);

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

    public ResponseEntity<?> checkUsername(String keyword) {
       Users user =repo.findByUsername(keyword);
       if(user==null){
           return new ResponseEntity<>(HttpStatus.OK);
       }
       else {
           return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
       }
    }
}
