package com.prokarma.assignment.consumer.service;

import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;

public interface CustomerService {

    public void invokeCustomerService(KafkaCustomerRequest kafkaCustomerRequest);

}
