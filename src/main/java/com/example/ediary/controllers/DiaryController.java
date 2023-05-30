package com.example.ediary.controllers;

import com.example.ediary.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiaryController {

    private final UserRepository userRepository;

    public DiaryController(UserRepository userRepository ){
        this.userRepository = userRepository;
    }

    @GetMapping("/diary")
    public String diary(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("diary",
                userRepository.findByUsername(authentication.getName()).get().getDiary());
        return "diaryDetails";
    }
}

