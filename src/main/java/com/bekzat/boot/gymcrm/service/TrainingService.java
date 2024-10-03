package com.bekzat.boot.gymcrm.service;

import com.bekzat.boot.gymcrm.dto.TrainingCreateDto;
import com.bekzat.boot.gymcrm.model.Training;

public interface TrainingService extends BaseService<Training, Long> {

    void create(TrainingCreateDto createDto);
}
