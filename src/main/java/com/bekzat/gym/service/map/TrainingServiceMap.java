package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TrainingRepository;
import com.bekzat.gym.dto.CredentialsDto;
import com.bekzat.gym.dto.TrainingReadDto;
import com.bekzat.gym.exceptions.AuthenticationException;
import com.bekzat.gym.exceptions.TrainingNotFoundException;
import com.bekzat.gym.mapper.TrainingMapper;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.service.TrainingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingServiceMap implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final UserCredentialsService userCredentialsService;
    private final TrainingMapper trainingMapper;

    public TrainingReadDto findById(Long id, CredentialsDto credentialsDto) {
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        log.info("Finding training by ID: {}", id);
        return trainingRepository.findById(id)
                .map(trainingMapper::toDto)
                .orElseThrow(() -> new TrainingNotFoundException(id));
    }
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
}
