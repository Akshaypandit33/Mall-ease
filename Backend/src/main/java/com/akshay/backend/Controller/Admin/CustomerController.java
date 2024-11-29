package com.akshay.backend.Controller.Admin;

import com.akshay.backend.Model.Role;
import com.akshay.backend.Model.User;
import com.akshay.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final UserRepository userRepository;

    @GetMapping("/get")
    public List<User> getAllCustomer()
    {
        return userRepository.findByRole(Role.CUSTOMER);
    }
}
