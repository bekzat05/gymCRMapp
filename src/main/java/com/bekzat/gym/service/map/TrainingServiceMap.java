package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TraineeRepository;
import com.bekzat.gym.dao.TrainerRepository;
import com.bekzat.gym.dao.TrainingRepository;
import com.bekzat.gym.dao.TrainingTypeRepository;
import com.bekzat.gym.dto.CredentialsDto;
import com.bekzat.gym.dto.TrainingCreateDto;
import com.bekzat.gym.dto.TrainingReadDto;
import com.bekzat.gym.exceptions.*;
import com.bekzat.gym.mapper.TrainingMapper;
import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.model.TrainingType;
import com.bekzat.gym.service.TrainingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        log.info("Saving training: {}", training);
        return trainingRepository.save(training);
    }

    @Transactional
    public boolean delete(Long id, CredentialsDto credentialsDto) {
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        Optional<Training> maybeTraining = trainingRepository.findById(id);
        maybeTraining.ifPresent(training -> trainingRepository.delete(training.getId()));
        return maybeTraining.isPresent();
    }

    @Transactional
    @Override
    public void create(TrainingCreateDto createDto) {
        Trainee trainee = traineeRepository.findByUsername(createDto.traineeUsername())
                .orElseThrow(() -> new TraineeNotFoundException(createDto.traineeUsername()));
        Trainer trainer = trainerRepository.findByUsername(createDto.trainerUsername())
                .orElseThrow(() -> new TrainerNotFoundException(createDto.trainerUsername()));
        TrainingType trainingType = trainingTypeRepository.findByName(createDto.type())
                .orElseThrow(() -> new TrainingTypeNotFoundException("Training type not found"));

        Training training = trainingMapper.toEntity(createDto);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setDate(new Date());
        trainingRepository.save(training);
    }
}
