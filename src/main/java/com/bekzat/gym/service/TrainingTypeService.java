package com.bekzat.gym.service;

import com.bekzat.gym.model.TrainingType;

import java.util.List;

public interface TrainingTypeService extends BaseService<TrainingType, Long> {

    List<TrainingType> findAll();
}
