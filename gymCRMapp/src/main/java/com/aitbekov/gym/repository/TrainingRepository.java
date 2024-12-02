package com.aitbekov.gym.repository;

import com.aitbekov.gym.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {


    @Query("SELECT t FROM Training t WHERE t.trainee.username = :traineeUsername")
    List<Training> findTrainingsByTraineeUsername(@Param("traineeUsername") String traineeUsername);


    @Query("SELECT t FROM Training t WHERE t.trainer.username = :trainerUsername")
    List<Training> findTrainingsByTrainerUsername(
            @Param("trainerUsername") String trainerUsername);
}
