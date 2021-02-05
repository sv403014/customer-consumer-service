package com.prokarma.assignment.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.prokarma.assignment.consumer.entity.AuditLog;

@Repository
public interface AuditLogRepository extends CrudRepository<AuditLog, Long> {

}
