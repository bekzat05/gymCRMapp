package com.example.demo.service;

import com.example.demo.model.TrainingType;

import java.util.List;

public interface TrainingTypeService extends BaseService<TrainingType, Long>  {

    List<TrainingType> findAll();
}
