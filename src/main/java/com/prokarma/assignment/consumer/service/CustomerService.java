package com.prokarma.assignment.consumer.service;

import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;

public interface CustomerService {

    public boolean invokeCustomerService(KafkaCustomerRequest kafkaCustomerRequest);

}
