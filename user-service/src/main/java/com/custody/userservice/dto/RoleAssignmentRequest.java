package com.custody.userservice.dto;

import com.custody.userservice.model.UserRole;

public class RoleAssignmentRequest {
  private String email;
  private UserRole role;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }
}
