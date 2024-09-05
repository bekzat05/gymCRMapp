package com.bekzat.gym.service;

import com.bekzat.gym.dao.TrainerDAO;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.service.map.TrainerServiceMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceMapTest {

    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private TrainerServiceMap trainerServiceMap;

    @Test
    void testFindByIdSuccess() {
        Long id = 1L;
        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setId(id);
        when(trainerDAO.findById(id)).thenReturn(Optional.of(expectedTrainer));

        Trainer actualTrainer = trainerServiceMap.findById(id);

        assertEquals(expectedTrainer, actualTrainer);
        verify(trainerDAO).findById(id);
    }

    @Test
    void testFindByIdThrowsNotFoundException() {
        Long nonExistentId = 1L;
        when(trainerDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            trainerServiceMap.findById(nonExistentId);
        });

        verify(trainerDAO).findById(nonExistentId);
    }

    @Test
    void testSave() {
        Trainer trainerToSave = new Trainer();
        trainerToSave.setId(1L);
        when(trainerDAO.save(trainerToSave)).thenReturn(trainerToSave);

        Trainer savedTrainer = trainerServiceMap.save(trainerToSave);

        assertEquals(trainerToSave, savedTrainer);
        verify(trainerDAO).save(trainerToSave);
    }
}