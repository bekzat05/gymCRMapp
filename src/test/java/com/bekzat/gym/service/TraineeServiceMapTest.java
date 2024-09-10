package com.bekzat.gym.service;

import com.bekzat.gym.dao.TraineeDAO;
import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.service.map.TraineeServiceMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class TraineeServiceMapTest {

    @Mock
    private TraineeDAO traineeDAO;


    @InjectMocks
    private TraineeServiceMap traineeServiceMap;


    @Test
    void testFindById() {
        Trainee expectedTrainee = new Trainee();
        expectedTrainee.setId(1L);
        when(traineeDAO.findById(1L)).thenReturn(Optional.of(expectedTrainee));

        Trainee actualTrainee = traineeServiceMap.findById(1L);

        assertEquals(expectedTrainee, actualTrainee);
        verify(traineeDAO).findById(1L);
    }

    @Test
    void testSave() {
        Trainee traineeToSave = new Trainee();
        traineeToSave.setId(1L);
        when(traineeDAO.save(traineeToSave)).thenReturn(traineeToSave);

        Trainee savedTrainee = traineeServiceMap.save(traineeToSave);

        assertEquals(traineeToSave, savedTrainee);
        verify(traineeDAO).save(traineeToSave);
    }

    @Test
    void testDelete() {
        Trainee traineeToDelete = new Trainee();
        traineeToDelete.setId(1L);

        traineeServiceMap.delete(traineeToDelete);

        verify(traineeDAO).delete(traineeToDelete);
    }

    @Test
    void testFindByIdThrowsNotFoundException() {
        Long nonExistentId = 1L;
        when(traineeDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            traineeServiceMap.findById(nonExistentId);
        });

        verify(traineeDAO).findById(nonExistentId);
    }
}