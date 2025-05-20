package com.example.Nutri_Nest.controller;

import com.example.Nutri_Nest.entity.Users;
import com.example.Nutri_Nest.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    @Autowired
    UsersService userService;

    @PostMapping("/admin/user-create")
    public String createUser(@RequestBody Users users){
        return userService.createUser(users);
    }

    @PostMapping("/public/register")
    public String registerUser(@RequestBody Users users){
        users.setRole("USER"); // assign default role
        return userService.createUser(users);
    }

}
