package com.aitbekov.workloadservice.repository;

import com.aitbekov.workloadservice.model.TrainerWorkloadSummary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TrainerWorkloadRepositoryImpl implements TrainerWorkloadRepository {
    @Override
    public void save(TrainerWorkloadSummary summary) {
    }

    @Override
    public Optional<TrainerWorkloadSummary> findByUsername(String username) {
        return Optional.empty();
    }

}
