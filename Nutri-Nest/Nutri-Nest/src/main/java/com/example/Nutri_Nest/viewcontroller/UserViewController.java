package com.example.Nutri_Nest.viewcontroller;

import com.example.Nutri_Nest.dto.UserDTO;
import com.example.Nutri_Nest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view/user")
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

    @GetMapping
    public String viewUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers().getBody();
        model.addAttribute("users", users);
        model.addAttribute("user", new UserDTO());
        return "user"; // Thymeleaf file name: user.html
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserDTO userDTO) {
        if (userDTO.getId() == null) {
            userService.createUser(userDTO);
        } else {
            userService.fullUpdateById(userDTO, userDTO.getId());
        }
        return "redirect:/view/user";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        UserDTO user = userService.getUserById(id).getBody();
        List<UserDTO> users = userService.getAllUsers().getBody();
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "user"; // reuse the same HTML
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/view/user";
    }
}
