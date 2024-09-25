package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TrainingDAO;
import com.bekzat.gym.exceptions.TrainingNotFoundException;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TrainingServiceMap implements TrainingService {
    @Autowired
    private TrainingDAO trainingDAO;

    @Override
    public Training findById(Long id) {
        log.info("Finding training by ID: {}", id);
        return trainingDAO.findById(id).orElseThrow(() -> new TrainingNotFoundException(id));
    }

    @Override
    public Training save(Training training) {
        log.info("Saving training: {}", training);
        return trainingDAO.save(training);
    }
}
