package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TrainerDAO;
import com.bekzat.gym.exceptions.TrainerNotFoundException;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.service.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TrainerServiceMap implements TrainerService {
    @Autowired
    private TrainerDAO trainerDAO;

    @Override
    public Trainer findById(Long id) {
        log.info("Finding trainer by ID: {}", id);
        return trainerDAO.findById(id).orElseThrow(() -> new TrainerNotFoundException(id));
    }

    @Override
    public Trainer save(Trainer trainer) {
        log.info("Saving trainer: {}", trainer);
        return trainerDAO.save(trainer);
    }
}
