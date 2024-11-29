package com.akshay.backend.Controller.Client;

import com.akshay.backend.Controller.Client.Request.LoginRequest;
import com.akshay.backend.Controller.Client.Response.AuthResponse;
import com.akshay.backend.Model.User;
import com.akshay.backend.Model.Role;  // Importing the Role enum
import com.akshay.backend.Repository.UserRepository;
import com.akshay.backend.SecurityConfig.JwtProvider;
import com.akshay.backend.SecurityConfig.Service.CustomUserDetailsService;
import com.akshay.backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public AuthResponse createUser(@RequestBody User user) throws Exception {
        // Check if the email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("This email is already linked with another account");
        }

        // Encoding the user's password before saving it
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if not provided
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);  // Assign CUSTOMER role by default
        }
        // Log the role for debugging
        System.out.println("User Role: " + user.getRole());

        // Set user authority based on the role
        List<GrantedAuthority> authority = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authority);

        // Generate JWT token
        String token = JwtProvider.generateToken(authentication);

        // Save the user if token generation is successful
        if (!token.isEmpty()) {
            userService.CreateUser(user);
        }
        return new AuthResponse(token, "User registered Successfully");
    }

    private Authentication authenticate(String email, String password) {
        // Load user details using email
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Handle invalid email scenario
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Email id");
        }

        // Handle incorrect password scenario
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        // Authenticate the user
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest loginRequest) {
        // Authenticate user credentials
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        // Generate JWT token upon successful authentication
        String token = JwtProvider.generateToken(authentication);
        return new AuthResponse(token, "User logged-in Successfully");
    }
}
