package com.bekzat.gym.dao;

import com.bekzat.gym.model.Training;
import org.springframework.stereotype.Component;

@Component
public class TrainingRepository extends BaseAbstractDAO<Long, Training> {
    public TrainingRepository() {
        super(Training.class);
    }
}
