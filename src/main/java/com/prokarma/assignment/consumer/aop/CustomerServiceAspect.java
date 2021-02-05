package com.prokarma.assignment.consumer.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.prokarma.assignment.consumer.entity.ErrorLog;
import com.prokarma.assignment.consumer.repository.ErrorLogRepository;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;


@Component
@Aspect
public class CustomerServiceAspect {
    Logger logger = LoggerFactory.getLogger(CustomerServiceAspect.class);

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @AfterThrowing(
            pointcut = "execution(* com.prokarma.assignment.consumer.service.DefaultCustomerService.invokeCustomerService(..))",
            throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        KafkaCustomerRequest customer = (KafkaCustomerRequest) joinPoint.getArgs()[0];
        logger.error("Exception : {1}", ex);
        ErrorLog errorLog = new ErrorLog();
        errorLog.setPayload(customer);
        errorLog.setErrorType(ex.getClass().getName());
        errorLog.setErrorDescription(ex.getMessage());
        long startTime = System.currentTimeMillis();
        errorLogRepository.save(errorLog);
        logger.info("Error Log information saved time:{} ms",
                System.currentTimeMillis() - startTime);
    }
}
