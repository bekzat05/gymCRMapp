package com.aitbekov.gym.service.map;

import com.aitbekov.gym.repository.TrainingTypeRepository;
import com.aitbekov.gym.model.TrainingType;
import com.aitbekov.gym.service.TrainingTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TrainingTypeServiceMap implements TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;

    @Override
    @Transactional
    public List<TrainingType> findAll() {
        return trainingTypeRepository.findAll();
    }
}
