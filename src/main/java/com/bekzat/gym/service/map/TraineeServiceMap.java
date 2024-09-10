package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.TraineeDAO;
import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceMap implements TraineeService {

    @Autowired
    private TraineeDAO traineeDAO;

    @Override
    public Trainee findById(Long id) {
        return traineeDAO.findById(id).orElseThrow(() -> new RuntimeException("Trainee not found"));    }

    @Override
    public Trainee save(Trainee trainee) {
        return traineeDAO.save(trainee);
    }

    @Override
    public void delete(Trainee trainee) {
        traineeDAO.delete(trainee);
    }
}
