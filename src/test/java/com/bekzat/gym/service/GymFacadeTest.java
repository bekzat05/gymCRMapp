package com.bekzat.gym.service;

import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.service.map.GymFacade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GymFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private GymFacade gymFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainingSession() {
        Long traineeId = 1L;
        Long trainerId = 2L;
        Training training = new Training();

        Trainee mockTrainee = new Trainee();
        mockTrainee.setId(traineeId);

        Trainer mockTrainer = new Trainer();
        mockTrainer.setId(trainerId);

        when(traineeService.findById(traineeId)).thenReturn(mockTrainee);
        when(trainerService.findById(trainerId)).thenReturn(mockTrainer);

        gymFacade.createTrainingSession(traineeId, trainerId, training);

        assertEquals(traineeId, training.getTrainee());
        assertEquals(trainerId, training.getTrainer());
        verify(trainingService).save(training);
    }
}
