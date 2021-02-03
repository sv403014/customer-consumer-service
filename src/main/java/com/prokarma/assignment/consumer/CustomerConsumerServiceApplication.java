package com.prokarma.assignment.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CustomerConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerConsumerServiceApplication.class, args);
    }

}
