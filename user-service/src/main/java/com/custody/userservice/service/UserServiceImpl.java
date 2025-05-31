package com.custody.userservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.custody.userservice.dto.RoleAssignmentRequest;
import com.custody.userservice.model.User;
import com.custody.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(String email, String name, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User newUser = new User();
        newUser.setEmail(email);
        
        
        return userRepository.save(newUser);
    }

    @Override
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

    @Override
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void assignRole(RoleAssignmentRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        user.setRole(request.getRole());
        userRepository.save(user);
    }

    @Override
    public void changeUserStatus(Long id, String status) {
        User user = userRepository.findById(id).orElseThrow();
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<String> getRoles() {
        return Arrays.asList("ADMIN", "OPS", "VIEWER");
    }
}
