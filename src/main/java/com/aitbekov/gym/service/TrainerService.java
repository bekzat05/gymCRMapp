package com.aitbekov.gym.service;

import com.aitbekov.gym.dto.*;
import com.aitbekov.gym.model.Trainer;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainerService extends BaseService<Trainer, Long> {
    TrainerProfileReadDto findTrainerByUsername(String username);

    TrainerProfileReadDto update(String username, TrainerCreateDto dto);

    List<TrainerReadDto> findAll();

    void changeActiveStatus(String username, StatusUpdateDto dto);

    List<TrainingReadDto> getTrainerTrainings(String username);

    List<TrainerReadDto> getTrainersNotAssignedToTrainee(String traineeUsername);
}
