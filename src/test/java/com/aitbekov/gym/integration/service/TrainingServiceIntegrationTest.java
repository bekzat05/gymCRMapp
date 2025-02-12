package com.aitbekov.gym.integration.service;

import com.aitbekov.gym.dto.CredentialsDto;
import com.aitbekov.gym.dto.TrainingCreateDto;
import com.aitbekov.gym.exceptions.TraineeNotFoundException;
import com.aitbekov.gym.exceptions.TrainerNotFoundException;
import com.aitbekov.gym.integration.IntegrationTestBase;
import com.aitbekov.gym.integration.annotation.IT;
import com.aitbekov.gym.model.Training;
import com.aitbekov.gym.model.TrainingType;
import com.aitbekov.gym.repository.TrainingRepository;
import com.aitbekov.gym.service.map.TrainingServiceMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
public class TrainingServiceIntegrationTest extends IntegrationTestBase {

    @Autowired
    private TrainingServiceMap trainingService;

    @Autowired
    private TrainingRepository trainingRepository;

    @Test
    public void testCreateTraining_success() {
        // Arrange
        TrainingCreateDto createDto = new TrainingCreateDto(
                "john.doe",
                "alice.johnson",
                "Cardio-training",
                TrainingType.Type.CARDIO,
                33
        );

        // Act
        trainingService.create(createDto);

        // Assert
        Optional<Training> training = trainingRepository.findAll().stream().findFirst();
        assertTrue(training.isPresent());
        assertEquals("john.doe", training.get().getTrainee().getUsername());
        assertEquals("alice.johnson", training.get().getTrainer().getUsername());
        assertEquals(TrainingType.Type.CARDIO, training.get().getTrainingType().getName());
    }

    @Test
    public void testCreateTraining_traineeNotFound() {
        // Arrange
        TrainingCreateDto createDto = new TrainingCreateDto(
                "john.dode",
                "alice.johnson",
                "Cardio-training",
                TrainingType.Type.CARDIO,
                33
        );

        // Act & Assert
        assertThrows(TraineeNotFoundException.class, () -> {
            trainingService.create(createDto);
        });
    }

    @Test
    public void testCreateTraining_trainerNotFound() {
        // Arrange
        TrainingCreateDto createDto = new TrainingCreateDto(
                "john.doe",
                "alice.johnason",
                "Cardio-training",
                TrainingType.Type.CARDIO,
                33
        );

        // Act & Assert
        assertThrows(TrainerNotFoundException.class, () -> {
            trainingService.create(createDto);
        });
    }

}
