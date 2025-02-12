package com.aitbekov.gym.repository;

import com.aitbekov.gym.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByUsername(String username);

    @Query("SELECT t FROM Trainer t WHERE t NOT IN " +
            "(SELECT tr.trainers FROM Trainee tr WHERE tr.username = :traineeUsername)")
    Set<Trainer> findTrainersNotAssignedToTrainee(@Param("traineeUsername") String traineeUsername);

    @Query("SELECT t FROM Trainer t WHERE t.username IN (:names)")
    Set<Trainer> findAllTrainersByUsername(@Param("names") List<String> names);

}
