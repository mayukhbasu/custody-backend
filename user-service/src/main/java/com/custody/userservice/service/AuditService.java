package com.custody.userservice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.custody.userservice.model.AuditLog;
import com.custody.userservice.repository.AuditLogRepository;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String action, String email, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setEmail(email);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log);
    }
}
