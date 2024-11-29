package com.akshay.backend.Service;

import com.akshay.backend.Model.Product;
import com.akshay.backend.Model.User;

public interface UserService {

    User CreateUser(User user);
    String LoginUser(User user);
    Boolean findByEmail(String email);

    User getUserFromToken(String token) throws Exception;

}
