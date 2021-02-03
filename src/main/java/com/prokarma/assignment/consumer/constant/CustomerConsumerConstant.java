package com.prokarma.assignment.consumer.constant;

public enum CustomerConsumerConstant {

    BIRTHDATE_REGEX("[^-\\n](?=.*-[^-]*$)"), CUSTOMER_NUMBER_REGEX(
            "\\d(?=(?:\\D*\\d){0,3}\\D*$)"), EMAIL_REGEX(
                    "[\\w\\W]{4}"), REPLACEMENT_CHARACTER("*"), EMAIL_REPLACEMENT_CHARACTER("****");

    private String value;

    private CustomerConsumerConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
