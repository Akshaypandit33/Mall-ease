package com.akshay.backend.ServiceImplementation;

import com.akshay.backend.Model.Product;
import com.akshay.backend.Model.User;
import com.akshay.backend.Repository.UserRepository;
import com.akshay.backend.SecurityConfig.JwtProvider;
import com.akshay.backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    @Override
    public User CreateUser(User user) {
        User newUser= new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());
        newUser.setCreated_at(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    @Override
    public String LoginUser(User user) {
        Optional<User> newUser=userRepository.findByEmail(user.getEmail());
        if(newUser.isPresent())
        {
            if(newUser.get().getPassword().equals(user.getPassword()))
            {
                return "User logged in successfully";
            }
            else {
                return "Invalid user credentials";
            }
        }
        else {
            return "User is not registered";
        }
    }

    @Override
    public Boolean findByEmail(String email) {
        if(userRepository.findByEmail(email).isPresent())
        {
            return true;
        }
        return false;
    }

    @Override
    public User getUserFromToken(String token) throws Exception {
        String email= JwtProvider.extractEmail(token);
        if(!email.isEmpty())
        {
            return userRepository.findByEmail(email).orElseThrow(() -> new Exception("User can not found"));
        }
        return null;
    }


}
