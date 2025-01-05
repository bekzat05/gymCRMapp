package com.aitbekov.workloadservice.service;

import com.aitbekov.workloadservice.dto.TrainerWorkloadRequest;
import com.aitbekov.workloadservice.model.TrainerWorkloadSummary;

public interface TrainerWorkloadService {

    void updateWorkload(TrainerWorkloadRequest request);
    TrainerWorkloadSummary getWorkload(String username, int year, int month);
}
