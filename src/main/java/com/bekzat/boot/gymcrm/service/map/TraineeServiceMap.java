package com.bekzat.boot.gymcrm.service.map;

import com.bekzat.boot.gymcrm.dto.*;
import com.bekzat.boot.gymcrm.exceptions.TraineeNotFoundException;
import com.bekzat.boot.gymcrm.exceptions.UserNotFoundException;
import com.bekzat.boot.gymcrm.mapstruct.TraineeMapper;
import com.bekzat.boot.gymcrm.mapstruct.TrainerMapper;
import com.bekzat.boot.gymcrm.mapstruct.TrainingMapper;
import com.bekzat.boot.gymcrm.model.Trainee;
import com.bekzat.boot.gymcrm.model.Trainer;
import com.bekzat.boot.gymcrm.model.Training;
import com.bekzat.boot.gymcrm.repository.TraineeRepository;
import com.bekzat.boot.gymcrm.repository.TrainerRepository;
import com.bekzat.boot.gymcrm.repository.TrainingRepository;
import com.bekzat.boot.gymcrm.service.TraineeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TraineeServiceMap implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final UserCredentialsService userCredentialsService;
    private final TraineeMapper traineeMapper;
    private final TrainingMapper trainingMapper;
    private final TrainerMapper trainerMapper;

    @Transactional
    @Override
    public void delete(String username) {
        log.info("Deleting trainee: {}", username);
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Trainee with username: {} not found", username);
                    return new TraineeNotFoundException(username);
                });
        trainee.getTrainers().forEach(trainer -> trainer.getTrainees().remove(trainee));
        trainee.getTrainings().clear();

        traineeRepository.deleteById(trainee.getId());
        log.info("Trainee {} deleted successfully", username);
    }

    @Transactional
    @Override
    public void changeActiveStatus(String username, StatusUpdateDto statusUpdateDto) {
        log.info("Changing active status for trainee: {}", username);
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Trainee with username: {} not found", username);
                    return new TraineeNotFoundException(username);
                });
        trainee.setIsActive(statusUpdateDto.isActive());
        traineeRepository.save(trainee);
        log.info("Trainee {} active status changed to {}", username, statusUpdateDto.isActive());
    }

    @Transactional
    @Override
    public List<TrainingReadDto> getTraineeTrainings(String trainerUsername, LocalDateTime from, LocalDateTime to, String traineeName) {
        log.info("Fetching trainings for trainee: {} with trainer: {} from {} to {}", traineeName, trainerUsername, from, to);
        List<Training> trainings = trainingRepository.findTrainingsByTraineeAndPeriodAndTrainer(trainerUsername, from, to, traineeName);
        List<TrainingReadDto> trainingDtos = trainingMapper.toDTOList(trainings);
        log.info("Found {} trainings", trainingDtos.size());
        return trainingDtos;
    }

    @Transactional
    @Override
    public CredentialsDto register(TraineeRegistrationDto registrationDto) {
        log.info("Registering trainee: {}", registrationDto.firstName());
        Trainee trainee = traineeMapper.toEntity(registrationDto);
        String username = userCredentialsService.generateUsername(trainee.getFirstName(), trainee.getLastName());
        String password = userCredentialsService.generateRandomPassword();
        trainee.setUsername(username);
        trainee.setPassword(password);
        traineeRepository.save(trainee);
        log.info("Trainee registered with username: {}", username);
        return new CredentialsDto(username, password);
    }

    @Transactional
    @Override
    public TraineeProfileReadDto update(String username, TraineeCreateAndUpdateDto dto) {
        log.info("Updating trainee: {}", username);
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User with username: {} not found", username);
                    return new UserNotFoundException("User with username: " + username + " not found");
                });

        trainee.setFirstName(dto.firstName());
        trainee.setLastName(dto.lastName());
        trainee.setAddress(dto.address());
        trainee.setDateOfBirth(dto.dateOfBirth());
        trainee.setIsActive(dto.isActive());
        traineeRepository.save(trainee);

        List<TrainerReadDto> trainers = trainerMapper.toDTOList(trainee.getTrainers());
        TraineeProfileReadDto updatedProfile = traineeMapper.toTraineeProfileDto(trainee);
        updatedProfile.trainers().addAll(trainers);

        log.info("Trainee {} updated successfully", username);
        return updatedProfile;
    }

    @Transactional
    @Override
    public TraineeProfileReadDto findTraineeByUsername(String username) {
        log.info("Finding trainee by username: {}", username);
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User with username: {} not found", username);
                    return new UserNotFoundException(username);
                });

        List<TrainerReadDto> trainers = trainerMapper.toDTOList(trainee.getTrainers());
        TraineeProfileReadDto traineeProfileReadDto = traineeMapper.toTraineeProfileDto(trainee);
        traineeProfileReadDto.trainers().addAll(trainers);
        log.info("Trainee found: {}", traineeProfileReadDto);
        return traineeProfileReadDto;
    }
    @Transactional
    @Override
    public TraineeProfileReadDto updateTraineeTrainers(String username, List<String> trainerUsernames) {
        log.info("Updating trainers for trainee: {}", username);
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Trainee with username: {} not found", username);
                    return new TraineeNotFoundException(username);
                });

        List<Trainer> trainers = trainerRepository.findAllTrainersByUsername(trainerUsernames);
        trainee.setTrainers(trainers);
        traineeRepository.save(trainee);
        log.info("Trainers updated for trainee: {}", username);
        return findTraineeByUsername(username);
    }

}
