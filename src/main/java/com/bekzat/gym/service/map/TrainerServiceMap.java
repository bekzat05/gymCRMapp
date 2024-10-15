package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TrainerRepository;
import com.bekzat.gym.dao.TrainingRepository;
import com.bekzat.gym.dao.TrainingTypeRepository;
import com.bekzat.gym.dao.UserRepository;
import com.bekzat.gym.dto.*;
import com.bekzat.gym.exceptions.AuthenticationException;
import com.bekzat.gym.exceptions.TrainerNotFoundException;
import com.bekzat.gym.exceptions.UserNotFoundException;
import com.bekzat.gym.mapper.TraineeMapper;
import com.bekzat.gym.mapper.TrainerMapper;
import com.bekzat.gym.mapper.TrainingMapper;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.model.TrainingType;
import com.bekzat.gym.service.TrainerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TrainerServiceMap implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final UserCredentialsService userCredentialsService;
    private final TrainerMapper trainerMapper;
    private final TraineeMapper traineeMapper;
    private final TrainingMapper trainingMapper;

    @Transactional
    @Override
    public CredentialsDto register(TrainerRegistrationDto registrationDto) {

        Trainer trainerEntity = trainerMapper.toEntity(registrationDto);
        String username = userCredentialsService.generateUsername(trainerEntity.getFirstName(), trainerEntity.getLastName());
        String password = userCredentialsService.generateRandomPassword();
        trainerEntity.setUsername(username);
        trainerEntity.setPassword(password);
        trainerRepository.save(trainerEntity);

        return new CredentialsDto(username, password);
    }

    @Transactional
    @Override
    public TrainerProfileReadDto findTrainerByUsername(String username) {
        Trainer trainer = trainerRepository.findByUsername(username).orElse(null);
        if (trainer != null) {
            List<TrainingType.Type> specializations = TrainerMapper.mapSpecializations(trainer.getSpecializations());
            List<TraineeReadDto> traineesList = (trainer.getTrainees() != null && !trainer.getTrainees().isEmpty())
                    ? trainer.getTrainees().stream()
                    .map(traineeMapper::toDto)
                    .collect(Collectors.toList())
                    : Collections.emptyList();
            return new TrainerProfileReadDto(trainer.getUsername(), trainer.getFirstName(), trainer.getLastName(),
                    specializations, traineesList);
        }
        return null;
    }

    @Transactional
    @Override
    public TrainerProfileReadDto update(String username, TrainerCreateDto dto) {

        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer not found with username: " + username));

        trainer.setFirstName(dto.firstName());
        trainer.setLastName(dto.lastName());
        trainer.setIsActive(dto.isActive());
        List<TrainingType> specializations = trainingTypeRepository.findByByNames(dto.specializations());
        trainer.setSpecializations(specializations);

        trainerRepository.save(trainer);


        return findTrainerByUsername(username);
    }

    @Transactional
    @Override
    public List<TrainerReadDto> findAll() {
        List<Trainer> trainers = trainerRepository.findAll();
        return trainerMapper.toDTOList(trainers);
    }

    @Transactional
    @Override
    public void changeActiveStatus(String username, StatusUpdateDto dto) {
        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> new TrainerNotFoundException(username));
        trainer.setIsActive(dto.isActive());
        trainerRepository.save(trainer);
    }

    @Transactional
    @Override
    public List<TrainingReadDto> getTrainerTrainings(String trainerUsername, LocalDateTime fromDate, LocalDateTime toDate, String traineeName) {
        List<Training> trainings = trainingRepository.findTrainingsByTrainerAndPeriodAndTrainee(trainerUsername, fromDate, toDate, traineeName);
        log.info("Trainings found: {}", trainings);
        return trainingMapper.toDTOList(trainings);
    }

    @Transactional
    @Override
    public List<TrainerReadDto> getTrainersNotAssignedToTrainee(String traineeUsername) {
        List<Trainer> trainers = trainerRepository.findTrainersNotAssignedToTrainee(traineeUsername);
        return trainerMapper.toDTOList(trainers);
    }


    @Transactional
    public void changeTrainerPassword(Long trainerId, String newPassword, CredentialsDto credentialsDto) {
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        log.info("Checking password for trainer with trainer id: {}", trainerId);
        userCredentialsService.changePassword(trainerId, newPassword);
    }
}
