package com.example.Nutri_Nest.viewcontroller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {

    @GetMapping("/nutrinest")
    public String home() {
        return "main";
    }
}

