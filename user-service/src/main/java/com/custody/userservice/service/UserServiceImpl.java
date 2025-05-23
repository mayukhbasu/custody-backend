package com.custody.userservice.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.custody.userservice.dto.RoleAssignmentRequest;
import com.custody.userservice.model.User;
import com.custody.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements  UserService{

  @Autowired
  private UserRepository userRepository;

  @Override
  public User createUserIfNotExists(String email, String name) {
    return userRepository.findByEmail(email).orElseGet(() -> {
      User user = new User();
      user.setEmail(email);
      user.setName(name);
      user.setRole("VIEWER");
      user.setStatus("ACTIVE");
      user.setCreatedDate(LocalDateTime.now());
      return userRepository.save(user);
    });
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
