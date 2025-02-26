package com.aitbekov.gym.service;

import com.aitbekov.gym.dto.TrainingCreateDto;
import com.aitbekov.gym.model.Training;

public interface TrainingService extends BaseService<Training, Long> {

    void create(TrainingCreateDto dto);
}
