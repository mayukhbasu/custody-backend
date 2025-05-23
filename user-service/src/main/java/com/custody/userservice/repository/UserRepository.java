package com.custody.userservice.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custody.userservice.model.User;


public interface  UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
