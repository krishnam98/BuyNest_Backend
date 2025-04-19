package com.amazonCklone.backend.Service;

import com.amazonCklone.backend.Model.UserPrincipal;
import com.amazonCklone.backend.Model.Users;
import com.amazonCklone.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users user = userRepo.findByUsername(username);

       if(user==null){
           System.out.println("User Not Found");
           throw new UsernameNotFoundException("User Not Found");
       }
        else{
            return new UserPrincipal(user);

       }
    }
}
