package com.aitbekov.gym.service.map;

import com.aitbekov.gym.dto.*;
import com.aitbekov.gym.exceptions.TraineeNotFoundException;
import com.aitbekov.gym.exceptions.UserNotFoundException;
import com.aitbekov.gym.repository.TraineeRepository;
import com.aitbekov.gym.repository.TrainerRepository;
import com.aitbekov.gym.repository.TrainingRepository;
import com.aitbekov.gym.mapstruct.TraineeMapper;
import com.aitbekov.gym.mapstruct.TrainerMapper;
import com.aitbekov.gym.mapstruct.TrainingMapper;
import com.aitbekov.gym.model.Trainee;
import com.aitbekov.gym.model.Trainer;
import com.aitbekov.gym.model.Training;
import com.aitbekov.gym.service.TraineeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TraineeServiceMap implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainingMapper trainingMapper;

    @Override
    @Transactional
    public TraineeProfileReadDto findTraineeByUsername(String username) {
        log.info("Finding trainee by username: {}", username);
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));


        List<TrainerReadDto> trainers = trainerMapper.toDtoList(trainee.getTrainers());
        TraineeProfileReadDto traineeProfileReadDto = traineeMapper.toProfileReadDto(trainee);
        traineeProfileReadDto.trainers().addAll(trainers);
        log.info("Trainee found: {}", traineeProfileReadDto);
        return traineeProfileReadDto;
    }

    @Override
    @Transactional
    public TraineeProfileReadDto update(String username, TraineeCreateAndUpdateDto dto) {
        log.info("Updating trainee: {}", username);
        Trainee existingTrainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new TraineeNotFoundException(username));

        existingTrainee.setFirstName(dto.firstName());
        existingTrainee.setLastName(dto.lastName());
        existingTrainee.setAddress(dto.address());
        existingTrainee.setDateOfBirth(dto.dateOfBirth());
        existingTrainee.setIsActive(dto.isActive());

        traineeRepository.save(existingTrainee);

        List<TrainerReadDto> trainers = trainerMapper.toDtoList(existingTrainee.getTrainers());
        TraineeProfileReadDto updatedProfile = traineeMapper.toProfileReadDto(existingTrainee);
        updatedProfile.trainers().addAll(trainers);

        log.info("Trainee {} updated successfully", username);
        return updatedProfile;
    }

    @Override
    @Transactional
    public void delete(String traineeUsername) {
        log.info("Deleting trainee: {}", traineeUsername);

        Trainee traineeToDelete = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> {
                    log.warn("Trainee not found: {}", traineeUsername);
                    return new TraineeNotFoundException(traineeUsername);
                });

        traineeToDelete.getTrainers().forEach(trainer -> trainer.getTrainees().remove(traineeToDelete));
        traineeToDelete.getTrainings().clear();

        traineeRepository.deleteById(traineeToDelete.getId());
        log.info("Trainee {} deleted successfully", traineeUsername);
    }

    @Override
    @Transactional
    public void changeActiveStatus(String username, StatusUpdateDto statusUpdateDto) {
        log.info("Changing active status for trainee: {}", username);
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new TraineeNotFoundException(username));
        trainee.setIsActive(statusUpdateDto.isActive());
        traineeRepository.save(trainee);
        log.info("Trainee {} active status changed to {}", username, statusUpdateDto.isActive());
    }

    @Transactional
    @Override
    public List<TrainingReadDto> getTraineeTrainings(String traineeUsername) {
        log.info("Fetching trainings for trainee: {}", traineeUsername);
        List<Training> trainings = trainingRepository.findTrainingsByTraineeUsername(traineeUsername);
        List<TrainingReadDto> trainingDtos = trainingMapper.toDtoList(trainings);
        log.info("Found {} trainings", trainingDtos.size());
        return trainingDtos;
    }


    @Override
    @Transactional
    public TraineeProfileReadDto updateTraineeTrainers(String username, List<String> trainers) {
        log.info("Updating trainers for trainee: {}", username);
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new TraineeNotFoundException(username));

        List<Trainer> trainersByUsername = trainerRepository.findAllTrainersByUsername(trainers);
        trainee.setTrainers(trainersByUsername);
        traineeRepository.save(trainee);

        log.info("Trainers updated for trainee: {}", username);
        return findTraineeByUsername(username);
    }
}