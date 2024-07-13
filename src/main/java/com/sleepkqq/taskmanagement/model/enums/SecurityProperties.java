package com.sleepkqq.taskmanagement.model.enums;

public enum SecurityProperties {

    BEARER_PREFIX("Bearer "),
    BEARER_TYPE("Bearer"),
    AUTHORIZATION_HEADER("Authorization");

    private final String value;

    SecurityProperties(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
