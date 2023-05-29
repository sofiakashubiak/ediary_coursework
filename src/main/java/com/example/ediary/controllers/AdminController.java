package com.example.ediary.controllers;

import com.example.ediary.models.User;
import com.example.ediary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/adminPage")
public class AdminController {

    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("unapprovedUsers")
    public List<User> setUnapprovedUsers() {
        return userRepository.findAllByApproved(false);
    }

    @GetMapping()
    public String adminPage() {
        return "adminPage";
    }

    @PatchMapping("approveUser/{id}")
    public String approveUser(@PathVariable("id") Long id) {

        User user = userRepository.findById(id).orElseThrow();
        user.setApproved(true);
        userRepository.save(user);
        return "redirect:/adminPage";
    }

    @DeleteMapping("unApproveUser/{id}")
    public String unApproveUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/adminPage";
    }
}
