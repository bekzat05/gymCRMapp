package com.aitbekov.gym.service.map;

import com.aitbekov.gym.dto.*;
import com.aitbekov.gym.exceptions.TrainerNotFoundException;
import com.aitbekov.gym.mapstruct.TraineeMapper;
import com.aitbekov.gym.repository.TrainerRepository;
import com.aitbekov.gym.repository.TrainingRepository;
import com.aitbekov.gym.repository.TrainingTypeRepository;
import com.aitbekov.gym.service.TrainerService;
import com.aitbekov.gym.mapstruct.TrainerMapper;
import com.aitbekov.gym.mapstruct.TrainingMapper;
import com.aitbekov.gym.model.Trainer;
import com.aitbekov.gym.model.Training;
import com.aitbekov.gym.model.TrainingType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TrainerServiceMap implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainingMapper trainingMapper;

    private final MessageSenderService messageSenderService;

//    private JmsTemplate jmsTemplate;

    @Override
    @Transactional
    public TrainerProfileReadDto findTrainerByUsername(String username) {
        log.info("Finding trainer by username: {}", username);
        Trainer trainer = trainerRepository.findByUsername(username).
                orElseThrow(() -> new TrainerNotFoundException(username));


        if (trainer != null) {
            List<TrainingType.Type> specializations = TrainerMapper.mapSpecializations(trainer.getSpecializations());
            List<TraineeReadDto> traineesList = (trainer.getTrainees() != null && !trainer.getTrainees().isEmpty())
                    ? trainer.getTrainees().stream()
                    .map(traineeMapper::toDto)
                    .collect(Collectors.toList())
                    : Collections.emptyList();
            TrainerProfileReadDto profile = new TrainerProfileReadDto(
                    trainer.getUsername(),
                    trainer.getFirstName(),
                    trainer.getLastName(),
                    specializations,
                    traineesList
            );
            log.info("Trainer found: {}", profile);
            return profile;
        }else {
            log.warn("Trainer not found with username: {}", username);
            return null;
        }
    }

    @Override
    @Transactional
    public TrainerProfileReadDto update(String username, TrainerCreateDto dto) {
        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Trainer not found with username: {}", username);
                    return new TrainerNotFoundException("Trainer not found with username: " + username);
                });

        trainer.setFirstName(dto.firstName());
        trainer.setLastName(dto.lastName());
        trainer.setIsActive(dto.isActive());
        Set<TrainingType> specializations = trainingTypeRepository.findByNames(dto.specializations());
        trainer.setSpecializations(specializations);

        trainerRepository.save(trainer);

        log.info("Trainer with username: {} updated successfully", username);
        return findTrainerByUsername(username);
    }

    @Override
    @Transactional
    public Set<TrainerReadDto> findAll() {
        log.info("Finding all trainers");
        List<Trainer> trainers = trainerRepository.findAll();
        Set<TrainerReadDto> trainerDtos = trainerMapper.toDtoList(Set.copyOf(trainers));
        log.info("Found {} trainers", trainerDtos.size());
        return trainerDtos;
    }

    @Override
    @Transactional
    public void changeActiveStatus(String username, StatusUpdateDto dto) {
        log.info("Changing active status for trainer with username: {}", username);
        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Trainer not found with username: {}", username);
                    return new TrainerNotFoundException(username);});
        trainer.setIsActive(dto.isActive());
        trainerRepository.save(trainer);
        log.info("Trainer {} active status changed to {}", username, dto.isActive());
    }

    @Override
    @Transactional
    public Set<TrainingReadDto> getTrainerTrainings(String username) {
        log.info("Fetching trainings for trainer with username: {}", username);
        Set<Training> trainings = trainingRepository.findTrainingsByTrainerUsername(username);
        Set<TrainingReadDto> trainingDtos = trainingMapper.toDtoList(trainings);
        log.info("Found {} trainings", trainingDtos.size());
        return trainingDtos;
    }

    @Override
    @Transactional
    public Set<TrainerReadDto> getTrainersNotAssignedToTrainee(String traineeUsername) {
        log.info("Finding trainers not assigned to trainee with username: {}", traineeUsername);
        Set<Trainer> trainers = trainerRepository.findTrainersNotAssignedToTrainee(traineeUsername);
        Set<TrainerReadDto> trainerDtos = trainerMapper.toDtoList(trainers);
        log.info("Found {} trainers not assigned to trainee", trainerDtos.size());
        return trainerDtos;
    }

    @Override
    public void updateTrainerWorkload(TrainerWorkloadRequest request) {
        log.info("Updating workload for trainer: {}", request.getUsername());
        String username = request.getUsername();
        trainerRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Trainer not found with username: {}", username);
                    return new TrainerNotFoundException(username);
                });
        log.info("Send workload request to queue");
        messageSenderService.sendTrainerWorkloadRequest(request);
//        jmsTemplate.convertAndSend(JmsConstants.WORKLOAD_QUEUE, request);
    }

    @Override
    public TrainerWorkloadSummary getTrainerWorkload(String username, int year, int month) {
        trainerRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Trainer not found with username: {}", username);
                    return new TrainerNotFoundException(username);
                });
        return null;
    }

//    private Map<String, Object> createRequestPayload(String username, int year, int month) {
//        Map<String, Object> requestPayload = new HashMap<>();
//        requestPayload.put("username", username);
//        requestPayload.put("year", year);
//        requestPayload.put("month", month);
//        return requestPayload;
//    }
//
//    private MessageCreator createMessageCreator(Map<String, Object> requestPayload) {
//        return session -> {
//            Message message = session.createObjectMessage((Serializable) requestPayload);
//            message.setJMSReplyTo(new ActiveMQQueue(JmsConstants.WORKLOAD_RESPONSE_QUEUE));
//            return message;
//        };
//    }
//
//    private Optional<TrainerWorkloadSummary> receiveAndProcessResponse() {
//        Message response = jmsTemplate.receive(JmsConstants.WORKLOAD_RESPONSE_QUEUE);
//
//        if (response instanceof TextMessage) {
//            try {
//                String jsonSummary = ((TextMessage) response).getText();
//                TrainerWorkloadSummary summary = new ObjectMapper().readValue(jsonSummary, TrainerWorkloadSummary.class);
//                log.info("Received summary: {}", summary);
//                return Optional.of(summary);
//            } catch (JMSException | JsonProcessingException e) {
//                log.error("Error processing response message", e);
//            }
//        }
//
//        return Optional.empty();
//    }

}
