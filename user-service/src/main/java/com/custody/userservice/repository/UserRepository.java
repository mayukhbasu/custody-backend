package com.custody.userservice.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custody.userservice.model.User;


public interface  UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  List<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}
