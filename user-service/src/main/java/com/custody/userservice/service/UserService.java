package com.custody.userservice.service;

import java.util.List;

import com.custody.userservice.dto.RoleAssignmentRequest;
import com.custody.userservice.model.User;

public interface UserService {
    User registerUser(String email, String name, String password);
    User authenticate(String email, String password);
    User getCurrentUser(String email);
    List<User> getAllUsers();
    void assignRole(RoleAssignmentRequest request);
    void changeUserStatus(Long id, String status);
    void deleteUser(Long id);
    List<String> getRoles();
}
