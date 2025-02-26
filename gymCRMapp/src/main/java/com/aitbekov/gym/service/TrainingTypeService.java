package com.aitbekov.gym.service;

import com.aitbekov.gym.model.TrainingType;

import java.util.List;

public interface TrainingTypeService extends BaseService<TrainingType, Long> {

    List<TrainingType> findAll();

}
