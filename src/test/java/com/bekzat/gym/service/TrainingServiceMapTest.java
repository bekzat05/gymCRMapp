package com.bekzat.gym.service;

import com.bekzat.gym.service.map.TrainingServiceMap;
import com.bekzat.gym.service.map.UserCredentialsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.bekzat.gym.dao.TrainingRepository;
import com.bekzat.gym.dto.CredentialsDto;
import com.bekzat.gym.dto.TrainingReadDto;
import com.bekzat.gym.exceptions.AuthenticationException;
import com.bekzat.gym.mapper.TrainingMapper;
import com.bekzat.gym.model.Training;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceMapTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private UserCredentialsService userCredentialsService;

    @InjectMocks
    private TrainingServiceMap trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Long trainingId = 1L;
        Training training = new Training();
        training.setId(trainingId);
        CredentialsDto credentialsDto = new CredentialsDto("boris.vlasevsky", "password");

        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(false);
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));
        when(trainingMapper.toDto(training)).thenReturn(new TrainingReadDto(trainingId, "Training", null, 60, null, null));

        TrainingReadDto result = trainingService.findById(trainingId, credentialsDto);

        assertNotNull(result);
        assertEquals(trainingId, result.id());

        verify(trainingRepository, times(1)).findById(trainingId);
    }

    @Test
    void testCreate() {
        Training training = new Training();
        CredentialsDto credentialsDto = new CredentialsDto("boris.vlasevsky", "password");

        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(false);
        when(trainingRepository.save(training)).thenReturn(training);

        Training result = trainingService.create(training, credentialsDto);

        assertNotNull(result);

        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void testDelete() {
        Long trainingId = 1L;
        Training training = new Training();
        training.setId(trainingId);
        CredentialsDto credentialsDto = new CredentialsDto("boris.vlasevsky", "password");

        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(false);
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));
        doNothing().when(trainingRepository).delete(trainingId);

        boolean result = trainingService.delete(trainingId, credentialsDto);

        assertTrue(result);

        verify(trainingRepository, times(1)).delete(trainingId);
    }

    @Test
    void testDeleteThrowsExceptionIfInvalidCredentials() {
        CredentialsDto credentialsDto = new CredentialsDto("boris.vlasevsky", "wrong-password");

        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(true);

        assertThrows(AuthenticationException.class, () -> trainingService.delete(1L, credentialsDto));
    }
}