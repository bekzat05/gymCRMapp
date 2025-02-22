package com.aitbekov.gym.service;

import com.aitbekov.gym.dto.*;
import com.aitbekov.gym.model.Trainer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface TrainerService extends BaseService<Trainer, Long> {
    TrainerProfileReadDto findTrainerByUsername(String username);

    TrainerProfileReadDto update(String username, TrainerCreateDto dto);

    Set<TrainerReadDto> findAll();

    void changeActiveStatus(String username, StatusUpdateDto dto);

    Set<TrainingReadDto> getTrainerTrainings(String username);

    Set<TrainerReadDto> getTrainersNotAssignedToTrainee(String traineeUsername);

    void updateTrainerWorkload(TrainerWorkloadRequest request);

    TrainerWorkloadSummary getTrainerWorkload(String username, int year, int month);
}
