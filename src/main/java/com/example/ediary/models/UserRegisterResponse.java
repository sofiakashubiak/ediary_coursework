package com.example.ediary.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

@Data
@Component
public class UserRegisterResponse {
    @Size(min = 3, message = "Minimal name length is 3 characters")
    private String name;

    @Size(min = 5, message = "Minimal username length is 5 characters")
    private String username;

    @Size(min = 6, message = "Minimal password length is 6 characters")
    private String password;

    @Size(min = 6, message = "Minimal password length is 6 characters")
    private String passwordConfirmation;

    @Size(min = 4, message = "Minimal group length is 4 characters")
    private String team;
}
