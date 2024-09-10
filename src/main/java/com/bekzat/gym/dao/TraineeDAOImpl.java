package com.bekzat.gym.dao;

import com.bekzat.gym.model.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TraineeDAOImpl extends BaseAbstractDAO<Trainee> implements TraineeDAO {

    @Override
    public void delete(Trainee trainee) {
        storage.delete(trainee.getId());
        log.info("Trainee deleted: {}", trainee);
    }
}
