package com.custody.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.custody.userservice.dto.LoginRequest;
import com.custody.userservice.dto.RegisterRequest;
import com.custody.userservice.dto.RoleAssignmentRequest;
import com.custody.userservice.model.User;
import com.custody.userservice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterRequest request) {
        return userService.registerUser(request.getEmail(), request.getName(), request.getPassword());
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return userService.authenticate(request.getEmail(), request.getPassword());
    }

    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        return userService.getCurrentUser(authentication.getName());
    }

    @PostMapping("/roles/assign")
    public void assignRole(@RequestBody RoleAssignmentRequest request) {
        userService.assignRole(request);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping("/{id}/status")
    public void changeStatus(@PathVariable Long id, @RequestBody String status) {
        userService.changeUserStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/roles")
    public List<String> getRoles() {
        return userService.getRoles();
    }

    @GetMapping("/{email}/roles")
    public String getRoleByEmail(@PathVariable String email) {
        return userService.getCurrentUser(email).getRole();
    }
}
