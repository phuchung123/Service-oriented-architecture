package com.example.bth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bth2.model.User;
import com.example.bth2.model.UserPrincipal;
import com.example.bth2.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        
        if (user == null) {
            System.out.print("User not found");
            throw new UsernameNotFoundException("User name not found");
        }
        
        return new UserPrincipal(user);
    }
}