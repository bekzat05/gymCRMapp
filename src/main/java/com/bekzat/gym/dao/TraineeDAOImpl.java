package com.bekzat.gym.dao;

import com.bekzat.gym.model.Trainee;
import org.springframework.stereotype.Component;

@Component
public class TraineeDAOImpl extends BaseAbstractDAO<Trainee> implements TraineeDAO {

    @Override
    public void delete(Trainee trainee) {
        storage.delete(trainee.getId());
    }
}
