package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TrainerDAO;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceMap implements TrainerService {
    @Autowired
    private TrainerDAO trainerDAO;

    @Override
    public Trainer findById(Long id) {
        return trainerDAO.findById(id).orElseThrow(() -> new RuntimeException("Trainer not found"));
    }

    @Override
    public Trainer save(Trainer trainer) {
        return trainerDAO.save(trainer);
    }
}
