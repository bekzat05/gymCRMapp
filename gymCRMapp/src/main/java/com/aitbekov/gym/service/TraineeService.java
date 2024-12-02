package com.aitbekov.gym.service;

import com.aitbekov.gym.dto.*;
import com.aitbekov.gym.model.Trainee;

import java.time.LocalDateTime;
import java.util.List;

public interface TraineeService extends BaseService<Trainee, Long> {

    TraineeProfileReadDto findTraineeByUsername(String username);

    TraineeProfileReadDto update(String username, TraineeCreateAndUpdateDto dto);

    void delete(String username);

    void changeActiveStatus(String username, StatusUpdateDto statusUpdateDto);

    List<TrainingReadDto> getTraineeTrainings(String username);

    TraineeProfileReadDto updateTraineeTrainers(String username, List<String> trainers);
}