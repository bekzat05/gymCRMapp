package com.bekzat.boot.gymcrm.service;

import com.bekzat.boot.gymcrm.model.TrainingType;

import java.util.List;

public interface TrainingTypeService extends BaseService<TrainingType, Long>  {

    List<TrainingType> findAll();
}
