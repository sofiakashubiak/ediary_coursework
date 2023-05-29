package com.example.ediary.repository;

import com.example.ediary.models.Role;
import com.example.ediary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAllByApproved(boolean approved);
    Optional<User> findById(Long id);
    void deleteById(Long id);
    Optional<User> findFirstByRole(Role role);
}
