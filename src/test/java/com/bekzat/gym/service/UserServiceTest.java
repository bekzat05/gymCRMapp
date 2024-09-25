package com.bekzat.gym.service;

import com.bekzat.gym.service.map.UserCredentialsService;
import com.bekzat.gym.service.map.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.bekzat.gym.dao.UserRepository;
import com.bekzat.gym.dto.CredentialsDto;
import com.bekzat.gym.dto.UserReadDto;
import com.bekzat.gym.mapper.UserMapper;
import com.bekzat.gym.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCredentialsService userCredentialsService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        CredentialsDto credentialsDto = new CredentialsDto("bekzat.aitbekov", "password");
        UserReadDto userReadDto = new UserReadDto(userId, "Bekzat", "Aitbekov", "bekzat.aitbekov");

        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userReadDto);

        UserReadDto result = userService.findById(userId, credentialsDto);

        assertNotNull(result);
        assertEquals(userId, result.id());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDelete() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        CredentialsDto credentialsDto = new CredentialsDto("bekzat.aitbekov", "password");

        when(userCredentialsService.checkCredentials(credentialsDto)).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        boolean result = userService.delete(userId, credentialsDto);

        assertTrue(result);

        verify(userRepository, times(1)).delete(userId);
    }
}
