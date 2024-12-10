package com.aitbekov.gym.service;

import com.aitbekov.gym.dto.*;
import com.aitbekov.gym.mapstruct.*;
import com.aitbekov.gym.model.*;
import com.aitbekov.gym.repository.*;
import com.aitbekov.gym.service.map.TrainerServiceMap;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceMapTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private TrainerMapper trainerMapper;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainerServiceMap trainerService;

    private static final String TRAINER_USERNAME = "trainer1";
    private static final Trainer TRAINER = new Trainer();
    private static final TrainerProfileReadDto TRAINER_PROFILE_DTO = new TrainerProfileReadDto(TRAINER_USERNAME, "Trainer", "One", Collections.emptyList(), Collections.emptyList());
    private static final TrainerCreateDto TRAINER_CREATE_DTO = new TrainerCreateDto("Trainer", "One", Collections.emptyList(), true);
    private static final StatusUpdateDto STATUS_UPDATE_DTO = new StatusUpdateDto(true);
    private static final List<TrainingReadDto> TRAINING_DTO_LIST = Collections.emptyList();
    private static final List<Training> TRAININGS = Collections.emptyList();
    private static final List<TrainerReadDto> TRAINER_DTO_LIST = Collections.emptyList();

    static {
        TRAINER.setUsername(TRAINER_USERNAME);
    }

    @Test
    @SneakyThrows
    void testFindAll() {
        when(trainerRepository.findAll()).thenReturn(Collections.singletonList(TRAINER));
        when(trainerMapper.toDtoList(anyList())).thenReturn(TRAINER_DTO_LIST);

        List<TrainerReadDto> trainers = trainerService.findAll();

        assertEquals(TRAINER_DTO_LIST, trainers);
    }

    @Test
    @SneakyThrows
    void testChangeActiveStatus() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(TRAINER));

        trainerService.changeActiveStatus(TRAINER_USERNAME, STATUS_UPDATE_DTO);

        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    @SneakyThrows
    void testGetTrainerTrainings() {
        when(trainingRepository.findTrainingsByTrainerUsername(anyString()))
                .thenReturn(TRAININGS);
        when(trainingMapper.toDtoList(anyList())).thenReturn(TRAINING_DTO_LIST);

        List<TrainingReadDto> trainings = trainerService.getTrainerTrainings(TRAINER_USERNAME);

        assertEquals(TRAINING_DTO_LIST, trainings);
    }

    @Test
    @SneakyThrows
    void testGetTrainersNotAssignedToTrainee() {
        when(trainerRepository.findTrainersNotAssignedToTrainee(anyString())).thenReturn(Collections.singletonList(TRAINER));
        when(trainerMapper.toDtoList(anyList())).thenReturn(TRAINER_DTO_LIST);

        List<TrainerReadDto> trainers = trainerService.getTrainersNotAssignedToTrainee("trainee1");

        assertEquals(TRAINER_DTO_LIST, trainers);
    }
}
