package com.aitbekov.workloadservice.config;

public class JmsConstants {
    public static final String WORKLOAD_QUEUE = "workload.queue";
    public static final String DEAD_LETTER_QUEUE = "dead.letter.queue";
    public static final String WORKLOAD_RESPONSE_QUEUE = "workload.response.queue";

    public static final String WORKLOAD_REQUEST_QUEUE = "workload.request.queue";

    private JmsConstants() {
    }
}
