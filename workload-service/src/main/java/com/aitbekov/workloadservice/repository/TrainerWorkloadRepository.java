package com.aitbekov.workloadservice.repository;

import com.aitbekov.workloadservice.model.TrainerWorkloadSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerWorkloadRepository extends MongoRepository<TrainerWorkloadSummary, String> {

    Optional<TrainerWorkloadSummary> findByUsername(String username);

    List<TrainerWorkloadSummary> findByFirstNameAndLastName(String firstName, String lastName);
}
