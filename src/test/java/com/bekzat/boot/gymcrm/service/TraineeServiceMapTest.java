package com.bekzat.boot.gymcrm.service;

import com.bekzat.boot.gymcrm.dto.*;
import com.bekzat.boot.gymcrm.exceptions.TraineeNotFoundException;
import com.bekzat.boot.gymcrm.exceptions.UserNotFoundException;
import com.bekzat.boot.gymcrm.mapstruct.*;
import com.bekzat.boot.gymcrm.model.*;
import com.bekzat.boot.gymcrm.repository.*;
import com.bekzat.boot.gymcrm.service.map.TraineeServiceMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TraineeServiceMapTest {

    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Spy
    private TraineeMapper traineeMapper = Mappers.getMapper(TraineeMapper.class);

    @Spy
    private TrainerMapper trainerMapper = Mappers.getMapper(TrainerMapper.class);
    @Spy
    private TrainingMapper trainingMapper = Mappers.getMapper(TrainingMapper.class);
    @InjectMocks
    private TraineeServiceMap traineeServiceMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteTraineeThatExists() {

        String username = "testUser";
        Trainee trainee = new Trainee();
        trainee.setUsername(username);
        trainee.setTrainers(new ArrayList<>());
        trainee.setTrainings(new ArrayList<>());
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));


        traineeServiceMap.delete(username);

        verify(traineeRepository, times(1)).deleteById(trainee.getId());
        verify(traineeRepository, times(1)).findByUsername(username);
    }

    @Test
    void deleteTraineeThatDoesNotExist() {

        String username = "nonExistentUser";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> traineeServiceMap.delete(username));
    }

    @Test
    void changeActiveStatusForExistingTrainee() {
        // Given
        String username = "testUser";
        Trainee trainee = new Trainee();
        trainee.setUsername(username);
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        StatusUpdateDto statusUpdateDto = new StatusUpdateDto(false);

        // When
        traineeServiceMap.changeActiveStatus(username, statusUpdateDto);

        // Then
        assertEquals(false, trainee.getIsActive());
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    void changeActiveStatusForNonExistingTrainee() {
        // Given
        String username = "nonExistentUser";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());
        StatusUpdateDto statusUpdateDto = new StatusUpdateDto(false);

        // When/Then
        assertThrows(TraineeNotFoundException.class, () -> traineeServiceMap.changeActiveStatus(username, statusUpdateDto));
    }

    @Test
    void getTraineeTrainingsForNonExistingTrainee() {
        // Given
        String traineeName = "nonExistentTrainee";
        String trainerUsername = "testTrainer";
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now();
        when(trainingRepository.findTrainingsByTraineeAndPeriodAndTrainer(trainerUsername, from, to, traineeName))
                .thenReturn(List.of());

        // When
        List<TrainingReadDto> result = traineeServiceMap.getTraineeTrainings(trainerUsername, from, to, traineeName);

        // Then
        assertTrue(result.isEmpty());
        verify(trainingRepository, times(1)).findTrainingsByTraineeAndPeriodAndTrainer(trainerUsername, from, to, traineeName);
    }


    @Test
    void updateExistingTrainee() {
        // Given
        String username = "testUser";
        Trainee trainee = new Trainee();
        trainee.setUsername(username);
        trainee.setTrainers(new ArrayList<>());  // Убедимся, что trainers не null и изменяем
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        TraineeCreateAndUpdateDto dto = new TraineeCreateAndUpdateDto(username, "John", "Doe", "123 Street", null, true);

        // When
        TraineeProfileReadDto result = traineeServiceMap.update(username, dto);

        // Then
        assertEquals("John", trainee.getFirstName());
        assertEquals("Doe", trainee.getLastName());
        assertEquals("123 Street", trainee.getAddress());
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    void updateNonExistingTrainee() {
        // Given
        String username = "nonExistentUser";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());
        TraineeCreateAndUpdateDto dto = new TraineeCreateAndUpdateDto(username, "John", "Doe", "123 Street", null, true);

        // When/Then
        assertThrows(UserNotFoundException.class, () -> traineeServiceMap.update(username, dto));
    }

    @Test
    void findTraineeByUsernameThatExists() {
        // Given
        String username = "testUser";
        Trainee trainee = new Trainee();
        trainee.setUsername(username);
        trainee.setTrainers(new ArrayList<>());
        trainee.setTrainings(new ArrayList<>());
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));

        // When
        traineeServiceMap.delete(username);

        // Then
        verify(traineeRepository, times(1)).deleteById(trainee.getId());
        verify(traineeRepository, times(1)).findByUsername(username);
    }

    @Test
    void findTraineeByUsernameThatDoesNotExist() {
        // Given
        String username = "nonExistentUser";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UserNotFoundException.class, () -> traineeServiceMap.findTraineeByUsername(username));
    }

    @Test
    void updateTraineeTrainersForExistingTrainee() {
        // Given
        String username = "testUser";
        Trainee trainee = new Trainee();
        trainee.setUsername(username);
        trainee.setTrainers(new ArrayList<>());  // Убедимся, что trainers не null и изменяем
        List<Trainer> trainers = List.of(new Trainer());
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findAllTrainersByUsername(List.of("trainer1", "trainer2"))).thenReturn(trainers);

        // When
        TraineeProfileReadDto result = traineeServiceMap.updateTraineeTrainers(username, List.of("trainer1", "trainer2"));

        // Then
        assertEquals(trainers, trainee.getTrainers());
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    void updateTraineeTrainersForNonExistingTrainee() {
        // Given
        String username = "nonExistentUser";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(TraineeNotFoundException.class, () -> traineeServiceMap.updateTraineeTrainers(username, List.of("trainer1", "trainer2")));
    }
}
