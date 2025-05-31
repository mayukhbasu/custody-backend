package com.custody.userservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.custody.userservice.dto.RoleAssignmentRequest;
import com.custody.userservice.model.User;
import com.custody.userservice.repository.UserRepository;
import com.custody.userservice.utils.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User registerUser(String email, String name, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password)); // ✅ MUST HASH HERE
        return userRepository.save(user);
    }
    

    @Override
    public String authenticate(String email, String password) {
    User userObj = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(password, userObj.getPassword())) {
        throw new RuntimeException("Invalid credentials");
    }

    return jwtUtil.generateToken(email);
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
