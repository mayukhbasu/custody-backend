package com.custody.userservice.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custody.userservice.dto.RoleAssignmentRequest;
import com.custody.userservice.model.User;
import com.custody.userservice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User registerUser(Principal principal) {
        return userService.createUserIfNotExists(principal.getName(), "Default Name");
    }

    @GetMapping("/me")
    public User getCurrentUser(Principal principal) {
        return userService.getCurrentUser(principal.getName());
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
