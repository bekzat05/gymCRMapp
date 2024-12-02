package com.aitbekov.gym.service;

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
import com.aitbekov.gym.service.map.TraineeServiceMap;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceMapTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TraineeMapper traineeMapper;

    @Mock(lenient = true)
    private TrainingMapper trainingMapper;

    @Mock(lenient = true)
    private TrainerMapper trainerMapper;

    @InjectMocks
    private TraineeServiceMap traineeService;

    private static final String TRAINEE_USERNAME = "john.doe";
    private static final String TRAINER_USERNAME = "trainer1";
    private static final Trainee TRAINEE = new Trainee();
    private static final Trainer TRAINER = new Trainer();
    private static final List<Trainer> TRAINERS = new ArrayList<>();
    private static final TraineeProfileReadDto TRAINEE_PROFILE_DTO = new TraineeProfileReadDto("John", "Doe", null, "123 Street", true, Collections.emptyList());
    private static final TraineeCreateAndUpdateDto TRAINEE_UPDATE_DTO = new TraineeCreateAndUpdateDto(TRAINEE_USERNAME, "John", "Doe", "123 Street", null, true);
    private static final StatusUpdateDto STATUS_UPDATE_DTO = new StatusUpdateDto(true);
    private static final List<TrainingReadDto> TRAINING_DTO_LIST = Collections.emptyList();
    private static final List<Training> TRAININGS = Collections.emptyList();

    static {
        TRAINEE.setUsername(TRAINEE_USERNAME);
        TRAINERS.add(TRAINER);
    }

    @Test
    @SneakyThrows
    void testChangeActiveStatus() {
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(TRAINEE));

        traineeService.changeActiveStatus(TRAINEE_USERNAME, STATUS_UPDATE_DTO);

        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    @SneakyThrows
    void testGetTraineeTrainings() {
        when(trainingRepository.findTrainingsByTraineeUsername(anyString()))
                .thenReturn(TRAININGS);
        when(trainingMapper.toDtoList(anyList())).thenReturn(TRAINING_DTO_LIST);

        List<TrainingReadDto> trainings = traineeService.getTraineeTrainings(TRAINEE_USERNAME);

        assertEquals(TRAINING_DTO_LIST, trainings);
    }

    @Test
    @SneakyThrows
    void testUpdate() {
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(TRAINEE));
        when(traineeMapper.toProfileReadDto(any(Trainee.class))).thenReturn(TRAINEE_PROFILE_DTO);
        when(trainerMapper.toDtoList(anyList())).thenReturn(Collections.emptyList());

        TraineeProfileReadDto updatedProfile = traineeService.update(TRAINEE_USERNAME, TRAINEE_UPDATE_DTO);

        assertEquals(TRAINEE_PROFILE_DTO, updatedProfile);
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    @SneakyThrows
    void testFindTraineeByUsername() {
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(TRAINEE));
        when(traineeMapper.toProfileReadDto(any(Trainee.class))).thenReturn(TRAINEE_PROFILE_DTO);
        when(trainerMapper.toDtoList(anyList())).thenReturn(Collections.emptyList());

        TraineeProfileReadDto profile = traineeService.findTraineeByUsername(TRAINEE_USERNAME);

        assertEquals(TRAINEE_PROFILE_DTO, profile);
    }

    @Test
    @SneakyThrows
    void testUpdateTraineeTrainers() {
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(TRAINEE));
        when(trainerRepository.findAllTrainersByUsername(anyList())).thenReturn(TRAINERS);
        when(traineeMapper.toProfileReadDto(any(Trainee.class))).thenReturn(TRAINEE_PROFILE_DTO);
        when(trainerMapper.toDtoList(anyList())).thenReturn(Collections.emptyList());

        TraineeProfileReadDto updatedProfile = traineeService.updateTraineeTrainers(TRAINEE_USERNAME, Collections.singletonList(TRAINER_USERNAME));

        assertEquals(TRAINEE_PROFILE_DTO, updatedProfile);
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }
}
