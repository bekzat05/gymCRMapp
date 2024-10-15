package com.example.demo.service;

import com.example.demo.dto.TrainingCreateDto;
import com.example.demo.model.Training;

public interface TrainingService extends BaseService<Training, Long> {

    void create(TrainingCreateDto createDto);
}
