package com.example.Nutri_Nest.service;

import com.example.Nutri_Nest.entity.UserPrincipal;
import com.example.Nutri_Nest.entity.Users;
import com.example.Nutri_Nest.repository.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userDetailsRepo.getByUsername(username); //name->getting from user
        if(user==null){
            throw new UsernameNotFoundException("Username not found!");
        }
        return new UserPrincipal(user);
    }
}
