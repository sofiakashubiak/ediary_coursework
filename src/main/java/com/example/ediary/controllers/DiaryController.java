package com.example.ediary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiaryController {
    @GetMapping("/")
    public String diary(){
        return "diary";
    }
}

