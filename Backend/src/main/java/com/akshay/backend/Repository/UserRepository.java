package com.akshay.backend.Repository;

import com.akshay.backend.Model.Role;
import com.akshay.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByRole(Role role);
    Optional<User> findByEmail(String email);
}
