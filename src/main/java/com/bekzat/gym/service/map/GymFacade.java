package com.bekzat.gym.service.map;

import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.service.TraineeService;
import com.bekzat.gym.service.TrainerService;
import com.bekzat.gym.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    @Autowired
    public GymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public void createTrainingSession(Long traineeId, Long trainerId, Training training) {
        Trainee trainee = traineeService.findById(traineeId);
        Trainer trainer = trainerService.findById(trainerId);
        training.setTrainee(trainee.getId());
        training.setTrainer(trainer.getId());
        trainingService.save(training);
    }
}
