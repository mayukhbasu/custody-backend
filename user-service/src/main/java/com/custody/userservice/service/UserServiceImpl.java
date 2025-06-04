package com.custody.userservice.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private AuditService auditService; // ✅ This is needed

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User registerUser(String email, String name, String password) {
        logger.info("Registering user with email: {}", email);

        try {
            if (email == null || name == null || password == null) {
                logger.error("Missing required fields - email: {}, name: {}, password: {}", email, name, password != null ? "***" : null);
                throw new IllegalArgumentException("Email, name, and password must not be null");
            }

            if (userRepository.findByEmail(email).isPresent()) {
                logger.warn("User already exists with email: {}", email);
                throw new RuntimeException("User already exists");
            }

            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("VIEWER");
            user.setStatus("ACTIVE");
            user.setCreatedDate(LocalDateTime.now());

            User savedUser = userRepository.save(user);
            logger.info("User registered successfully with ID: {}", savedUser.getId());

            auditService.log("REGISTRATION_SUCCESS", email, "Successful registration");
            return savedUser;

        } catch (Exception e) {
            logger.error("Error occurred while registering user with email: {}", email, e);
            throw e;
        }
    }

    

    @Override
    public String authenticate(String email, String password) {
    User userObj = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(password, userObj.getPassword())) {
        auditService.log("LOGIN_FAILED", email, "Incorrect password");
        throw new RuntimeException("Invalid credentials");
    }
    auditService.log("LOGIN_SUCCESS", email, "Successful login");
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
