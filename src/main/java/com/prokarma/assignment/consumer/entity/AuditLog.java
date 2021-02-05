package com.prokarma.assignment.consumer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "AUDIT_LOG")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_number")
    private String customerNumber;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private KafkaCustomerRequest payload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public KafkaCustomerRequest getPayload() {
        return payload;
    }

    public void setPayload(KafkaCustomerRequest payload) {
        this.payload = payload;
    }


}
