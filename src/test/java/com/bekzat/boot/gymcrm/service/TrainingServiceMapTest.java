package com.bekzat.boot.gymcrm.service;

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
import com.bekzat.boot.gymcrm.service.map.TrainingServiceMap;
import com.bekzat.boot.gymcrm.service.map.UserCredentialsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceMapTest {
    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private UserCredentialsService userCredentialsService;

    @Spy
    private TrainingMapper trainingMapper = Mappers.getMapper(TrainingMapper.class);

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingServiceMap trainingServiceMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTrainingWithValidCredentials() {

        Training training = new Training();
        CredentialsDto credentialsDto = new CredentialsDto("testUser", "password");
        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(true);
        when(trainingRepository.save(training)).thenReturn(training);

        Training result = trainingServiceMap.create(training, credentialsDto);

        assertEquals(training, result);
        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void createTrainingWithInvalidCredentials() {

        Training training = new Training();
        CredentialsDto credentialsDto = new CredentialsDto("testUser", "wrongPassword");
        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(false);


        assertThrows(AuthenticationException.class, () -> trainingServiceMap.create(training, credentialsDto));
        verify(trainingRepository, times(0)).save(training);
    }

    @Test
    void deleteTrainingWithValidCredentials() {

        Long id = 1L;
        Training training = new Training();
        CredentialsDto credentialsDto = new CredentialsDto("testUser", "password");
        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(true);
        when(trainingRepository.findById(id)).thenReturn(Optional.of(training));

        boolean result = trainingServiceMap.delete(id, credentialsDto);

        assertTrue(result);
        verify(trainingRepository, times(1)).delete(training);
    }

    @Test
    void deleteTrainingWithInvalidCredentials() {

        Long id = 1L;
        Training training = new Training();
        CredentialsDto credentialsDto = new CredentialsDto("testUser", "wrongPassword");
        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(false);

        assertThrows(AuthenticationException.class, () -> trainingServiceMap.delete(id, credentialsDto));
        verify(trainingRepository, times(0)).delete(training);
    }

    @Test
    void createTraining() {

        String traineeUsername = "testTrainee";
        String trainerUsername = "testTrainer";
        TrainingCreateDto createDto = new TrainingCreateDto(traineeUsername, trainerUsername, "Test Training", TrainingType.Type.CARDIO, 60);
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        TrainingType trainingType = new TrainingType();

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(trainerUsername)).thenReturn(Optional.of(trainer));
        when(trainingTypeRepository.findByName(TrainingType.Type.CARDIO)).thenReturn(Optional.of(trainingType));
        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setName(createDto.name());
        training.setDuration(createDto.duration());

        when(trainingMapper.toEntity(createDto)).thenReturn(training);
        when(trainingRepository.save(training)).thenReturn(training);

        trainingServiceMap.create(createDto);

        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void createTrainingWithNonExistingTrainee() {

        String traineeUsername = "nonExistentTrainee";
        String trainerUsername = "testTrainer";
        TrainingCreateDto createDto = new TrainingCreateDto(traineeUsername, trainerUsername, "Test Training", TrainingType.Type.CARDIO, 60);
        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> trainingServiceMap.create(createDto));
        verify(trainingRepository, times(0)).save(any());
    }

    @Test
    void createTrainingWithNonExistingTrainer() {

        String traineeUsername = "testTrainee";
        String trainerUsername = "nonExistentTrainer";
        TrainingCreateDto createDto = new TrainingCreateDto(traineeUsername, trainerUsername, "Test Training", TrainingType.Type.CARDIO, 60);
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(trainerUsername)).thenReturn(Optional.empty());


        assertThrows(TrainerNotFoundException.class, () -> trainingServiceMap.create(createDto));
        verify(trainingRepository, times(0)).save(any());
    }

    @Test
    void createTrainingWithNonExistingTrainingType() {

        String traineeUsername = "testTrainee";
        String trainerUsername = "testTrainer";
        TrainingCreateDto createDto = new TrainingCreateDto(traineeUsername, trainerUsername, "Test Training", TrainingType.Type.CARDIO, 60);
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(trainerUsername)).thenReturn(Optional.of(trainer));
        when(trainingTypeRepository.findByName(TrainingType.Type.CARDIO)).thenReturn(Optional.empty());


        assertThrows(TrainingTypeNotFoundException.class, () -> trainingServiceMap.create(createDto));
        verify(trainingRepository, times(0)).save(any());
    }
}
