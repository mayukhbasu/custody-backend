package com.custody.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custody.userservice.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
