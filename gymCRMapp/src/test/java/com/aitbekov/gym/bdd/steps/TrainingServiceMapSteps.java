package com.aitbekov.gym.bdd.steps;

import com.aitbekov.gym.dto.TrainingCreateDto;
import com.aitbekov.gym.exceptions.TraineeNotFoundException;
import com.aitbekov.gym.exceptions.TrainerNotFoundException;
import com.aitbekov.gym.mapstruct.TrainingMapper;
import com.aitbekov.gym.model.Trainee;
import com.aitbekov.gym.model.Trainer;
import com.aitbekov.gym.model.Training;
import com.aitbekov.gym.model.TrainingType;
import com.aitbekov.gym.repository.TraineeRepository;
import com.aitbekov.gym.repository.TrainerRepository;
import com.aitbekov.gym.repository.TrainingRepository;
import com.aitbekov.gym.repository.TrainingTypeRepository;
import com.aitbekov.gym.service.map.TrainingServiceMap;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TrainingServiceMapSteps {

    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingServiceMap trainingServiceMap;

    private TrainingCreateDto trainingCreateDto;
    private Trainee trainee;
    private Trainer trainer;
    private TrainingType trainingType;
    private Training training;

    public TrainingServiceMapSteps() {
        MockitoAnnotations.openMocks(this);
    }

    private void setupTrainingCreateRequest(String traineeUsername, String trainerUsername, String type, boolean isTypeValid) {
        trainee = new Trainee();
        trainee.setUsername(traineeUsername);

        trainer = new Trainer();
        trainer.setUsername(trainerUsername);

        if (isTypeValid) {
            trainingType = new TrainingType();
            trainingType.setName(TrainingType.Type.valueOf(type.toUpperCase()));
        } else {
            trainingType = null;
        }

        trainingCreateDto = new TrainingCreateDto(
                traineeUsername,
                trainerUsername,
                "Yoga Training",
                trainingType != null ? trainingType.getName() : null,
                60
        );

        training = new Training();
        training.setDate(new Date());

        Mockito.when(trainingMapper.toEntity(trainingCreateDto)).thenReturn(training);
    }
    @Given("a training create request with trainee {string}, trainer {string}, and training type {string}")
    public void aTrainingCreateRequest(String traineeUsername, String trainerUsername, String type) {
        setupTrainingCreateRequest(traineeUsername, trainerUsername, type, true);
    }

    @Given("a non-existent trainee with username {string}")
    public void aNonExistentTrainee(String username) {
        Mockito.when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());
        setupTrainingCreateRequest(username, "trainer1", "YOGA", true);
    }

    @Given("a non-existent trainer with username {string}")
    public void aNonExistentTrainer(String username) {
        Mockito.when(trainerRepository.findByUsername(username)).thenReturn(Optional.empty());
        setupTrainingCreateRequest("john_doe", username, "YOGA", true);
    }

    @Given("a non-existent training type {string}")
    public void aNonExistentTrainingType(String type) {
        Mockito.when(trainingTypeRepository.findByName(Mockito.any())).thenReturn(Optional.empty());
        setupTrainingCreateRequest("john_doe", "trainer1", type, false);
    }

    @When("the training is created")
    public void createTraining() {
        Mockito.when(traineeRepository.findByUsername(trainingCreateDto.traineeUsername())).thenReturn(Optional.of(trainee));
        Mockito.when(trainerRepository.findByUsername(trainingCreateDto.trainerUsername())).thenReturn(Optional.of(trainer));
        Mockito.when(trainingTypeRepository.findByName(trainingCreateDto.type())).thenReturn(Optional.of(trainingType));

        trainingServiceMap.create(trainingCreateDto);
    }

    @When("the training with non-existing trainee is created")
    public void createNonExistingTraineeTraining() {
        Mockito.when(traineeRepository.findByUsername(trainingCreateDto.traineeUsername())).thenReturn(Optional.empty());
        assertThrows(TraineeNotFoundException.class, () -> trainingServiceMap.create(trainingCreateDto));
    }

    @When("the training with non-existing trainer is created")
    public void createNonExistingTrainerTraining() {
        Mockito.when(traineeRepository.findByUsername(trainingCreateDto.traineeUsername())).thenReturn(Optional.of(trainee));
        Mockito.when(trainerRepository.findByUsername(trainingCreateDto.trainerUsername())).thenReturn(Optional.empty());
        assertThrows(TrainerNotFoundException.class, () -> trainingServiceMap.create(trainingCreateDto));
    }

    @Then("the training should be saved")
    public void verifyTrainingSaved() {
        verify(trainingRepository).save(training);
    }

    @Then("an error should be thrown for missing trainee")
    public void verifyTraineeNotFoundError() {
        assertThrows(TraineeNotFoundException.class, () -> trainingServiceMap.create(trainingCreateDto));
    }

    @Then("an error should be thrown for missing trainer")
    public void verifyTrainerNotFoundError() {
        assertThrows(TrainerNotFoundException.class, () -> trainingServiceMap.create(trainingCreateDto));
    }
}