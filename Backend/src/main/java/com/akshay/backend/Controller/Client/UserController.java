package com.akshay.backend.Controller.Client;

import com.akshay.backend.Controller.Client.Request.LoginRequest;
import com.akshay.backend.Model.User;
import com.akshay.backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String Home()
    {
        return "Hello";
    }



}
