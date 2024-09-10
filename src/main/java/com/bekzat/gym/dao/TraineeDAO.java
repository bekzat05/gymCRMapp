package com.bekzat.gym.dao;

import com.bekzat.gym.model.Trainee;

public interface TraineeDAO extends BaseDAO<Trainee> {

    void delete(Trainee trainee);
}
