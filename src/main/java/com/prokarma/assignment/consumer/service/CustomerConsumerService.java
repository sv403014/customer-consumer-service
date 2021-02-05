package com.prokarma.assignment.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.prokarma.assignment.consumer.converter.DefaultCustomerConsumerConverter;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;

@Service
public class CustomerConsumerService {
    Logger logger = LoggerFactory.getLogger(CustomerConsumerService.class);

    @Autowired
    private DefaultCustomerConsumerConverter defaultCustomerConsumerConverter;

    @Autowired
    private CustomerService customerService;

    /*
     * @KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics =
     * "${producer.topic}", groupId = "${consumer.group-id}")
     */
    @KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics = "${cloudkarafka.topic}",
            groupId = "${cloudkarafka.group-id}")
    public void getTopics(KafkaCustomerRequest kafkaCustomerRequest) {
        long startingTime = System.currentTimeMillis();
        defaultCustomerConsumerConverter.convert(kafkaCustomerRequest);
        logger.info("Consumed data {}", kafkaCustomerRequest);
        customerService.invokeCustomerService(kafkaCustomerRequest);
        logger.info("Consumer Serviced time : {} ms", System.currentTimeMillis() - startingTime);
    }

}
