package com.prokarma.assignment.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prokarma.assignment.consumer.entity.AuditLog;
import com.prokarma.assignment.consumer.repository.AuditLogRepository;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;

@Service
public class DefaultCustomerService implements CustomerService {

    Logger logger = LoggerFactory.getLogger(DefaultCustomerService.class);

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public boolean invokeCustomerService(KafkaCustomerRequest kafkaCustomerRequest) {
        long startingTime = System.currentTimeMillis();
        
        AuditLog auditLog = new AuditLog();
        auditLog.setCustomerNumber(kafkaCustomerRequest.getCustomerNumber());
        auditLog.setPayload(kafkaCustomerRequest);
        auditLog = auditLogRepository.save(auditLog);
        logger.info("Audit data saved time : {} ms", System.currentTimeMillis() - startingTime);
        return auditLog!=null;

    }

}
