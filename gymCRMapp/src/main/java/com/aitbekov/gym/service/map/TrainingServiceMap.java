package com.aitbekov.gym.service.map;

import com.aitbekov.gym.dto.CredentialsDto;
import com.aitbekov.gym.dto.TrainingCreateDto;
import com.aitbekov.gym.exceptions.AuthenticationException;
import com.aitbekov.gym.exceptions.TraineeNotFoundException;
import com.aitbekov.gym.exceptions.TrainerNotFoundException;
import com.aitbekov.gym.exceptions.TrainingTypeNotFoundException;
import com.aitbekov.gym.repository.TraineeRepository;
import com.aitbekov.gym.repository.TrainerRepository;
import com.aitbekov.gym.repository.TrainingRepository;
import com.aitbekov.gym.repository.TrainingTypeRepository;
import com.aitbekov.gym.mapstruct.TrainingMapper;
import com.aitbekov.gym.model.Trainee;
import com.aitbekov.gym.model.Trainer;
import com.aitbekov.gym.model.Training;
import com.aitbekov.gym.model.TrainingType;
import com.aitbekov.gym.service.TrainingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TrainingServiceMap implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingMapper trainingMapper;

    @Transactional
    public Training create(Training training, CredentialsDto credentialsDto) {
        log.info("Creating new training with credentials: {}", credentialsDto.username());
        Training savedTraining = trainingRepository.save(training);
        log.info("Training saved successfully: {}", savedTraining);
        return savedTraining;
    }
    @Transactional
    public boolean delete(Long id, CredentialsDto credentialsDto) {
        log.info("Deleting training with ID: {} using credentials: {}", id, credentialsDto.username());
        Optional<Training> maybeTraining = trainingRepository.findById(id);
        maybeTraining.ifPresent(training -> {
            trainingRepository.delete(training);
            log.info("Training with ID: {} deleted successfully", id);
        });
        return maybeTraining.isPresent();
    }

    @Override
    @Transactional
    public void create(TrainingCreateDto dto) {
        log.info("Creating training with trainee username: {} and trainer username: {}", dto.traineeUsername(), dto.trainerUsername());
        Trainee trainee = traineeRepository.findByUsername(dto.traineeUsername())
                .orElseThrow(() -> {
                    log.warn("Trainee with username: {} not found", dto.traineeUsername());
                    return new TraineeNotFoundException(dto.traineeUsername());
                });

        Trainer trainer = trainerRepository.findByUsername(dto.trainerUsername())
                .orElseThrow(() -> {
                    log.warn("Trainer with username: {} not found", dto.trainerUsername());
                    return new TrainerNotFoundException(dto.trainerUsername());
                });

        TrainingType trainingType = trainingTypeRepository.findByName(dto.type())
                .orElseThrow(() -> {
                    log.warn("Training type with name: {} not found", dto.type());
                    return new TrainingTypeNotFoundException("Training type not found");
                });

        Training training = trainingMapper.toEntity(dto);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setDate(LocalDateTime.now());
        training.setName(dto.name());
        training.setDuration(dto.duration());
        trainingRepository.save(training);
        log.info("Training created successfully with ID: {}", training.getId());
    }
}
