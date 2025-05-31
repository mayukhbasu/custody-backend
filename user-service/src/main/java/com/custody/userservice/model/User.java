package com.custody.userservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;
    private String role; // ADMIN, OPS, VIEWER
    private String password;
    
    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
    private String status; // ACTIVE, INACTIVE
    private LocalDateTime createdDate;
    public Long getId() {
      return id;
    }
    
    
    public void setId(Long id) {
      this.id = id;
    }
    public String getEmail() {
      return email;
    }
    public void setEmail(String email) {
      this.email = email;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public String getRole() {
      return role;
    }
    public void setRole(String role) {
      this.role = role;
    }
    public String getStatus() {
      return status;
    }
    public void setStatus(String status) {
      this.status = status;
    }
    public LocalDateTime getCreatedDate() {
      return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
      this.createdDate = createdDate;
    }
    
}
