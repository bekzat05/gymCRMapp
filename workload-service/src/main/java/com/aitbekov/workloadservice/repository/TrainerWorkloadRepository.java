package com.aitbekov.workloadservice.repository;

import com.aitbekov.workloadservice.model.TrainerWorkloadSummary;

import java.util.Optional;


public interface TrainerWorkloadRepository{
    void save(TrainerWorkloadSummary summary);

    TrainerWorkloadSummary findByUsername(String username);
}
