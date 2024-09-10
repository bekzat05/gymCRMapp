package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TraineeDAO;
import com.bekzat.gym.exceptions.TraineeNotFoundException;
import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.service.TraineeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class TraineeServiceMap implements TraineeService {

    @Autowired
    private TraineeDAO traineeDAO;

    @Override
    public Trainee findById(Long id) {
        log.info("Finding trainee by ID: {}", id);
        return traineeDAO.findById(id).orElseThrow(() -> new TraineeNotFoundException(id));    }

    @Override
    public Trainee save(Trainee trainee) {
        log.info("Saving trainee: {}", trainee);
        return traineeDAO.save(trainee);
    }

    @Override
    public void delete(Trainee trainee) {
        log.info("Deleting trainee: {}", trainee);
        traineeDAO.delete(trainee);
    }
}
