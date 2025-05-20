package com.example.Nutri_Nest.viewcontroller;


import com.example.Nutri_Nest.dto.UserProgressDTO;
import com.example.Nutri_Nest.service.UserProgressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/userProgress")
public class UserProgressViewController {

    @Autowired
    private UserProgressService userProgressService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("userProgressList", userProgressService.getAllUserProgress().getBody());
        model.addAttribute("userProgress", new UserProgressDTO());
        return "user_progress";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("userProgress") UserProgressDTO dto) {
        if (dto.getId() == null) {
            userProgressService.createUserProgress(dto);
        } else {
            userProgressService.fullUpdateById(dto, dto.getId());
        }
        return "redirect:/userProgress";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        UserProgressDTO dto = userProgressService.getUserProgressById(id).getBody();
        model.addAttribute("userProgressList", userProgressService.getAllUserProgress().getBody());
        model.addAttribute("userProgress", dto);
        return "user_progress";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userProgressService.deleteById(id);
        return "redirect:/userProgress";
    }
}

