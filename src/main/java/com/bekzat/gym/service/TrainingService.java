package com.bekzat.gym.service;

import com.bekzat.gym.dto.TrainingCreateDto;
import com.bekzat.gym.model.Training;

public interface TrainingService extends BaseService<Training, Long> {
    void create(TrainingCreateDto createDto);
}
