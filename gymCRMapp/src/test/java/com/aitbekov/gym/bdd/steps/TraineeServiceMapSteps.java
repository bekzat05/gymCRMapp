package com.aitbekov.gym.bdd.steps;

import com.aitbekov.gym.dto.*;
import com.aitbekov.gym.mapstruct.TraineeMapper;
import com.aitbekov.gym.mapstruct.TrainerMapper;
import com.aitbekov.gym.mapstruct.TrainingMapper;
import com.aitbekov.gym.model.Trainee;
import com.aitbekov.gym.model.Trainer;
import com.aitbekov.gym.model.Training;
import com.aitbekov.gym.repository.TraineeRepository;
import com.aitbekov.gym.repository.TrainerRepository;
import com.aitbekov.gym.repository.TrainingRepository;
import com.aitbekov.gym.repository.UserRepository;
import com.aitbekov.gym.service.map.TraineeServiceMap;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@CucumberContextConfiguration
public class TraineeServiceMapSteps {

    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingMapper trainingMapper;
    @Mock
    private TraineeMapper traineeMapper;
    @Mock
    private TrainerMapper trainerMapper;
    @InjectMocks
    private TraineeServiceMap traineeServiceMap;
    private Trainee trainee;
    private Set<Trainer> trainers;
    private TraineeProfileReadDto profileReadDto;
    public TraineeServiceMapSteps() {
        MockitoAnnotations.openMocks(this);
        initTestData();
    }
    private void initTestData() {
        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUsername("john.doe");
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setIsActive(true);
        trainee.setTrainers(new HashSet<>());

        profileReadDto = new TraineeProfileReadDto(
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getDateOfBirth(),
                trainee.getAddress(),
                trainee.getIsActive(),
                new ArrayList<>()
        );
    }

    private void mockTraineeRepositoryFindByUsername(String username) {
        Mockito.when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        Mockito.when(traineeMapper.toProfileReadDto(trainee)).thenReturn(profileReadDto);
    }

    @Given("a trainee exists with username {string}")
    public void aTraineeExistsWithUsername(String username) {
        trainee.setUsername(username);
        mockTraineeRepositoryFindByUsername(username);
    }

    @When("the trainee with username {string} is deleted")
    public void deleteTrainee(String username) {
        Mockito.doNothing().when(userRepository).deleteById(trainee.getId());
        Mockito.doNothing().when(trainingRepository).deleteAllByTrainee(trainee);
        traineeServiceMap.delete(username);
    }

    @Then("the trainee with username {string} should not exist in the system")
    public void traineeShouldNotExist(String username) {
        verify(traineeRepository).deleteById(trainee.getId());
    }

    @When("I change the active status of trainee {string} to {string}")
    public void changeActiveStatus(String username, String isActive) {
        mockTraineeRepositoryFindByUsername(username);
        traineeServiceMap.changeActiveStatus(username, new StatusUpdateDto(Boolean.parseBoolean(isActive)));
    }

    @Then("the active status of trainee {string} should be {string}")
    public void verifyActiveStatus(String username, String expectedStatus) {
        verify(traineeRepository).save(trainee);
        assertEquals(Boolean.parseBoolean(expectedStatus), trainee.getIsActive());
    }

    @When("I update trainee {string} with first name {string}, last name {string}, address {string}, and date of birth {string}")
    public void updateTrainee(String username, String firstName, String lastName, String address, String dateOfBirth) {
        mockTraineeRepositoryFindByUsername(username);
        TraineeCreateAndUpdateDto dto = new TraineeCreateAndUpdateDto(username, firstName, lastName, address, Date.valueOf(dateOfBirth), true);

        Mockito.when(trainerMapper.toDtoList(trainee.getTrainers())).thenReturn(new HashSet<>());
        Mockito.when(traineeMapper.toProfileReadDto(trainee)).thenReturn(profileReadDto);

        traineeServiceMap.update(username, dto);
    }

    @Then("the details of trainee {string} should be updated")
    public void verifyTraineeDetails(String username) {
        verify(traineeRepository).save(trainee);
        assertEquals("John", trainee.getFirstName());
        assertEquals("Doe", trainee.getLastName());
    }

    @When("I search for trainee with username {string}")
    public void findTraineeByUsername(String username) {
        mockTraineeRepositoryFindByUsername(username);
        traineeServiceMap.findTraineeByUsername(username);
    }

    @Then("the trainee profile should be returned")
    public void verifyTraineeProfile() {
        verify(traineeRepository).findByUsername(Mockito.anyString());
        verify(traineeMapper).toProfileReadDto(trainee);
        assertEquals(trainee.getFirstName(), profileReadDto.firstName());
        assertEquals(trainee.getLastName(), profileReadDto.lastName());
    }

    @When("I update trainers for trainee {string} with {string} and {string}")
    public void updateTraineeTrainers(String username, String trainer1, String trainer2) {
        trainers = new HashSet<>();
        Trainer t1 = new Trainer();
        t1.setUsername(trainer1);
        Trainer t2 = new Trainer();
        t2.setUsername(trainer2);
        trainers.add(t1);
        trainers.add(t2);

        Mockito.when(trainerRepository.findAllTrainersByUsername(Mockito.anyList())).thenReturn(trainers);
        mockTraineeRepositoryFindByUsername(username);

        traineeServiceMap.updateTraineeTrainers(username, List.of(trainer1, trainer2));
    }

    @Then("the trainers of trainee {string} should be updated")
    public void verifyUpdatedTrainers(String username) {
        verify(traineeRepository).save(trainee);
        assertTrue(trainee.getTrainers().containsAll(trainers));
    }
}