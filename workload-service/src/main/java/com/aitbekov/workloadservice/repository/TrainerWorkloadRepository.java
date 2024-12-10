package com.aitbekov.workloadservice.repository;

import com.aitbekov.workloadservice.model.TrainerWorkloadSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerWorkloadRepository extends JpaRepository<TrainerWorkloadSummary, Long> {

    TrainerWorkloadSummary findByUsername(String username);
}
