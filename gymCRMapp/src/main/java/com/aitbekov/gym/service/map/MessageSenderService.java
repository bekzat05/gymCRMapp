package com.aitbekov.gym.service.map;

import com.aitbekov.gym.dto.TrainerWorkloadRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@Slf4j
public class MessageSenderService {
    @Value("${cloud.aws.sqs.queue.url}")
    private String queueUrl;

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageSenderService(SqsClient sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    public void sendTrainerWorkloadRequest(TrainerWorkloadRequest request) {
        log.info("Sending workload request: {}", request);
        try {
            String messageBody = objectMapper.writeValueAsString(request);
            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build();
            var response = sqsClient.sendMessage(sendMsgRequest);
            log.info("Message sent successfully. MessageId: {}", response.messageId());
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize TrainerWorkloadRequest", e);
        }
    }

}
