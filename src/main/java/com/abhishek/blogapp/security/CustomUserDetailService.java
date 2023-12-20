package com.abhishek.blogapp.security;

import com.abhishek.blogapp.entities.User;
import com.abhishek.blogapp.exceptions.ResourceNotFoundException;
import com.abhishek.blogapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // load user from database
        return this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User ", "with email " + email, 0));
    }
}
