package com.prokarma.assignment.customer.consumer.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prokarma.assignment.consumer.converter.DefaultCustomerConsumerConverter;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;

@ExtendWith(MockitoExtension.class)
class DefaultCustomerConsumerConverterTest {

    @Test
    void testConvert() {
        DefaultCustomerConsumerConverter mockDefaultCustomerConsumerConverter =
                mock(DefaultCustomerConsumerConverter.class);
        doAnswer((i) -> {
            KafkaCustomerRequest customerRequest = i.getArgument(0);
            assertEquals("EE89878765", customerRequest.getCustomerNumber());
            return null;
        }).when(mockDefaultCustomerConsumerConverter).convert(getKafkaCustomerRequestData());
        mockDefaultCustomerConsumerConverter.convert(getKafkaCustomerRequestData());
        verify(mockDefaultCustomerConsumerConverter, times(1))
                .convert(getKafkaCustomerRequestData());
    }

    private KafkaCustomerRequest getKafkaCustomerRequestData() {

        KafkaCustomerRequest customerRequest = new KafkaCustomerRequest();
        customerRequest.setCustomerNumber("EE89878765");
        customerRequest.setFirstName("shafi12345");
        customerRequest.setLastName("shaik12345");
        customerRequest.setBirthdate("11-02-1988");
        customerRequest.setCountry("India");
        customerRequest.setCountryCode("IN");
        customerRequest.setMobileNumber("9900853904");
        customerRequest.setEmail("shafi.shaik999@gmail.com");
        customerRequest.setCustomerStatus("Open");
        customerRequest.setAddress("Hyderabad");

        return customerRequest;
    }

}
