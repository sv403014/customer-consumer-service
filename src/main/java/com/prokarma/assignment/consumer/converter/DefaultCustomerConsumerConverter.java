package com.prokarma.assignment.consumer.converter;

import org.springframework.stereotype.Component;
import com.prokarma.assignment.consumer.constant.CustomerConsumerConstant;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;

@Component
public class DefaultCustomerConsumerConverter
        implements CustomerConsumerConverter<KafkaCustomerRequest> {

    @Override
    public void convert(KafkaCustomerRequest kafkaCustomerRequest) {
        kafkaCustomerRequest.setCustomerNumber(kafkaCustomerRequest.getCustomerNumber().replaceAll(
                CustomerConsumerConstant.CUSTOMER_NUMBER_REGEX.getValue(),
                CustomerConsumerConstant.REPLACEMENT_CHARACTER.getValue()));

        kafkaCustomerRequest.setEmail(kafkaCustomerRequest.getEmail().replaceFirst(
                CustomerConsumerConstant.EMAIL_REGEX.getValue(),
                CustomerConsumerConstant.EMAIL_REPLACEMENT_CHARACTER.getValue()));

        kafkaCustomerRequest.setBirthdate(kafkaCustomerRequest.getBirthdate().replaceAll(
                CustomerConsumerConstant.BIRTHDATE_REGEX.getValue(),
                CustomerConsumerConstant.REPLACEMENT_CHARACTER.getValue()));

    }

}
