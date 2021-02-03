package com.prokarma.assignment.customer.consumer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.prokarma.assignment.consumer.converter.DefaultCustomerConsumerConverter;
import com.prokarma.assignment.consumer.service.CustomerConsumerService;
import com.prokarma.assignment.consumer.service.CustomerService;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;

@EmbeddedKafka
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerConsumerServiceTest {
    private static final String TOPIC = "ProdTopic";

    @InjectMocks
    private CustomerConsumerService customerConsumerService;

    @Mock
    private CustomerService customerService;

    @Mock
    private DefaultCustomerConsumerConverter defaultCustomerConsumerConverter;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    BlockingQueue<ConsumerRecord<String, String>> records;

    KafkaMessageListenerContainer<String, String> container;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(
                KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker));
        DefaultKafkaConsumerFactory<String, String> consumerFactory =
                new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(),
                        new StringDeserializer());
        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, String>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container,
                embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @AfterAll
    void tearDown() {
        container.stop();
    }

    @Test
    void testGetTopicsWhenEnsureSendMessageIsReceived() throws Exception {
        Map<String, Object> configs =
                new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        Producer<String, String> producer = new DefaultKafkaProducerFactory<>(configs,
                new StringSerializer(), new StringSerializer()).createProducer();

        producer.send(
                new ProducerRecord<>(TOPIC, "z4w9bqw6-consumers", "{\"event\":\"Test Event\"}"));
        producer.flush();

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.key()).isEqualTo("z4w9bqw6-consumers");
        assertThat(singleRecord.value()).isEqualTo("{\"event\":\"Test Event\"}");
    }

    @Test
    void testGetTopics() {

        CustomerConsumerService mockCustomerConsumerService = mock(CustomerConsumerService.class);

        doAnswer((i) -> {
            KafkaCustomerRequest customerRequest = i.getArgument(0);
            assertEquals("EE89878765", customerRequest.getCustomerNumber());
            return null;
        }).when(mockCustomerConsumerService).getTopics(getKafkaCustomerRequestData());
        mockCustomerConsumerService.getTopics(getKafkaCustomerRequestData());
        customerConsumerService.getTopics(getKafkaCustomerRequestData());
        verify(mockCustomerConsumerService, times(1)).getTopics(getKafkaCustomerRequestData());

    }

    private KafkaCustomerRequest getKafkaCustomerRequestData() {

        KafkaCustomerRequest customerRequest = new KafkaCustomerRequest();
        customerRequest.setCustomerNumber("EE89878765");
        customerRequest.setFirstName("shafi12345");
        customerRequest.setLastName("shafi12345");
        customerRequest.setBirthdate("11-02-1988");
        customerRequest.setCountry("India");
        customerRequest.setCountryCode("IN");
        customerRequest.setMobileNumber("9900853904");
        customerRequest.setEmail("shafi.shaik999@gmail.com");
        customerRequest.setCustomerStatus("Open");
        customerRequest.setAddress("address");

        return customerRequest;
    }

}
