package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TrainingTypeRepository;
import com.bekzat.gym.model.TrainingType;
import com.bekzat.gym.service.TrainingTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TrainingTypeServiceMap implements TrainingTypeService {
    private TrainingTypeRepository trainingTypeRepository;

    @Override
    @Transactional
    public List<TrainingType> findAll() {
        return trainingTypeRepository.findAll();
    }
}
