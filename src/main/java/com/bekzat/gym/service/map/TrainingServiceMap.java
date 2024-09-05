package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TrainingDAO;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceMap implements TrainingService {
    @Autowired
    private TrainingDAO trainingDAO;

    @Override
    public Training findById(Long id) {
        return trainingDAO.findById(id).orElseThrow(() -> new RuntimeException("Training not found"));
    }

    @Override
    public Training save(Training training) {
        return trainingDAO.save(training);
    }
}
