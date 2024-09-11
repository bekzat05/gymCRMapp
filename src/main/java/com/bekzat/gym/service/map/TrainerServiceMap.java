package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TrainerRepository;
import com.bekzat.gym.dao.UserRepository;
import com.bekzat.gym.dto.CredentialsDto;
import com.bekzat.gym.dto.TrainerCreateDto;
import com.bekzat.gym.dto.TrainerReadDto;
import com.bekzat.gym.exceptions.AuthenticationException;
import com.bekzat.gym.exceptions.TrainerNotFoundException;
import com.bekzat.gym.exceptions.UserNotFoundException;
import com.bekzat.gym.mapper.TrainerMapper;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.service.TrainerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TrainerServiceMap implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final UserCredentialsService userCredentialsService;
    private final TrainerMapper trainerMapper;

    public TrainerReadDto findById(Long id, CredentialsDto credentialsDto) {
        log.info("Finding trainer by ID: {}", id);
        return trainerRepository.findById(id)
                .map(trainerMapper::toDto)
                .orElseThrow(() -> new TrainerNotFoundException(id));
    }
    @Transactional
    public CredentialsDto create(TrainerCreateDto trainer) {
        Trainer trainerEntity = trainerMapper.toEntity(trainer);
        String username = userCredentialsService.generateUsername(trainer.firstName(), trainer.lastName());
        String password = userCredentialsService.generateRandomPassword();
        trainerEntity.setUsername(username);
        trainerEntity.setPassword(password);
        trainerRepository.save(trainerEntity);

        return new CredentialsDto(username, password);
    }
    @Transactional
    public void changeTrainerPassword(Long trainerId, String newPassword, CredentialsDto credentialsDto) {
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        log.info("Checking password for trainer with trainer id: {}", trainerId);
        userCredentialsService.changePassword(trainerId, newPassword);
    }
    @Transactional
    public boolean checkTrainerCredentials(String username, String password, CredentialsDto credentialsDto) {
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        log.info("Checking credentials for trainer with username: {}", username);
        return userRepository.checkCredentials(username, password);
    }
    @Transactional
    public TrainerReadDto findTrainerByUsername(String username, CredentialsDto credentialsDto) {

        log.info("Finding trainer by username: {}", username);
        return trainerRepository.findByUsername(username)
                .map(trainerMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
    @Transactional
    public void activateTrainer(Long id, CredentialsDto credentialsDto) {
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new TrainerNotFoundException(id));
        if (!trainer.getIsActive()) {
            trainer.setIsActive(true);
            trainerRepository.update(trainer);
        }
    }
    @Transactional
    public void deactivateTrainer(Long id, CredentialsDto credentialsDto) {
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new TrainerNotFoundException(id));
        if (trainer.getIsActive()) { // Проверка, что тренер активирован
            trainer.setIsActive(false);
            trainerRepository.update(trainer);
        }
    }
}
