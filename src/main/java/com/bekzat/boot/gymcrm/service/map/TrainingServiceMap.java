package com.bekzat.boot.gymcrm.service.map;

import com.bekzat.boot.gymcrm.dto.CredentialsDto;
import com.bekzat.boot.gymcrm.dto.TrainingCreateDto;
import com.bekzat.boot.gymcrm.exceptions.AuthenticationException;
import com.bekzat.boot.gymcrm.exceptions.TraineeNotFoundException;
import com.bekzat.boot.gymcrm.exceptions.TrainerNotFoundException;
import com.bekzat.boot.gymcrm.exceptions.TrainingTypeNotFoundException;
import com.bekzat.boot.gymcrm.mapstruct.TrainingMapper;
import com.bekzat.boot.gymcrm.model.Trainee;
import com.bekzat.boot.gymcrm.model.Trainer;
import com.bekzat.boot.gymcrm.model.Training;
import com.bekzat.boot.gymcrm.model.TrainingType;
import com.bekzat.boot.gymcrm.repository.TraineeRepository;
import com.bekzat.boot.gymcrm.repository.TrainerRepository;
import com.bekzat.boot.gymcrm.repository.TrainingRepository;
import com.bekzat.boot.gymcrm.repository.TrainingTypeRepository;
import com.bekzat.boot.gymcrm.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingServiceMap implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserCredentialsService userCredentialsService;
    private final TrainingMapper trainingMapper;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    @Transactional
    public Training create(Training training, CredentialsDto credentialsDto) {
        log.info("Creating new training with credentials: {}", credentialsDto.username());
        if (!userCredentialsService.checkCredentials(credentialsDto)) {
            log.warn("Invalid credentials provided for creating training");
            throw new AuthenticationException("Invalid credentials");
        }
        Training savedTraining = trainingRepository.save(training);
        log.info("Training saved successfully: {}", savedTraining);
        return savedTraining;
    }

    @Transactional
    public boolean delete(Long id, CredentialsDto credentialsDto) {
        log.info("Deleting training with ID: {} using credentials: {}", id, credentialsDto.username());
        if (!userCredentialsService.checkCredentials(credentialsDto)) {
            log.warn("Invalid credentials provided for deleting training");
            throw new AuthenticationException("Invalid credentials");
        }
        Optional<Training> maybeTraining = trainingRepository.findById(id);
        maybeTraining.ifPresent(training -> {
            trainingRepository.delete(training);
            log.info("Training with ID: {} deleted successfully", id);
        });
        return maybeTraining.isPresent();
    }

    @Transactional
    @Override
    public void create(TrainingCreateDto createDto) {
        log.info("Creating training with trainee username: {} and trainer username: {}", createDto.traineeUsername(), createDto.trainerUsername());
        Trainee trainee = traineeRepository.findByUsername(createDto.traineeUsername())
                .orElseThrow(() -> {
                    log.warn("Trainee not found with username: {}", createDto.traineeUsername());
                    return new TraineeNotFoundException(createDto.traineeUsername());
                });
        Trainer trainer = trainerRepository.findByUsername(createDto.trainerUsername())
                .orElseThrow(() -> {
                    log.warn("Trainer not found with username: {}", createDto.trainerUsername());
                    return new TrainerNotFoundException(createDto.trainerUsername());
                });
        TrainingType trainingType = trainingTypeRepository.findByName(createDto.type())
                .orElseThrow(() -> {
                    log.warn("Training type not found: {}", createDto.type());
                    return new TrainingTypeNotFoundException("Training type not found");
                });

        Training training = trainingMapper.toEntity(createDto);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setDate(new Date());
        trainingRepository.save(training);
        log.info("Training created successfully with ID: {}", training.getId());
    }
}
